package engineeringcalculator;

import java.util.ArrayList;
import java.util.LinkedList;

//Класс необходим для разбиения вводимого выражения на отдельные элементы и логического контроля ввода
public class Expression {

    private final Functions functions;
    private final Constants constants;

    private LinkedList<String> e;    //Стек для хранения выражения

    private int bracketsCount = 0;     //Переменная необходимая для контроля правильности сочетания открывающих и закрывающих скобок

    //Блок типов элементов выражения
    private final int START = 0;            //Тип для элемента _start, обозначающего начало выражения
    private final int OPEN_BRACKET = 1;     // (
    private final int CLOSE_BRACKET = 2;    // )
    private final int MINUS = 3;            // - (знак минус в силу некоторых особенностей использования требует отдельного типа)
    private final int OPERATOR = 4;         // *, +, /, ^
    private final int NUMBER = 5;           // 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, .
    private final int FUNC = 6;             //Функции
    private final int CONST = 7;            //Константы
    private final int UNKNOWN = 8;          //Тип неизвестен

    //Разрешенные элементы. Содержимое каждой строки - это элементы, разрешенные после элемента, соответствующего номеру строки
    private final int[][] allowedElements = {
            {OPEN_BRACKET, MINUS, NUMBER, FUNC, CONST},
            {OPEN_BRACKET, MINUS, NUMBER, FUNC, CONST},
            {CLOSE_BRACKET, MINUS, OPERATOR},
            {OPEN_BRACKET, NUMBER, FUNC, CONST},
            {OPEN_BRACKET, NUMBER, FUNC, CONST},
            {CLOSE_BRACKET, MINUS, OPERATOR, NUMBER},
            {OPEN_BRACKET},
            {CLOSE_BRACKET, MINUS, OPERATOR},
    };

    public Expression(Functions functions, Constants constants) {
        this.functions = functions;
        this.constants = constants;
        e = new LinkedList<>();
        e.add("_start");
    }

    //Метод служит для добавления элемента в выражение
    public void addToExpression(String s) {

        //Вначале проверяем, возможно ли добавление элемента
        int typeLast;          //Тип последнего элемента в выражении
        int typeCurrent;       //Тип добавляемого элемента
        typeLast = getTypeElement(e.peekLast());
        typeCurrent = getTypeElement(s);
        boolean isFind = false;
        for (int i : allowedElements[typeLast]) {
            if (i == typeCurrent) {
                isFind = true;
                break;
            }
        }
        if (!isFind) return;

        //Следующий шаг - добавление элемента. В зависимости от типа элемента добавление выполняется по-разному
        if (typeCurrent == OPEN_BRACKET) {
            e.add(s);
            bracketsCount++;
            return;
        }

        if (typeCurrent == CLOSE_BRACKET) {
            if ((bracketsCount - 1) < 0) return;
            e.add(s);
            bracketsCount--;
            return;
        }

        if ((typeCurrent == MINUS) | (typeCurrent == OPERATOR)) {
            e.add(s);
            return;
        }

        if (typeCurrent == NUMBER) {
            if (typeLast != NUMBER) {
                if (s.equals(".")) {
                    e.add("0.");
                    return;
                }
                if (s.equals("0")) {
                    e.add("0.");
                    return;
                }
                e.add(s);
            }
            if (typeLast == NUMBER) {
                try {
                    Double.parseDouble(e.peekLast() + s);
                } catch (Exception e) {
                    return;
                }
                String tmp = e.pollLast();
                e.add(tmp + s);
            }
        }

        if (typeCurrent == FUNC) {
            e.add(s);
            e.add("(");
            bracketsCount++;
            return;
        }

        if (typeCurrent == CONST) {
            e.add(s);
            return;
        }

    }

    //Метод служит для удаления элемента из выражения
    public void removeFromExpression() {

        //Из пустого выражения нельзя ничего удалить
        if (e.size() == 1) return;

        //Получаем последний элемент выражения и узнаем его тип
        String s = e.pollLast();
        int typeLast = getTypeElement(s);

        //В зависимости от типа элемента производим его удаление
        if (typeLast == OPEN_BRACKET) {
            if (getTypeElement(e.peekLast()) == FUNC) e.pollLast();
            bracketsCount--;
            return;
        }

        if (typeLast == CLOSE_BRACKET) {
            bracketsCount++;
            return;
        }

        if (typeLast == NUMBER) {
            if (s.length() > 0) {
                s = s.substring(0, s.length() - 1);
            }
            if (s.length() > 0) e.add(s);
        }

    }

    //Служит для вычисления значения выражения
    public void calculateExpression() throws Exception {

        //Проверка возможности вычислить выражение
        int typeLast = getTypeElement(e.peekLast());
        if (typeLast == START) throw new Exception("Введите выражение");
        if ((typeLast != CLOSE_BRACKET) & (typeLast != NUMBER) & (typeLast != CONST))
            throw new Exception("Выражение не завершено...");
        if (bracketsCount != 0) throw new Exception("Закройте незакрытые скобки");

        //Создаем вспомогательный массив, с которым будем работать при вычислении значения
        ArrayList<String> a = new ArrayList<>(e);

        //Первый этап - замена имен констант их значениями
        for (int i = 0; i < a.size(); i++)
            if (getTypeElement(a.get(i)) == CONST) a.set(i, ("" + constants.getValue(a.get(i))));

        //Объявление вспомогательных переменных
        int typeLeft, typeRight;    //Типы данных в ячейках слева и справа от данной
        boolean minusBit;           //true - если был "свёрнут" хотя бы один унарный минус
        boolean bracketsBit;        //true - если была "свёрнута" хотя бы одна пара скобок
        boolean functionBit;        //true - если была вычислена хотя бы одна функция

        double arg1, arg2;          //Агрументы арифметических операция
        double result;              //Результат выполнения арифметической операции
        int rangLevel;              //Базовый уровень ранга арифметических операций (увеличивается внутри пар скобок)
        int rang;                  //Текущий ранг арифметической операции
        int maxRang;                //Текущий максимальный ранг
        int maxRangPos;             //Позиция элемента с максимальным рангом

        String s;                   //Вспомогательная строковая переменная
        int t;                      //Вспомогательная целочисленная переменная
        double d;                   //Вспомогательная вещественная переменная

        //Второй этап - итеративное вычисление значения
        while (true) {

            do {
                minusBit = false;
                bracketsBit = false;
                functionBit = false;

                //Замена унарных минусов на отрицательные числа
                for (int i = 1; i < (a.size() - 1); i++) {
                    if (getTypeElement(a.get(i)) != MINUS) continue;
                    typeLeft = getTypeElement(a.get(i - 1));
                    typeRight = getTypeElement(a.get(i + 1));
                    if (((typeLeft == START) & (typeRight == NUMBER)) | ((typeLeft == OPEN_BRACKET) & (typeRight == NUMBER))) {
                        d = (-1) * Double.parseDouble(a.get(i + 1));
                        a.set(i, ("" + d));
                        a.remove(i + 1);
                        minusBit = true;
                    }
                }

                //Удаление пар скобок, внутри которых содержатся одиночные числа
                for (int i = 1; i < (a.size() - 1); i++) {
                    if (getTypeElement(a.get(i)) != NUMBER) continue;
                    typeLeft = getTypeElement(a.get(i - 1));
                    typeRight = getTypeElement(a.get(i + 1));
                    if ((typeLeft == OPEN_BRACKET) & (typeRight == CLOSE_BRACKET)) {
                        a.remove(i + 1);
                        a.remove(i - 1);
                        i -= 2;
                        bracketsBit = true;
                    }
                }

                //Вычисление значений функций. В данном месте возможно появление исключения для функций, аргумент которых оказался некорректен
                for (int i = 1; i < (a.size() - 1); i++) {
                    typeRight = getTypeElement(a.get(i + 1));
                    if ((getTypeElement(a.get(i)) == FUNC) & (typeRight == NUMBER)) {
                        d = functions.execute(a.get(i), Double.parseDouble(a.get(i + 1)));
                        a.set(i, ("" + d));
                        a.remove(i + 1);
                        functionBit = true;
                    }
                }

            } while (minusBit | bracketsBit | functionBit);

            //Проверка условия выхода из цикла: выражение должно сократиться до одного числа - оно и будет результатом вычисления
            if (a.size() == 2) break;

            //Поиск операции с наивысшим рангом
            rangLevel=0;
            rang=0;
            maxRang=0;
            maxRangPos=0;
            for (int i=1;i<(a.size()-1);i++){
                s=a.get(i);
                t=getTypeElement(s);
                if (t==OPEN_BRACKET)rangLevel+=10;
                if (t==CLOSE_BRACKET)rangLevel-=10;
                if ((t==OPERATOR) | (t==MINUS)){
                    if (s.equals("+") | s.equals("-"))rang=rangLevel+1;
                    if (s.equals("*") | s.equals("/"))rang=rangLevel+2;
                    if (s.equals("^"))rang=rangLevel+3;
                    if(rang>maxRang){
                        maxRang=rang;
                        maxRangPos=i;
                    }
                }
            }

            //Вычисление результата наиболее высокоранговой операции. В данном месте возможно появление исключения при выполнении деления на 0
            if(maxRangPos>0){
                arg1=Double.parseDouble(a.get(maxRangPos-1));
                arg2=Double.parseDouble(a.get(maxRangPos+1));
                result=0;
                s=a.get(maxRangPos);
                switch (s){
                    case "+":{
                        result=arg1+arg2;
                        break;
                    }
                    case "-":{
                        result=arg1-arg2;
                        break;
                    }
                    case "*":{
                        result=arg1*arg2;
                        break;
                    }
                    case "/":{
                        result=arg1/arg2;
                        break;
                    }
                    case "^":
                        result=Math.pow(arg1,arg2);
                        break;
                }
                a.set(maxRangPos,(""+result));
                a.remove(maxRangPos-1);
                a.remove(maxRangPos);
            }

        }

        //Отбрасывание лишних нулей после запятой в случае получения результата, являющегося целым числом
        d=Double.parseDouble(a.get(1));
        if (d==Math.round(d)){
            s=a.get(1);
            a.set(1,s.substring(0,s.indexOf(".")));
        }

        //Запись результата в выражение
        e.clear();
        e.add(a.get(1));

    }

    //Метод служит для полной очистки выражения. Вызвращает пустую строку
    public void clear() {
        e.clear();
        e.add("_start");
        bracketsCount = 0;
    }

    //Возвращает тип переданного ему аргумента
    private int getTypeElement(String s) {

        if (s.equals("_start")) return START;

        if (s.equals("(")) return OPEN_BRACKET;

        if (s.equals(")")) return CLOSE_BRACKET;

        if (s.equals("-")) return MINUS;

        if ("*+^/".contains(s) & (s.length() == 1)) return OPERATOR;

        if (s.equals(".")) return NUMBER;
        try {
            Double.parseDouble(s);
            return NUMBER;
        } catch (Exception e) {
        }

        for (String[] f : functions.getFuncNames()) {
            if (s.equals(f[0])) return FUNC;
        }

        for (String[] c : constants.getConstNames()) {
            if (s.equals(c[0])) return CONST;
        }

        return UNKNOWN;

    }

    @Override
    public String toString() {
        String s = "";
        for (String t : e) {
            if (getTypeElement(t) == START) continue;
            s += t;
        }
        return s;
    }

}

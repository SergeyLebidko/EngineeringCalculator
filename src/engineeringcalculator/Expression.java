package engineeringcalculator;

import java.util.ArrayList;
import java.util.LinkedList;

//Класс необходим для разбиения вводимого выражения на отдельные элементы и логического контроля ввода
public class Expression {

    private final Functions functions;
    private final Constants constants;

    private LinkedList<String> e;    //Стек для хранения выражения

    private int bracketsCount=0;     //Переменная необходимая для контроля правильности сочетания открывающих и закрывающих скобок

    //Блок типов элементов выражения
    private final int START=0;            //Тип для элемента _start, обозначающего начало выражения
    private final int OPEN_BRACKET=1;     // (
    private final int CLOSE_BRACKET=2;    // )
    private final int MINUS=3;            // - (знак минус в силу некоторых особенностей использования требует отдельного типа)
    private final int OPERATOR=4;         // *, +, /, ^
    private final int NUMBER=5;           // 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, .
    private final int FUNC=6;             //Функции
    private final int CONST=7;            //Константы
    private final int UNKNOWN=8;          //Тип неизвестен

    //Разрешенные элементы. Содержимое каждой строки - это элементы, разрешенные после элемента, соответствующего номеру строки
    private final int[][] allowedElements={
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
        e=new LinkedList<>();
        e.add("_start");
    }

    //Метод служит для добавления элемента в выражение
    public void addToExpression(String s) {

        //Вначале проверяем, возможно ли добавление элемента
        int typeLast;          //Тип последнего элемента в выражении
        int typeCurrent;       //Тип добавляемого элемента
        typeLast=getTypeElement(e.peekLast());
        typeCurrent=getTypeElement(s);
        boolean isFind=false;
        for (int i: allowedElements[typeLast]){
            if(i==typeCurrent){
                isFind=true;
                break;
            }
        }
        if(!isFind)return;

        //Следующий шаг - добавление элемента. В зависимости от типа элемента добавление выполняется по-разному
        if(typeCurrent==OPEN_BRACKET){
            e.add(s);
            bracketsCount++;
            return;
        }

        if(typeCurrent==CLOSE_BRACKET){
            if((bracketsCount-1)<0)return;
            e.add(s);
            bracketsCount--;
            return;
        }

        if((typeCurrent==MINUS) | (typeCurrent==OPERATOR)){
            e.add(s);
            return;
        }

        if(typeCurrent==NUMBER){
            if(typeLast!=NUMBER){
                if(s.equals(".")){
                    e.add("0.");
                    return;
                }
                if(s.equals("0")){
                    e.add("0.");
                    return;
                }
                e.add(s);
            }
            if(typeLast==NUMBER){
                try {
                    Double.parseDouble(e.peekLast()+s);
                }catch (Exception e){
                    return;
                }
                String tmp=e.pollLast();
                e.add(tmp+s);
            }
        }

        if(typeCurrent==FUNC){
            e.add(s);
            e.add("(");
            bracketsCount++;
            return;
        }

        if(typeCurrent==CONST){
            e.add(s);
            return;
        }

    }

    //Метод служит для удаления элемента из выражения
    public void removeFromExpression() {

        //Из пустого выражения нельзя ничего удалить
        if(e.size()==1)return;

        //Получаем последний элемент выражения и узнаем его тип
        String s=e.pollLast();
        int typeLast=getTypeElement(s);

        //В зависимости от типа элемента производим его удаление
        if(typeLast==OPEN_BRACKET){
            if(getTypeElement(e.peekLast())==FUNC)e.pollLast();
            bracketsCount--;
            return;
        }

        if(typeLast==CLOSE_BRACKET){
            bracketsCount++;
            return;
        }

        if(typeLast==NUMBER){
            if(s.length()>0){
                s=s.substring(0,s.length()-1);
            }
            if(s.length()>0)e.add(s);
        }

    }

    //Служит для вычисления значения выражения
    public void calculateExpression() throws Exception{

        //Проверка возможности вычислить выражение
        int typeLast=getTypeElement(e.peekLast());
        if(typeLast==START)throw new Exception("Введите выражение");
        if((typeLast!=CLOSE_BRACKET) & (typeLast!=NUMBER) & (typeLast!=CONST))throw new Exception("Выражение не завершено...");
        if(bracketsCount!=0)throw new Exception("Закройте незакрытые скобки");

        //Создаем вспомогательный массив, с которым будем работать при вычислении значения
        ArrayList<String> a=new ArrayList<>(e);

        //Первый этап - замена имен констант их значениями
        for (int i=0;i<a.size();i++)if(getTypeElement(a.get(i))==CONST)a.set(i,(""+constants.getValue(a.get(i))));

        //Второй этап - итеративное вычисление значения
        System.out.println("Начало вычисления выражения");
        while (true){

            //Замена унарных минусов на отрицательные числа

            //Удаление пар скобок, внутри которых содержатся одиночные числа

            //Вычисление значений функций. В данном месте возможно появление исключения для функций, аргумент которых оказался некорректен

            //Проверка условия выхода из цикла: выражение должно сократиться до одного числа - оно и будет результатом вычисления

            //Поиск операции с наивысшим рангом

            //Вычисление результата наиболее высокоранговой операции. В данном месте возможно появление исключения при выполнении деления на 0

            break;

        }

        //Отбрасывание лишних нулей после запятой в случае получения результата, являющегося целым числом

        //Запись результата в выражение

    }

    //Метод служит для полной очистки выражения. Вызвращает пустую строку
    public void clear() {
        e.clear();
        e.add("_start");
        bracketsCount=0;
    }

    //Возвращает тип переданного ему аргумента
    private int getTypeElement(String s){

        if(s.equals("_start"))return START;

        if(s.equals("("))return OPEN_BRACKET;

        if(s.equals(")"))return CLOSE_BRACKET;

        if(s.equals("-"))return MINUS;

        if("*+^/".contains(s) & (s.length()==1))return OPERATOR;

        if(s.equals("."))return NUMBER;
        try {
            Double.parseDouble(s);
            return NUMBER;
        }catch (Exception e){}

        for (String[] f: functions.getFuncNames()){
            if (s.equals(f[0]))return FUNC;
        }

        for (String[] c: constants.getConstNames()){
            if(s.equals(c[0]))return CONST;
        }

        return UNKNOWN;

    }

    @Override
    public String toString() {
        String s="";
        for(String t: e){
            if(getTypeElement(t)==START)continue;
            s+=t;
        }
        return s;
    }
    
}

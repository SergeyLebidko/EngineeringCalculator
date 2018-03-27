package engineeringcalculator;

import java.util.LinkedList;

//Класс необходим для разбиения вводимого выражения на отдельные элементы и логического контроля ввода
public class Expression {

    private final Functions functions;
    private final Constants constants;
    private final Calculator calculator;

    private LinkedList<String> e;    //Стек для хранения выражения

    //Блок типов элементов выражения
    private final int START=0;            //Тип для элемента _start, обозначающего начало выражения
    private final int OPEN_BRACKET=1;     // (
    private final int CLOSE_BRACKET=2;    // )
    private final int MINUS=4;            // - (знак минус в силу некоторых особенностей использования требует отдельного типа)
    private final int OPERATOR=5;         // *, +, /, ^
    private final int NUMBER=6;           // 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, .
    private final int FUNC=7;             //Функции
    private final int CONST=8;            //Константы
    private final int UNKNOWN=9;          //Тип неизвестен

    public Expression(Functions functions, Constants constants) {
        this.functions = functions;
        this.constants = constants;
        calculator = new Calculator(functions, constants);
        e=new LinkedList<>();
        e.add("_start");
    }

    //Метод служит для добавления элемента в выражение
    public void addToExpression(String s) {

    }

    //Метод служит для удаления элемента из выражения
    public void removeFromExpression() {

    }

    //Служит для вычисления значения выражения
    public void calculateExpression() throws Exception{

    }

    //Метод служит для полной очистки выражения. Вызвращает пустую строку
    public void clear() {

    }

    //Возвращает тип переданного ему аргумента
    private int getTypeElement(String s){

        if(s.equals("_start"))return START;

        if(s.equals("("))return OPEN_BRACKET;

        if(s.equals(")"))return CLOSE_BRACKET;

        if(s.equals("-"))return MINUS;

        if("*+^/".contains(s) & (s.length()==1))return OPERATOR;

        if("0123456789.".contains(s) & (s.length()==1))return NUMBER;

        for (String[] f: functions.getFuncNames()){
            if (s.toLowerCase().equals(f[0]))return FUNC;
        }

        for (String[] c: constants.getConstNames()){
            if(s.toLowerCase().equals(c[0]))return CONST;
        }

        return UNKNOWN;

    }

    @Override
    public String toString() {
        return "";
    }
    
}

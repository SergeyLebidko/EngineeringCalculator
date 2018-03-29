package engineeringcalculator;

import java.util.LinkedList;

//Класс необходим для вычисления значений выражений
public class Calculator {

    private final Functions functions;
    private final Constants constants;

    public Calculator(Functions functions, Constants constants) {
        this.functions = functions;
        this.constants = constants;
    }

    //Метод вычисляет переданное ему выражение
    public double Calculate(LinkedList<String> e) throws Exception{
        return 123.456;    //Фиктивный возврат результата
    }

}

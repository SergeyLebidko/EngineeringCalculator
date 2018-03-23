package engineeringcalculator;

//Класс необходим для вычисления значений выражений
public class Calculator {

    private final Functions functions;
    private final Constants constants;

    public Calculator(Functions functions, Constants constants) {
        this.functions = functions;
        this.constants = constants;
    }

}

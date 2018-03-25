package engineeringcalculator;

//Класс необходим для разбиения вводимого выражения на отдельные элементы и логического контроля ввода
public class Expression {

    private final Functions functions;
    private final Constants constants;
    private final Calculator calculator;

    private String e="";    //Временная фиктивная переменная для хранения выражения

    public Expression(Functions functions, Constants constants) {
        this.functions = functions;
        this.constants = constants;
        calculator = new Calculator(functions, constants);
    }

    //Метод служит для добавления элемента в выражение
    public void addToExpression(String s) {
        e+=s;    //Фиктивное добавление элемента в выражение
    }

    //Метод служит для удаления элемента из выражения
    public void removeFromExpression() {
        //Фиктивное удаление элемента из выражения
        if(e.length()>0){
            e=e.substring(0,e.length()-1);
        }
    }

    //Служит для вычисления значения выражения
    public void calculateExpression() throws Exception{
        e="123";    //Фиктивное вычисление выражения
    }

    //Метод служит для полной очистки выражения. Вызвращает пустую строку
    public void clear() {
        e="";    //Фиктивная очистка выражения
    }

    @Override
    public String toString() {
        return e;    //Фиктивное преобразование выражения в строку
    }
    
}

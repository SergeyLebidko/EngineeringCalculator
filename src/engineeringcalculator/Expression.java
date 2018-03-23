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

    //Метод служит для добавления элемента в выражение. Возвращает обновленное выражение
    public String addToExpression(String s) throws Exception {
        if(!s.equals("="))e+=s;              //Фиктивное добавление нового элемента в выражение
        if(s.equals("="))e="1234567890";     //Фиктивное "вычисление" выражения
        return e;
    }

    //Метод служит для удаления элемента из выражения. Возвращает обновленное выражение
    public String removeFromExpression() {
        //Фиктивное удаление элемента из выражения
        if(e.length()>0){
            e=e.substring(0,e.length()-1);
        }
        return e;
    }

    @Override
    public String toString() {
        return e;    //Фиктивное преобразование выражения в строку
    }

    //Метод служит для полной очистки выражения. Вызвращает пустую строку
    public void clear() {
        e="";    //Фиктивная очистка выражения
    }
}

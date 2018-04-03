package engineeringcalculator;

//Класс необходим для реализации поддержки функций
public class Functions {

    private boolean isRadianEnabled = true;    //Если равен true, то аргументы тригонометрических функций будут выражены в радианах

    private String[][] funcNames = {
            {"sin", "Синус угла"},
            {"cos", "Косинус угла"},
            {"tan", "Тангенс угла"},
            {"ctan", "Котангенс угла"},
            {"ln", "Натуральный логарифм числа"},
            {"log", "Логарифм числа по основанию 10"},
            {"exp", "Экспонента числа"},
            {"sqrt", "Квадратный корень числа"},
            {"abs", "Модуль числа"}
    };

    //Метод устанавливает правило интерпретации аргумета тригонометрическими функциями
    public void setRadianEnabled(boolean opt) {
        isRadianEnabled = opt;
    }

    //Метод возвращает текущий способ интерпретации аргумента тригонометрических функций
    public boolean isRadianEnabled() {
        return isRadianEnabled;
    }

    //Метод, возвращающий результат выполнения функции в зависимости от переданного имени и аргумента
    public double execute(String funcName, double x) throws Exception {
        funcName = funcName.toLowerCase();
        switch (funcName) {
            case "sin":
                return sin(x);
            case "cos":
                return cos(x);
            case "tan":
                return tan(x);
            case "cotan":
                return cotan(x);
            case "abs":
                return abs(x);
            case "sqrt":
                return sqrt(x);
            case "ln":
                return ln(x);
            case "log":
                return log(x);
            case "exp":
                return exp(x);
            default:
                throw new Exception("Функция " + funcName + " не определена");
        }
    }

    //Метод, возвращающий массив с описанием функций
    public String[][] getFuncNames() {
        return funcNames;
    }

    //Блок методов, реализующих доступные математические функции
    private double sin(double x) {
        if (!isRadianEnabled) x = Math.toRadians(x);
        return Math.sin(x);
    }

    private double cos(double x) {
        if (!isRadianEnabled) x = Math.toRadians(x);
        return Math.cos(x);
    }

    private double tan(double x) throws Exception {
        if (!isRadianEnabled) x = Math.toRadians(x);
        double c = Math.cos(x);
        double s = Math.sin(x);
        if (Math.abs(s) == 1) throw new Exception("Тангенс угла " + x + " не определен");
        return s / c;
    }

    private double cotan(double x) throws Exception {
        if (!isRadianEnabled) x = Math.toRadians(x);
        double c = Math.cos(x);
        double s = Math.sin(x);
        if (Math.abs(c) == 1) throw new Exception("Котангенс угла " + x + " не определен");
        return c / s;
    }

    private double abs(double x) {
        return Math.abs(x);
    }

    private double sqrt(double x) throws Exception {
        if (x < 0) throw new Exception("Нельзя вычислить квадратный корень отрицательного аргумента");
        return Math.sqrt(x);
    }

    private double ln(double x) throws Exception {
        if (x == 0) throw new Exception("Нельзя вычислить логарифм нуля");
        if (x < 0) throw new Exception("Нельзя вычислить натуральный логарифм отрицательного числа");
        return Math.log(x);
    }

    private double log(double x) throws Exception {
        if (x == 0) throw new Exception("Нельзя вычислить логарифм нуля");
        if (x < 0) throw new Exception("Нельзя вычислить десятичный логарифм отрицательного числа");
        return Math.log10(x);
    }

    private double exp(double x) {
        return Math.exp(x);
    }
}

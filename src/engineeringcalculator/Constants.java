package engineeringcalculator;

//Класс необходим для реализации поддержки констант
public class Constants {

    private String[][] constNames={
            {"Pi", "Число Пи"},
            {"e", "Число е"}
    };

    //Метод, возвращающий значение константы по её имени
    public double execute(String constName) throws Exception {
        constName=constName.toLowerCase();
        switch (constName){
            case "pi": return pi();
            case "e": return  e();
            default: throw new Exception("Константа "+constName+" не определена");
        }
    }

    //Метод, возвращающий массив с описанием доступных констант
    public String[][] getConstNames() {
        return constNames;
    }

    //Блок методов, возвращающих значения констант
    private double pi(){
        return Math.PI;
    }

    private double e(){
        return Math.E;
    }
}

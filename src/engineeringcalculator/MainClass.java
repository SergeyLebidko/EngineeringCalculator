package engineeringcalculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainClass {

    //Основные параметры окна
    private JFrame frm;
    private final int W=900;
    private final int H=450;
    private final int minW=800;
    private final int minH=400;

    //Объекты, необходимые для работы с функциями и константами
    private final Functions functions=new Functions();
    private final Constants constants=new Constants();

    //Цветовые константы
    private final Color color1 = new Color(43, 189, 230);   //Используется для цвета кнопки переключения "градусы/радианы" когда выбраны радианы
    private final Color color2 = new Color(105, 230, 78);   //Используется для цвета кнопки переключения "градусы/радианы" когда выбраны градусы
    private final Color color3 = new Color(180,180,180);    //Используется для функциональных кнопок, кнопок констант, кнопок арифметических операций, а также для скобок
    private final Color color4 = new Color(210,210,210);    //Используется для кнопок цифрового блока
    private final Color color5 = new Color(255, 45, 8);     //Используется для кнопок удаления и очистки

    //Элементы интерфейса
    private final JLabel exprLb;
    private final JTextField exprField;

    public MainClass() {

        //Следующий код нужен для включения отображения компонентов интерфейса в стиле Windows 10
        /*String laf=UIManager.getSystemLookAndFeelClassName();
        try {
            UIManager.setLookAndFeel(laf);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex){
            JOptionPane.showMessageDialog(null, "Возникла ошибка при попытке переключить стиль интерфейса. Работа программы будет прекращена", "Ошибка", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }*/

        //Создаем главное окно
        frm=new JFrame();
        frm.setLayout(new BorderLayout());
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setTitle("Инженерный калькулятор");
        frm.setSize(W,H);
        int xPos=Toolkit.getDefaultToolkit().getScreenSize().width/2-W/2;
        int yPos=Toolkit.getDefaultToolkit().getScreenSize().height/2-H/2;
        frm.setLocation(xPos,yPos);
        frm.setMinimumSize(new Dimension(minW,minH));

        //Создаем вспомогательые панели верхнего блока
        Box topPane=Box.createVerticalBox();
        topPane.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
        JPanel b0=new JPanel(new FlowLayout(FlowLayout.RIGHT));
        b0.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
        Box b1=Box.createHorizontalBox();
        b1.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));

        //Создаем элементы отображения информации (метку вывода выражения и табло калькулятора)
        exprLb =new JLabel(" ");
        exprLb.setFont(new Font(null,Font.PLAIN,14));
        exprField =new JTextField();
        exprField.setText("");
        exprField.setToolTipText("Введите выражение");
        exprField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        exprField.setBackground(Color.WHITE);
        exprField.setHorizontalAlignment(JTextField.RIGHT);
        exprField.setFont(new Font(null,Font.PLAIN,28));
        exprField.setEditable(false);

        //Создаем кнопку переключения "радианы/градусы" и кнопку очистки табло
        JButton radOptionsBtn=new JButton("Rad");
        radOptionsBtn.setToolTipText("Аргументы тригонометрических функций выражены в радианах");
        radOptionsBtn.setBackground(color1);
        radOptionsBtn.setFocusPainted(false);
        radOptionsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(functions.isRadianEnabled()){
                    radOptionsBtn.setText("Grad");
                    radOptionsBtn.setBackground(color2);
                    radOptionsBtn.setToolTipText("Аргументы тригонометрических функций выражены в градусах");
                    functions.setRadianEnabled(false);
                    return;
                }
                if(!functions.isRadianEnabled()){
                    radOptionsBtn.setText("Rad");
                    radOptionsBtn.setBackground(color1);
                    radOptionsBtn.setToolTipText("Аргументы тригонометрических функций выражены в радианах");
                    functions.setRadianEnabled(true);
                    return;
                }
            }
        });
        JButton clearBtn=new JButton("CE");
        clearBtn.setToolTipText("Очистить выражение");
        clearBtn.setBackground(color5);
        clearBtn.setFocusPainted(false);

        //Собираем верхнюю панель
        b0.add(Box.createHorizontalGlue());
        b0.add(exprLb);
        b1.add(radOptionsBtn);
        b1.add(Box.createHorizontalStrut(5));
        b1.add(clearBtn);
        b1.add(Box.createHorizontalStrut(10));
        b1.add(exprField);
        topPane.add(b0);
        topPane.add(b1);
        //topPane.add(Box.createVerticalStrut(5));

        //Добавляем верхнюю панель в главное окно
        frm.add(topPane, BorderLayout.NORTH);

        //Создаем элементы нижнего блока
        Box bottomPane=Box.createHorizontalBox();
        bottomPane.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
        JPanel p0=new JPanel(new GridLayout(0,3, 5,5));
        p0.setBorder(BorderFactory.createEmptyBorder(0,0,0,3));
        JPanel p1=new JPanel(new GridLayout(0,5,5,5));
        p1.setBorder(BorderFactory.createEmptyBorder(0,3,0,0));

        //Добавляем в интерфейс кнопки функций
        String[][] keys=functions.getFuncNames();
        JButton btn;
        for (String[] r: keys){
            btn=new JButton(r[0]);
            btn.setFont(new Font(null, Font.PLAIN,16));
            btn.setToolTipText(r[1]);
            btn.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
            btn.addActionListener(aListener);
            btn.setBackground(color3);
            p0.add(btn);
        }

        //Добавляем в интерфейс кнопки констант
        keys=constants.getConstNames();
        for (String[] r: keys){
            btn=new JButton(r[0]);
            btn.setFont(new Font(null, Font.PLAIN,16));
            btn.setToolTipText(r[1]);
            btn.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
            btn.addActionListener(aListener);
            btn.setBackground(color3);
            p0.add(btn);
        }

        //Добавляем кнопки цифр, арифметических операций, кнопку стирания введенных элементов и кнопку "равно"
        keys=new String[][]{
                {"7", ""},{"8", ""},{"9", ""},{"+", ""},{"^", "Возведение в степень"},
                {"4", ""},{"5", ""},{"6", ""},{"-", ""},{"(", ""},
                {"1", ""},{"2", ""},{"3", ""},{"*", ""},{")", ""},
                {"0", ""},{".", ""},{"=", ""},{"/", ""},{"<<<", "Удалить"},
        };
        for (String[] r: keys){
            btn=new JButton(r[0]);
            btn.setFont(new Font(null, Font.PLAIN,16));
            btn.setToolTipText(r[1]);
            btn.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
            btn.addActionListener(aListener);
            if("0123456789.".contains(r[0]))btn.setBackground(color4);
            if("/*-+^()".contains(r[0]))btn.setBackground(color3);
            if(r[0].equals("="))btn.setBackground(color1);
            if(r[0].equals("<<<"))btn.setBackground(color5);
            p1.add(btn);
        }

        //Собираем нижнюю панель
        bottomPane.add(p0);
        bottomPane.add(p1);

        //Добавляем нижнюю панель в главное окно
        frm.add(bottomPane, BorderLayout.CENTER);

        //Выводим окно на экран
        frm.setVisible(true);
    }

    //Обработчик нажатия на кнопки
    private ActionListener aListener=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            exprLb.setText(((JButton)e.getSource()).getActionCommand());
            exprField.setText(((JButton)e.getSource()).getActionCommand());
        }
    };

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainClass();
            }
        });
    }

}

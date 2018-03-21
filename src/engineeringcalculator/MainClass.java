package engineeringcalculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainClass {

    //Основные параметры окна
    private JFrame frm;
    private final int W=800;
    private final int H=400;

    //Цветовые константы
    private final Color color1=new Color(215,215,215);

    //Элементы интерфейса
    private final JLabel exprLb;
    private final JTextField exprField;

    private final JButton clearBtn;
    private final JToggleButton radOptionsBtn;


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
        frm.setMinimumSize(new Dimension(W,H));

        //Создаем вспомогательые панели верхнего блока
        Box topPane=Box.createVerticalBox();
        topPane.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
        JPanel b0=new JPanel(new FlowLayout(FlowLayout.RIGHT));
        b0.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
        Box b1=Box.createHorizontalBox();
        b1.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));

        //Создаем элементы отображения информации (метку вывода выражения и табло калькулятора)
        exprLb =new JLabel("Тестовая строка. Должна быть убрана. Очень длинная тестовая строка. Но все равно должна быть рано или поздно убрана. Но сейчас надо испытать ее на очень длинные строки...");
        exprLb.setFont(new Font(null,Font.PLAIN,14));
        exprField =new JTextField();
        exprField.setText("Тестовый текст. Должен быть убран");
        exprField.setToolTipText("Введите выражение");
        exprField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        exprField.setBackground(Color.WHITE);
        exprField.setHorizontalAlignment(JTextField.RIGHT);
        exprField.setFont(new Font(null,Font.PLAIN,18));
        exprField.setEditable(false);
        radOptionsBtn=new JToggleButton("Rad",true);
        radOptionsBtn.setToolTipText("Аргументы тригонометрических функций выражены в радианах");
        radOptionsBtn.setBackground(color1);
        radOptionsBtn.setFocusPainted(false);
        radOptionsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(radOptionsBtn.isSelected()){
                    radOptionsBtn.setText("Rad");
                    radOptionsBtn.setToolTipText("Аргументы тригонометрических функций выражены в радианах");
                }
                if(!radOptionsBtn.isSelected()){
                    radOptionsBtn.setText("Grad");
                    radOptionsBtn.setToolTipText("Аргументы тригонометрических функций выражены в градусах");
                }
            }
        });
        clearBtn=new JButton("CE");
        clearBtn.setToolTipText("Очистить выражение");
        clearBtn.setBackground(color1);
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
        topPane.add(Box.createVerticalStrut(15));

        //Добавляем верхнюю панель в главное окно
        frm.add(topPane, BorderLayout.NORTH);

        //Выводим окно на экран
        frm.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainClass();
            }
        });
    }

}

package engineeringcalculator;

import javax.swing.*;
import java.awt.*;

public class MainClass {

    private JFrame frm;
    private final int W=800;
    private final int H=400;

    private final JLabel resLb;
    private final JTextField tab;

    private final JButton clearBtn;


    public MainClass() {
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
        Box b0=Box.createHorizontalBox();
        b0.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
        Box b1=Box.createHorizontalBox();
        b1.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));

        //Создаем элементы отображения информации (метку вывода выражения и табло калькулятора)
        resLb=new JLabel("Тестовая строка. Должна быть убрана");
        resLb.setFont(new Font(null,Font.PLAIN,14));
        tab=new JTextField();
        tab.setText("Тестовый текст. Должен быть убран");
        tab.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        tab.setHorizontalAlignment(JTextField.RIGHT);
        tab.setFont(new Font(null,Font.PLAIN,18));
        tab.setEditable(false);
        clearBtn=new JButton("CE");
        clearBtn.setBackground(Color.RED);

        //Собираем верхнюю панель
        b0.add(Box.createHorizontalGlue());
        b0.add(resLb);
        b1.add(clearBtn);
        b1.add(Box.createHorizontalStrut(10));
        b1.add(tab);
        topPane.add(b0);
        topPane.add(b1);
        topPane.add(Box.createVerticalStrut(15));

        //Добавляем верхнюю панель в главное окно
        frm.add(topPane, BorderLayout.NORTH);

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

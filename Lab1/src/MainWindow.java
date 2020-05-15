import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    public MainWindow(){
        super("Ізоморфне перетворення зірковий многокутників");
        JFrame.setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        initComponents();
        setSize(600, 350);
        setLocation(450, 100);
        setResizable(false);
        setVisible(true);
    }

    public void initComponents(){
        setLayout(new GridLayout(9, 1));

        JLabel titleRow = new JLabel("<html><p style='padding-left: 150px; font-size: 16px'>Зіркові многокутники</p></html>");

        JLabel nRow = new JLabel();
        nRow.setLayout(new GridLayout(1, 4));
        JLabel n = new JLabel("<html><p style='padding-left: 6px; font-size: 12px'>Кількість кутів n: </p></html>");
        JTextField nInput = new JTextField();
        nRow.add(Box.createVerticalBox());
        nRow.add(n);
        nRow.add(nInput);
        nRow.add(Box.createVerticalBox());

        JLabel ttlsRow = new JLabel();
        ttlsRow.setLayout(new GridLayout(1, 7));
        JLabel label1 = new JLabel("<html><p style='padding-left: 20px; font-size: 13px' color='blue'>Z1</p></html>");
        JLabel label2 = new JLabel("<html><p style='padding-left: 20px; font-size: 13px' color='red'>Z2</p></html>");
        ttlsRow.add(Box.createVerticalBox());
        ttlsRow.add(label1);
        ttlsRow.add(Box.createVerticalBox());
        ttlsRow.add(Box.createVerticalBox());
        ttlsRow.add(Box.createVerticalBox());
        ttlsRow.add(label2);
        ttlsRow.add(Box.createVerticalBox());

        JLabel xRow = new JLabel();
        xRow.setLayout(new GridLayout(1, 5));
        JLabel z1x = new JLabel("<html><p style='padding-left: 3px'>x кола навколо Z1: </p></html>");
        JTextField z1xInput = new JTextField();
        JLabel z2x = new JLabel("x кола навколо Z2: ");
        JTextField z2xInput = new JTextField();
        xRow.add(z1x);
        xRow.add(z1xInput);
        xRow.add(Box.createVerticalBox());
        xRow.add(z2x);
        xRow.add(z2xInput);

        JLabel yRow = new JLabel();
        yRow.setLayout(new GridLayout(1, 5));
        JLabel z1y = new JLabel("<html><p style='padding-left: 3px'>y кола навколо Z1: </p></html>");
        JTextField z1yInput = new JTextField();
        JLabel z2y = new JLabel("y кола навколо Z2: ");
        JTextField z2yInput = new JTextField();
        yRow.add(z1y);
        yRow.add(z1yInput);
        yRow.add(Box.createVerticalBox());
        yRow.add(z2y);
        yRow.add(z2yInput);

        JLabel angleRow = new JLabel();
        angleRow.setLayout(new GridLayout(1, 5));
        JLabel z1Angle = new JLabel("<html><p style='padding-left: 8px'>Кут повороту Z1: </p></html>");
        JTextField z1AngleInput = new JTextField();
        JLabel z2Angle = new JLabel("<html><p style='padding-left: 8px'>Кут повороту Z2: </p></html>");
        JTextField z2AngleInput = new JTextField();
        angleRow.add(z1Angle);
        angleRow.add(z1AngleInput);
        angleRow.add(Box.createVerticalBox());
        angleRow.add(z2Angle);
        angleRow.add(z2AngleInput);

        JLabel btnRow = new JLabel();
        btnRow.setLayout(new GridLayout(1, 3));
        JButton btn = new JButton("Знайти перетворення");
        btn.addActionListener(e -> {
            if (!nInput.getText().equals("") && !z1xInput.getText().equals("") && !z1yInput.getText().equals("") && !z2xInput.getText().equals("")
                    && !z2yInput.getText().equals("") && !z1AngleInput.getText().equals("") && !z2AngleInput.getText().equals("")){
                int nTrans = Integer.parseInt(nInput.getText());
                int xZ1 = Integer.parseInt(z1xInput.getText());
                int yZ1 = Integer.parseInt(z1yInput.getText());
                int xZ2 = Integer.parseInt(z2xInput.getText());
                int yZ2 = Integer.parseInt(z2yInput.getText());
                int angleZ1 = Integer.parseInt(z1AngleInput.getText());
                int angleZ2 = Integer.parseInt(z2AngleInput.getText());
                if (nTrans < 5){
                    JOptionPane.showMessageDialog(null, "<html><p style='font-size: 17px'>n повинне бути більше 4</p></html>");
                } else {
                    new IzomorphStellarTransformation(nTrans, xZ1, yZ1, angleZ1, xZ2, yZ2, angleZ2);
                }
            } else {
                JOptionPane.showMessageDialog(null, "<html><p style='font-size: 17px'>Заповніть всі поля</p></html>");
            }

        });
        btnRow.add(Box.createVerticalBox());
        btnRow.add(btn);
        btnRow.add(Box.createVerticalBox());

        add(titleRow);
        add(nRow);
        add(ttlsRow);
        add(xRow);
        add(yRow);
        add(angleRow);
        add(Box.createHorizontalBox());
        add(btnRow);
        add(Box.createHorizontalBox());
    }

    public static void main(String[] args) {
        new MainWindow();
    }
}

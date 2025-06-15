package GuiForms;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame{
    private JPanel JPanel0;
    private JButton MainLoginButton;
    private JButton MainCloseButton;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JLabel mainTitleLabel;
    private JLabel mainIndicationLabel;

    public MainWindow() {
        setTitle("Aplikacja Wizyt Lekarskich");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setContentPane(JPanel0);

        StyleUtil.stylePanel(JPanel0);
        StyleUtil.stylePanel(panel1);
        StyleUtil.stylePanel(panel2);
        StyleUtil.stylePanel(panel3);
        StyleUtil.styleButton(MainLoginButton);
        StyleUtil.styleButton(MainCloseButton);
        StyleUtil.styleLabel(mainTitleLabel);
        StyleUtil.styleLabel(mainIndicationLabel);

        setVisible(true);

        MainLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginFrame();
            }
        });
        MainCloseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

    }
}

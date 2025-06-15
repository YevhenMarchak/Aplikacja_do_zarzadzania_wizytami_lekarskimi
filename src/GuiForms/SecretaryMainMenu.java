package GuiForms;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SecretaryMainMenu extends JFrame{
    private JPanel Jpanel2;
    private JButton PatientsButton;
    private JButton DoctorsButton;
    private JButton VisitsButton;
    private JButton LogOutButton;
    private JPanel panel0;
    private JPanel panel1;
    private JPanel panel2;
    private JLabel SMMLabel;
    private JLabel smmLabel;

    public SecretaryMainMenu() {
        setTitle("Sekretariat");
        setContentPane(Jpanel2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        StyleUtil.stylePanel(Jpanel2);
        StyleUtil.stylePanel(panel0);
        StyleUtil.stylePanel(panel1);
        StyleUtil.stylePanel(panel2);
        StyleUtil.styleButton(PatientsButton);
        StyleUtil.styleButton(DoctorsButton);
        StyleUtil.styleButton(VisitsButton);
        StyleUtil.styleButton(LogOutButton);
        StyleUtil.styleLabel(SMMLabel);
        StyleUtil.styleLabel(smmLabel);

        setVisible(true);

        PatientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new PatientsForm();
            }
        });
        DoctorsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new DoctorsForm();
            }
        });
        VisitsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new VisitsForm();
            }
        });
        LogOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginFrame();
            }
        });
    }
}

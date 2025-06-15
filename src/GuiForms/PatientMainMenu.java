package GuiForms;

import model.Patient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PatientMainMenu extends JFrame {
    private JPanel Jpanel7;
    private JButton MyDataButton;
    private JButton MyVisistsButton;
    private JButton ReservVisitButton;
    private JButton LogoutButton2;
    private JPanel panel0;
    private JPanel panel2;
    private JPanel panel1;
    private JLabel PMMLabel;
    private JLabel woLabel;

    private Patient patient;


    public PatientMainMenu(Patient patient) {
        this.patient = patient;

        setTitle("Panel pacjenta");
        setContentPane(Jpanel7);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        StyleUtil.stylePanel(Jpanel7);
        StyleUtil.stylePanel(panel0);
        StyleUtil.stylePanel(panel1);
        StyleUtil.stylePanel(panel2);
        StyleUtil.styleButton(MyDataButton);
        StyleUtil.styleButton(MyVisistsButton);
        StyleUtil.styleButton(ReservVisitButton);
        StyleUtil.styleButton(LogoutButton2);
        StyleUtil.styleLabel(PMMLabel);
        StyleUtil.styleLabel(woLabel);

        setVisible(true);
        MyDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MyDataForm(patient);
            }
        });
        MyVisistsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MyVisitsForm(patient);
            }
        });
        ReservVisitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ReservVisitForm(patient);
            }
        });
        LogoutButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginFrame();
            }
        });
    }
}

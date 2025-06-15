package GuiForms;

import model.Patient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyDataForm extends JFrame{
    private JPanel Jpanel8;
    private JButton BackButton;
    private JTextField PatientLNField;
    private JTextField PatientFNField;
    private JTextField PatientpeselField;
    private JTextField PatientMailField;
    private JTextField PatientPhoneField;
    private JPanel panel0;
    private JPanel panel1;
    private JPanel panel2;
    private JLabel MDLabel;

    public MyDataForm(Patient patient) {
        setTitle("Moje dane");
        setContentPane(Jpanel8);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        StyleUtil.stylePanel(Jpanel8);
        StyleUtil.stylePanel(panel0);
        StyleUtil.stylePanel(panel1);
        StyleUtil.stylePanel(panel2);
        StyleUtil.styleButton(BackButton);
        StyleUtil.styleLabel(MDLabel);

        setVisible(true);
        // Ustaw dane pacjenta
        PatientFNField.setText(patient.getFirstName());
        PatientLNField.setText(patient.getLastName());
        PatientpeselField.setText(patient.getPesel());
        PatientMailField.setText(patient.getEmail());
        PatientPhoneField.setText(patient.getPhone());

        // Pola tylko do odczytu
        PatientFNField.setEditable(false);
        PatientLNField.setEditable(false);
        PatientpeselField.setEditable(false);
        PatientMailField.setEditable(false);
        PatientPhoneField.setEditable(false);
        BackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new PatientMainMenu(patient);
            }
        });
    }
}

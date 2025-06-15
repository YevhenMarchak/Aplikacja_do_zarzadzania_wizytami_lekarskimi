package GuiForms;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddPatientCredentialsForm extends JFrame{
    private JPanel Jpanel6;
    private JTextField CredentialsLogintextField;
    private JTextField CredentialsPasswordtextField;
    private JButton SaveCredentialsButton;
    private JButton CredentialsExitButton;
    private JPanel panel0;
    private JPanel panel1;
    private JPanel panel3;
    private JPanel panel4;
    private JPanel panel2;
    private JLabel PSLabel;

    private int patientId;


    public AddPatientCredentialsForm(int patientId) {
            this.patientId = patientId;
            setTitle("Dane logowania pacjenta");
            setContentPane(Jpanel6);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(600, 400);
            setLocationRelativeTo(null);

        StyleUtil.stylePanel(Jpanel6);
        StyleUtil.stylePanel(panel0);
        StyleUtil.stylePanel(panel1);
        StyleUtil.stylePanel(panel2);
        StyleUtil.stylePanel(panel3);
        StyleUtil.stylePanel(panel4);
        StyleUtil.styleButton(CredentialsExitButton);
        StyleUtil.styleButton(SaveCredentialsButton);
        StyleUtil.styleLabel(PSLabel);

        setVisible(true);
        CredentialsExitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new PatientsForm();
            }
        });
        SaveCredentialsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login = CredentialsLogintextField.getText().trim();
                String password = CredentialsPasswordtextField.getText().trim();

                if (login.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Login i hasło są wymagane!", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                UserDAO dao = new UserDAO();
                if (dao.loginExists(login)) {
                    JOptionPane.showMessageDialog(null, "Taki login już istnieje!", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                User user = new User(patientId, login, password, "PATIENT", patientId);
                dao.addUser(user);

                JOptionPane.showMessageDialog(null, "Konto pacjenta utworzone pomyślnie.");
                dispose();
            }
        });
    }
}

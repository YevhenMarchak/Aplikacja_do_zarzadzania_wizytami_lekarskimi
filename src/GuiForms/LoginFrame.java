package GuiForms;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import dao.PatientDAO;
import dao.UserDAO;
import model.Patient;
import model.User;
public class LoginFrame extends JFrame{
    private JPanel Jpanel1;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton singinButton;
    private JButton LoginFrameBackButton;
    private JLabel loginFrameLabel;
    private JPanel panel0;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JPanel panel5;

    public LoginFrame(){
        setTitle("Logowanie");
        setContentPane(Jpanel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        StyleUtil.stylePanel(Jpanel1);
        StyleUtil.stylePanel(panel0);
        StyleUtil.stylePanel(panel1);
        StyleUtil.stylePanel(panel2);
        StyleUtil.stylePanel(panel3);
        StyleUtil.stylePanel(panel4);
        StyleUtil.stylePanel(panel5);
        StyleUtil.styleButton(singinButton);
        StyleUtil.styleButton(LoginFrameBackButton);
        StyleUtil.styleLabel(loginFrameLabel);

        setVisible(true);
        singinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login = textField1.getText().trim();
                String password = new String(passwordField1.getPassword()).trim();

                UserDAO userDAO = new UserDAO();
                User user = userDAO.login(login, password);

                if (user != null) {
                    dispose();

                    if (user.isSecretary()) {
                        new SecretaryMainMenu().setVisible(true);
                    } else if (user.isPatient()) {
                        PatientDAO patientDAO = new PatientDAO();
                        Patient patient = patientDAO.getPatientById(user.getPatientId());
                        if (patient != null) {
                            new PatientMainMenu(patient).setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(null, "Błąd: nie znaleziono danych pacjenta!", "Błąd", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                }else {
                    JOptionPane.showMessageDialog(null, "Nieprawidłowy login lub hasło", "Błąd logowania", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        LoginFrameBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MainWindow();
            }
        });
    }
}

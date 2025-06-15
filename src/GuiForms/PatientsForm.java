package GuiForms;

import dao.PatientDAO;
import model.Patient;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.util.List;

public class PatientsForm extends JFrame{
    private JPanel Jpanel3;
    private JTable Patienttable;
    private JTextField PatientIDtextField;
    private JTextField PatientFirstNametextField;
    private JTextField PatientLastNametextField;
    private JTextField PESELtextField;
    private JButton PatientAddButton;
    private JButton PatientEditButton;
    private JButton PatientDelButton;
    private JButton PatientExitButton;
    private JTextField PatientEmailtextField;
    private JTextField PatientPhonetextField;
    private JScrollPane PatientTableScroll;
    private JPanel panel0;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JLabel SPLabel;

    public PatientsForm() {
        setTitle("Zarządzanie pacjentami");
        setContentPane(Jpanel3);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        StyleUtil.stylePanel(Jpanel3);
        StyleUtil.stylePanel(panel0);
        StyleUtil.stylePanel(panel1);
        StyleUtil.stylePanel(panel2);
        StyleUtil.stylePanel(panel3);
        StyleUtil.styleButton(PatientAddButton);
        StyleUtil.styleButton(PatientDelButton);
        StyleUtil.styleButton(PatientEditButton);
        StyleUtil.styleButton(PatientExitButton);
        StyleUtil.styleLabel(SPLabel);

        setVisible(true);

        refreshPatientTable();

        PatientDAO dao = new PatientDAO();

        Patienttable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = Patienttable.getSelectedRow();
                if (row >= 0) {
                    PatientIDtextField.setText(Patienttable.getValueAt(row, 0).toString());
                    PatientFirstNametextField.setText(Patienttable.getValueAt(row, 1).toString());
                    PatientLastNametextField.setText(Patienttable.getValueAt(row, 2).toString());
                    PESELtextField.setText(Patienttable.getValueAt(row, 3).toString());
                    PatientEmailtextField.setText(Patienttable.getValueAt(row, 4).toString());
                    PatientPhonetextField.setText(Patienttable.getValueAt(row, 5).toString());
                }
            }
        });



        PatientExitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new SecretaryMainMenu();
            }
        });
        PatientAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String firstName = PatientFirstNametextField.getText().trim();
                String lastName = PatientLastNametextField.getText().trim();
                String pesel = PESELtextField.getText().trim();
                String email = PatientEmailtextField.getText().trim();
                String phone = PatientPhonetextField.getText().trim();

                if (firstName.isEmpty() || lastName.isEmpty() || pesel.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Wypełnij wymagane pola (imię, nazwisko, PESEL)");
                    return;
                }else if (!pesel.matches("\\d{11}")) {
                    JOptionPane.showMessageDialog(null, "PESEL musi składać się z dokładnie 11 cyfr!");
                    return;
                }

                Patient patient = new Patient(0, firstName, lastName, phone, email, pesel);



                int id = dao.addPatient(patient);
                refreshPatientTable();
                new AddPatientCredentialsForm(id);


            }
        });


        PatientEditButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(PatientIDtextField.getText().trim());
                    String firstName = PatientFirstNametextField.getText().trim();
                    String lastName = PatientLastNametextField.getText().trim();
                    String pesel = PESELtextField.getText().trim();
                    String email = PatientEmailtextField.getText().trim();
                    String phone = PatientPhonetextField.getText().trim();

                    if (firstName.isEmpty() || lastName.isEmpty() || pesel.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Wypełnij wymagane pola (imię, nazwisko, PESEL)");
                        return;
                    }

                    if (!pesel.matches("\\d{11}")) {
                        JOptionPane.showMessageDialog(null, "PESEL musi składać się z dokładnie 11 cyfr!");
                        return;
                    }

                    Patient patient = new Patient(id, firstName, lastName, phone, email, pesel);
                    dao.updatePatient(patient);
                    refreshPatientTable();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Nieprawidłowy format ID", "Błąd", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        PatientDelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(PatientIDtextField.getText().trim());
                    int confirm = JOptionPane.showConfirmDialog(null, "Czy na pewno chcesz usunąć pacjenta?", "Potwierdź", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        dao.deletePatient(id);
                        refreshPatientTable();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Nieprawidłowy format ID", "Błąd", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        Patienttable.addComponentListener(new ComponentAdapter() {
        });
    }
    private void refreshPatientTable() {
        PatientDAO dao = new PatientDAO();
        List<Patient> patients = dao.getAllPatients();

        String[] columnNames = {"ID", "Imię", "Nazwisko", "PESEL", "Email", "Telefon"};
        Object[][] data = new Object[patients.size()][6];

        for (int i = 0; i < patients.size(); i++) {
            Patient p = patients.get(i);
            data[i][0] = p.getId();
            data[i][1] = p.getFirstName();
            data[i][2] = p.getLastName();
            data[i][3] = p.getPesel();
            data[i][4] = p.getEmail();
            data[i][5] = p.getPhone();
        }

        Patienttable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    }
}

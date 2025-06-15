package GuiForms;

import dao.DoctorDAO;
import model.Doctor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DoctorsForm extends JFrame {
    private JPanel Jpanel4;
    private JTable Doctortable;
    private JTextField DoctorIDtextField;
    private JTextField DoctorFirstNametextField;
    private JTextField DoctorLastNametextField;
    private JTextField DoctroSpecializationtextField;
    private JButton AddDoctorButton;
    private JButton EditDoctorButton;
    private JButton DeleteDoctorButton;
    private JButton ExitDoctorButton;
    private JTextField DoctorPhonetextField;
    private JTextField DoctorEmailtextField;
    private JScrollPane DoctorTableScroll;
    private JPanel panel0;
    private JLabel PSLabel;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;

    private DoctorDAO dao = new DoctorDAO();


    public DoctorsForm() {
        setTitle("Zarządzanie lekarzami");
        setContentPane(Jpanel4);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        StyleUtil.stylePanel(Jpanel4);
        StyleUtil.stylePanel(panel0);
        StyleUtil.stylePanel(panel1);
        StyleUtil.stylePanel(panel2);
        StyleUtil.stylePanel(panel3);
        StyleUtil.styleButton(AddDoctorButton);
        StyleUtil.styleButton(EditDoctorButton);
        StyleUtil.styleButton(DeleteDoctorButton);
        StyleUtil.styleButton(ExitDoctorButton);
        StyleUtil.styleLabel(PSLabel);


        setVisible(true);

        refreshDoctorTable();
        ExitDoctorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new SecretaryMainMenu();
            }
        });

        Doctortable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = Doctortable.getSelectedRow();
            if (selectedRow != -1) {
                DoctorIDtextField.setText(Doctortable.getValueAt(selectedRow, 0).toString());
                DoctorFirstNametextField.setText(Doctortable.getValueAt(selectedRow, 1).toString());
                DoctorLastNametextField.setText(Doctortable.getValueAt(selectedRow, 2).toString());
                DoctroSpecializationtextField.setText(Doctortable.getValueAt(selectedRow, 3).toString());
                DoctorPhonetextField.setText(Doctortable.getValueAt(selectedRow, 4).toString());
                DoctorEmailtextField.setText(Doctortable.getValueAt(selectedRow, 5).toString());
            }
        });

        AddDoctorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstName = DoctorFirstNametextField.getText().trim();
                String lastName = DoctorLastNametextField.getText().trim();
                String specialization = DoctroSpecializationtextField.getText().trim();
                String phone = DoctorPhonetextField.getText().trim();
                String email = DoctorEmailtextField.getText().trim();

                if (firstName.isEmpty() || lastName.isEmpty() || specialization.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Wypełnij wymagane pola (imię, nazwisko, specjalizacja)");
                    return;
                }

                Doctor doctor = new Doctor(0, firstName, lastName, phone, email, specialization);
                dao.addDoctor(doctor);
                refreshDoctorTable();
            }
        });
        DeleteDoctorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = Doctortable.getSelectedRow();
                if (selectedRow != -1) {
                    int doctorId = (int) Doctortable.getValueAt(selectedRow, 0);
                    dao.deleteDoctor(doctorId);
                    refreshDoctorTable();
                } else {
                    JOptionPane.showMessageDialog(null, "Wybierz lekarza do usunięcia");
                }
            }
        });
        EditDoctorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(DoctorIDtextField.getText().trim());
                    String firstName = DoctorFirstNametextField.getText().trim();
                    String lastName = DoctorLastNametextField.getText().trim();
                    String specialization = DoctroSpecializationtextField.getText().trim();
                    String phone = DoctorPhonetextField.getText().trim();
                    String email = DoctorEmailtextField.getText().trim();

                    Doctor doctor = new Doctor(id, firstName, lastName, phone, email, specialization);
                    dao.updateDoctor(doctor);
                    refreshDoctorTable();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Nieprawidłowe ID lekarza");
                }
            }
        });
    }
    private void refreshDoctorTable() {
        List<Doctor> doctors = dao.getAllDoctors();

        String[] columnNames = {"ID", "Imię", "Nazwisko", "Specjalizacja", "Telefon", "Email"};
        Object[][] data = new Object[doctors.size()][6];

        for (int i = 0; i < doctors.size(); i++) {
            Doctor d = doctors.get(i);
            data[i][0] = d.getId();
            data[i][1] = d.getFirstName();
            data[i][2] = d.getLastName();
            data[i][3] = d.getSpecialization();
            data[i][4] = d.getPhone();
            data[i][5] = d.getEmail();
        }

        Doctortable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    }


}

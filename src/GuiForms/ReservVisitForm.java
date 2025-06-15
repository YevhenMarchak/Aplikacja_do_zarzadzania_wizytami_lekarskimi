package GuiForms;

import dao.DoctorDAO;
import dao.VisitDAOImpl;
import dao.VisitStatus;
import model.Doctor;
import model.Patient;
import model.Visit;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ReservVisitForm extends JFrame{
    private JPanel JPanel10;
    private JComboBox DoctorscomboBox;
    private JTextField ReserveDatatextField;
    private JButton MakeReservButton;
    private JButton ResrvBackButton;
    private JLabel ZWLabel;
    private JPanel panel0;
    private JPanel panel1;
    private JPanel panel2;

    private final Patient currentPatient;

    public ReservVisitForm(Patient patient) {
        this.currentPatient = patient;

        setTitle("Rezerwacja wizyty");
        setContentPane(JPanel10);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        StyleUtil.stylePanel(JPanel10);
        StyleUtil.stylePanel(panel0);
        StyleUtil.stylePanel(panel1);
        StyleUtil.stylePanel(panel2);
        StyleUtil.styleButton(MakeReservButton);
        StyleUtil.styleButton(ResrvBackButton);
        StyleUtil.styleLabel(ZWLabel);

        setVisible(true);

        loadDoctors();


        ResrvBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new PatientMainMenu(currentPatient);
            }
        });
        MakeReservButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dateInput = ReserveDatatextField.getText().trim();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

                try {
                    LocalDateTime dateTime = LocalDateTime.parse(dateInput, formatter);

                    if (dateTime.isBefore(LocalDateTime.now())) {
                        JOptionPane.showMessageDialog(null, "Nie można zarezerwować wizyty w przeszłości!", "Błąd", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    Doctor selectedDoctor = (Doctor) DoctorscomboBox.getSelectedItem();
                    if (selectedDoctor == null) {
                        JOptionPane.showMessageDialog(null, "Wybierz lekarza.", "Błąd", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    VisitDAOImpl dao = new VisitDAOImpl();

                    if (dao.hasDoctorConflict(selectedDoctor.getId(), dateTime)) {
                        JOptionPane.showMessageDialog(null, "Wybrany lekarz ma już wizytę w tym czasie lub w przedziale ±30 minut!", "Konflikt terminów", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    Visit visit = new Visit(0, currentPatient, selectedDoctor, dateTime, VisitStatus.ZAPLANOWANA);
                    visit.setNotes(""); // pacjent nie dodaje notatki

                    dao.addVisit(visit);

                    JOptionPane.showMessageDialog(null, "Wizyta zarezerwowana pomyślnie!");
                    dispose();
                    new PatientMainMenu(currentPatient);

                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(null, "Błędny format daty! Użyj: yyyy-MM-dd HH:mm", "Błąd", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    private void loadDoctors() {
        DoctorscomboBox.removeAllItems();
        List<Doctor> doctors = new DoctorDAO().getAllDoctors();
        for (Doctor doctor : doctors) {
            DoctorscomboBox.addItem(doctor);
        }
    }
}

package GuiForms;

import dao.VisitDAOImpl;
import model.Patient;
import model.Visit;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MyVisitsForm extends JFrame{
    private JPanel JPanel9;
    private JTable MyVisitstable;
    private JTextArea DoctorsNotetextArea;
    private JButton BackVisitsButton;
    private JLabel MWLabel;
    private JPanel panel0;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;

    private VisitDAOImpl visitDAO = new VisitDAOImpl();

    public MyVisitsForm(Patient patient) {
        setTitle("Moje wizyty");
        setContentPane(JPanel9);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(600, 400);
        setLocationRelativeTo(null);

        StyleUtil.stylePanel(JPanel9);
        StyleUtil.stylePanel(panel0);
        StyleUtil.stylePanel(panel1);
        StyleUtil.stylePanel(panel2);
        StyleUtil.stylePanel(panel3);
        StyleUtil.styleButton(BackVisitsButton);
        StyleUtil.styleLabel(MWLabel);

        setVisible(true);

        refreshVisitTable(patient);

        MyVisitstable.getSelectionModel().addListSelectionListener(e -> {
            int row = MyVisitstable.getSelectedRow();
            if (row >= 0) {
                String note = (String) MyVisitstable.getValueAt(row, 5);
                DoctorsNotetextArea.setText(note != null ? note : "");
            }
        });
        BackVisitsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new PatientMainMenu(patient);
            }
        });
    }
    private void refreshVisitTable(Patient patient) {
        List<Visit> visits = visitDAO.getAllVisits();
        List<Visit> myVisits = visits.stream()
                .filter(v -> v.getPatient().getId() == patient.getId())
                .toList();

        String[] columns = {"ID", "Data", "Status", "Lekarz", "Specjalizacja", "Notatka"};
        Object[][] data = new Object[myVisits.size()][6];

        for (int i = 0; i < myVisits.size(); i++) {
            Visit v = myVisits.get(i);
            data[i][0] = v.getId();
            data[i][1] = v.getDateTime().toString();
            data[i][2] = v.getStatus().name();
            data[i][3] = v.getDoctor().getFirstName() + " " + v.getDoctor().getLastName();
            data[i][4] = v.getDoctor().getSpecialization();
            data[i][5] = v.getNotes();
        }

        MyVisitstable.setModel(new javax.swing.table.DefaultTableModel(data, columns));
    }
}

package GuiForms;

import dao.VisitDAOImpl;
import dao.VisitStatus;
import model.Visit;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class VisitsForm extends JFrame{
    private JPanel Jpanel5;
    private JTable Visitstable;
    private JComboBox VisitStatuscomboBox;
    private JButton ChangeStatusButton;
    private JButton RefreshButton;
    private JButton VisitsExitButton;
    private JTextArea VisitNotetextArea;
    private JPanel panel0;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JLabel PSLabel;

    private VisitDAOImpl visitDAO = new VisitDAOImpl();
    private List<Visit> visits;

    public VisitsForm() {
        setTitle("Zarządzanie wizytami");
        setContentPane(Jpanel5);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        StyleUtil.stylePanel(Jpanel5);
        StyleUtil.stylePanel(panel0);
        StyleUtil.stylePanel(panel1);
        StyleUtil.stylePanel(panel2);
        StyleUtil.stylePanel(panel3);
        StyleUtil.styleButton(ChangeStatusButton);
        StyleUtil.styleButton(RefreshButton);
        StyleUtil.styleButton(VisitsExitButton);
        StyleUtil.styleLabel(PSLabel);

        setVisible(true);

        VisitStatuscomboBox.setModel(new DefaultComboBoxModel<>(VisitStatus.values()));

        refreshTable();
        VisitsExitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new SecretaryMainMenu();
            }
        });
        Visitstable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = Visitstable.getSelectedRow();
                if (row != -1) {
                    Visit selected = visits.get(row);
                    VisitStatuscomboBox.setSelectedItem(selected.getStatus());
                    VisitNotetextArea.setText(selected.getNotes());
                }
            }
        });
        RefreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTable();
            }
        });
        ChangeStatusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = Visitstable.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "Wybierz wizytę do edycji.");
                    return;
                }

                Visit visit = visits.get(row);
                visit.setStatus((VisitStatus) VisitStatuscomboBox.getSelectedItem());
                visit.setNotes(VisitNotetextArea.getText());

                visitDAO.updateVisit(visit);
                JOptionPane.showMessageDialog(null, "Zmieniono status wizyty.");
                refreshTable();
            }
        });
    }
    private void refreshTable() {
        visits = visitDAO.getAllVisits();

        String[] colNames = {"ID", "Data", "Pacjent", "Lekarz", "Status", "Notatka"};
        Object[][] data = new Object[visits.size()][6];

        for (int i = 0; i < visits.size(); i++) {
            Visit v = visits.get(i);
            data[i][0] = v.getId();
            data[i][1] = v.getDateTime().toString();
            data[i][2] = v.getPatient().getFirstName() + " " + v.getPatient().getLastName();
            data[i][3] = v.getDoctor().getFirstName() + " " + v.getDoctor().getLastName();
            data[i][4] = v.getStatus().name();
            data[i][5] = v.getNotes();
        }

        Visitstable.setModel(new DefaultTableModel(data, colNames));
    }
}

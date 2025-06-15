package dao;
import database.DatabaseConnection;
import model.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VisitDAOImpl implements VisitDAO {

    @Override
    public void addVisit(Visit visit) {
        String sql = "INSERT INTO visits (visit_date, status, notes, patient_id, doctor_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, Timestamp.valueOf(visit.getDateTime()));
            stmt.setString(2, visit.getStatus().name());
            stmt.setString(3, visit.getNotes());
            stmt.setInt(4, visit.getPatient().getId());
            stmt.setInt(5, visit.getDoctor().getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateVisit(Visit visit) {
        String sql = "UPDATE visits SET visit_date=?, status=?, notes=?, patient_id=?, doctor_id=? WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, Timestamp.valueOf(visit.getDateTime()));
            stmt.setString(2, visit.getStatus().name());
            stmt.setString(3, visit.getNotes());
            stmt.setInt(4, visit.getPatient().getId());
            stmt.setInt(5, visit.getDoctor().getId());
            stmt.setInt(6, visit.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteVisit(int id) {
        String sql = "DELETE FROM visits WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Visit> getAllVisits() {
        List<Visit> visits = new ArrayList<>();
        String sql = "SELECT " +
                "v.id AS visit_id, " +
                "v.visit_date, " +
                "v.status, " +
                "v.notes, " +
                "p.id AS patient_id, " +
                "p.first_name AS patient_first_name, " +
                "p.last_name AS patient_last_name, " +
                "p.phone AS patient_phone, " +
                "p.email AS patient_email, " +
                "p.pesel AS patient_pesel, " +
                "d.id AS doctor_id, " +
                "d.first_name AS doctor_first_name, " +
                "d.last_name AS doctor_last_name, " +
                "d.phone AS doctor_phone, " +
                "d.email AS doctor_email, " +
                "d.specialization " +
                "FROM visits v " +
                "JOIN patients p ON v.patient_id = p.id " +
                "JOIN doctors d ON v.doctor_id = d.id";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Patient patient = new Patient(
                        rs.getInt("patient_id"),
                        rs.getString("patient_first_name"),
                        rs.getString("patient_last_name"),
                        rs.getString("patient_phone"),
                        rs.getString("patient_email"),
                        rs.getString("patient_pesel")
                );

                Doctor doctor = new Doctor(
                        rs.getInt("doctor_id"),
                        rs.getString("doctor_first_name"),
                        rs.getString("doctor_last_name"),
                        rs.getString("doctor_phone"),
                        rs.getString("doctor_email"),
                        rs.getString("specialization")
                );

                Visit visit = new Visit(
                        rs.getInt("visit_id"),
                        patient,
                        doctor,
                        rs.getTimestamp("visit_date").toLocalDateTime(),
                        VisitStatus.valueOf(rs.getString("status"))
                );
                visit.setNotes(rs.getString("notes"));

                visits.add(visit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return visits;
    }

    public boolean hasDoctorConflict(int doctorId, LocalDateTime dateTime) {
        String sql = "SELECT COUNT(*) FROM visits WHERE doctor_id = ? AND ABS(EXTRACT(EPOCH FROM (visit_date - ?))) < 1800";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, doctorId);
            stmt.setTimestamp(2, Timestamp.valueOf(dateTime));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}

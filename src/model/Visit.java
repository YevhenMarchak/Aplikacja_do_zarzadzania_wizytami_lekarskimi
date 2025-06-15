package model;
import dao.VisitStatus;

import java.time.LocalDateTime;

public class Visit {
    private int id;
    private Patient patient;
    private Doctor doctor;
    private LocalDateTime dateTime;
    private VisitStatus status;
    private String notes;

    public Visit(int id, Patient patient, Doctor doctor, LocalDateTime dateTime, VisitStatus status) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.dateTime = dateTime;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public VisitStatus getStatus() {
        return status;
    }

    public void setStatus(VisitStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return dateTime + " - " + doctor.getLastName() + " z " + patient.getLastName();
    }
}

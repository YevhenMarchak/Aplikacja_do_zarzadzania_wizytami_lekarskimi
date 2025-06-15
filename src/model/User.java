package model;

public class User {
    private int id;
    private String login;
    private String password;
    private String role;
    private Integer patientId;

    public User(int id, String login, String password, String role, Integer patientId) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
        this.patientId = patientId;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public boolean isPatient() {
        return "PATIENT".equalsIgnoreCase(role);
    }

    public boolean isSecretary() {
        return "SECRETARY".equalsIgnoreCase(role);
    }
}

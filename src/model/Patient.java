package model;

public class Patient extends Person {
    private String pesel;

    public Patient(int id, String firstName, String lastName, String phone, String email, String pesel) {
        super(id, firstName, lastName, phone, email);
        this.pesel = pesel;
    }

    public Patient(String firstName, String lastName, String pesel, String email) {
        this(0, firstName, lastName, "", email, pesel);
    }

    @Override
    public String getInfo() {
        return "Pacjent: " + firstName + " " + lastName + " (" + pesel + ")";
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }
}

 package model;

    public class Doctor extends Person {
        private String specialization;

        public Doctor(int id, String firstName, String lastName, String phone, String email, String specialization) {
            super(id, firstName, lastName, phone, email);
            this.specialization = specialization;
        }

        @Override
        public String getInfo() {
            return "Lekarz: " + firstName + " " + lastName + " - " + specialization;
        }

        public String getSpecialization() {
            return specialization;
        }

        public void setSpecialization(String specialization) {
            this.specialization = specialization;
        }

        @Override
        public String toString() {
            return firstName + " " + lastName + " - " + specialization;
        }

    }



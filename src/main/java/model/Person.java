package model;


import java.util.Objects;

public final class Person {

    private String email;
    private String forename;
    private String surname;
    private String password;

    public Person() {
    }

    public Person(String email, String forename, String surname, String password) {
        this.email = email;
        this.forename = forename;
        this.surname = surname;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Person{" +
                "email='" + email + '\'' +
                ", forename='" + forename + '\'' +
                ", surname='" + surname + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return Objects.equals(getEmail(), person.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail());
    }
}

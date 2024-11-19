package org.example;



public class User {
    private Long id;
    private final String firstName;
    private final String lastName;
    private final String patronymic;
    private final Integer age;
    private final String email;
    private final Integer driving_experience;


    public User(Long id, String firstName, String lastName, String patronymic, String email, Integer age, Integer driving_experience) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.email = email;
        this.age = age;
        this.driving_experience = driving_experience;
    }

    Long getId() {
        return id;
    }

    String getFirstName() {
        return firstName;
    }

    String getLastName() {
        return lastName;
    }

    String getPatronymic() {
        return patronymic;
    }

    String getEmail() {
        return email;
    }

    Integer getDrivingExperience() {
        return driving_experience;
    }

    Integer getAge() {
        return age;
    }

    void setID(Long id) {
        this.id = id;
    }
}
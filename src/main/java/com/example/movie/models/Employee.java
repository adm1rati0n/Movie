package com.example.movie.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Фамилия - обязательное поле")
    @Size(max = 255, message = "Максимальная длина фамилии - 255 символов")
    @Pattern(regexp = "^([а-яА-Яё0-9]+|[a-zA-Z0-9]+)$",
            message = "Фамилия может содержать только цифры и буквы русского или латинского алфавита")
    private String surname;
    @NotBlank(message = "Имя - обязательное поле")
    @Size(max = 255, message = "Максимальная длина имени - 255 символов")
    @Pattern(regexp = "^([а-яА-Яё0-9]+|[a-zA-Z0-9]+)$",
            message = "Имя может содержать только цифры и буквы русского или латинского алфавита")
    private String name;
    @NotBlank(message = "Отчество - обязательное поле")
    @Size(max = 255, message = "Максимальная длина отчества - 255 символов")
    @Pattern(regexp = "^([а-яА-Яё0-9]+|[a-zA-Z0-9]+)$",
            message = "Отчество может содержать только цифры и буквы русского или латинского алфавита")
    private String middlename;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

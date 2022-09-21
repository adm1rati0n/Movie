package com.example.movie.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Название - обязательное поле")
    @Size(max = 255, message = "Максимальная длина названия - 255 символов")
    @Pattern(regexp = "^([а-яА-Яё0-9]+|[a-zA-Z0-9]+)$",
            message = "Название может содержать только цифры и буквы русского или латинского алфавита")
    private String name;
    @NotBlank(message = "Название - обязательное поле")
    @Positive(message = "Размер зала должен быть больше нуля")
    private Integer size;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }

    @OneToMany(mappedBy = "name", fetch = FetchType.EAGER)
    private List<Film> films;
}

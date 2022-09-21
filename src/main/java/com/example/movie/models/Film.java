package com.example.movie.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Название - обязательное поле")
    @Size(max = 255, message = "Максимальная длина названия - 255 символов")
    @Pattern(regexp = "^([а-яА-Яё0-9]+|[a-zA-Z0-9]+)$",
            message = "Название может содержать только цифры и буквы русского или латинского алфавита")
    private String name;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "studio_id")
    private Studio studio;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "producer_id")
    private Producer producer;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "actor_id")
    private MainActor actor;

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

    public Studio getStudio() {
        return studio;
    }

    public void setStudio(Studio studio) {
        this.studio = studio;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public MainActor getActor() {
        return actor;
    }

    public void setActor(MainActor actor) {
        this.actor = actor;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "genre_id")
    private Genre genre;
}

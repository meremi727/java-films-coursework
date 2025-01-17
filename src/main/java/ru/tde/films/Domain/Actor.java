package ru.tde.films.Domain;

import jakarta.persistence.*;

@Entity
public class Actor extends Person {
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private String country;

    /// Возвращает пол.
    public Gender getGender() { return gender; }

    /// Устанавливает новое значениеи пола.
    public void setGender(Gender gender) { this.gender = gender; }

    /// Возвращает страну.
    public String getCountry() { return country; }

    /// Устанавливает новое значение страны.
    public void setCountry(String country) { this.country = country; }
}

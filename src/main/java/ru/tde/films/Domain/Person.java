package ru.tde.films.Domain;

import java.util.Collections;
import java.util.Date;
import jakarta.persistence.*;

@Entity
@Table(name = "Person")
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String name;

    private String patronymic;

    @Column(nullable = false)
    private Date dateOfBirth;

    /// Возвращает уникальный идентификатор сущности.
    public int getId() { return id; }

    /// Возвращает фамилию.
    public String getSurname() { return surname; }

    /// Устанавливает новую фамилию.
    public void setSurname(String surname) { this.surname = surname; }

    /// Возвращает имя.
    public String getName() { return name; }

    /// Устанавливает новое имя.
    public void setName(String name) { this.name = name; }

    /// Возвращает отчество.
    public String getPatronymic() { return patronymic; }

    /// Устанавливает новое отчество.
    public void setPatronymic(String patronymic) { this.patronymic = patronymic; }

    /// Возвращает дату рождения.
    public Date getDateOfBirth() { return dateOfBirth; }

    /// Устанавливает новую дату рождения.
    public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getFio() {
        return getSurname() + " " + getName().charAt(0) + " " + getPatronymic().charAt(0);
    }
}


package ru.tde.films.Domain;

public enum Gender {
    FEMALE ("Женский"),
    MALE ("Мужской");

    private final String label;

    Gender(String label) { this.label = label; }

    @Override
    public String toString() { return label; }
}
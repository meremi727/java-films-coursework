package ru.tde.films.Domain;

import java.util.Date;
import jakarta.persistence.*;

@Entity
@Table(name = "Comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int score;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private Date dateWritten;

    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;

    /// Возвращает уникальный идентификатор отзыва.
    public int getId() { return id; }

    /// Возвращает имя пользователя, оставившего отзыв.
    public String getName() { return name; }

    /// Устанавливает имя пользователя.
    public void setName(String name) { this.name = name; }

    /// Возвращает оценку.
    public int getScore() { return score; }

    /// Устанавливает оценку.
    public void setScore(int score) { this.score = score; }

    /// Возвращает текст отзыва.
    public String getText() { return text; }

    /// Устанавливает текст отзыва.
    public void setText(String text) { this.text = text; }

    /// Возвращает дату написания отзыва.
    public Date getDateWritten() { return dateWritten; }

    /// Устанавливает дату написания отзыва.
    public void setDateWritten(Date dateWritten) { this.dateWritten = dateWritten; }

    /// Возвращает фильм, к которому написан отзыв.
    public Film getFilm() { return film; }

    /// Устанавливает фильм, к которому написан отзыв.
    public void setFilm(Film film) { this.film = film; }
}

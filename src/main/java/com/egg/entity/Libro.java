package com.egg.entity;

import com.egg.validation.ValidISBN;
import com.egg.validation.ValidYear;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "libro")
public class Libro {

    @Id
    @Column(name = "isbn", nullable = false, unique = true)
    @NotNull(message = "El ISBN del libro no puede ser nulo")
    @ValidISBN(message = "El ISBN debe ser de 10 o 13 dígitos")
    private Long isbn;

    @Column(name = "titulo", length = 150, nullable = false)
    @NotNull(message = "El título del libro no puede ser nulo")
    @Size(min = 1, max = 150, message = "El título del libro debe tener entre 1 y 150 caracteres")
    private String titulo;

    @Column(name = "anio", nullable = false)
    @NotNull(message = "El año del libro no puede ser nulo")
    @ValidYear(message = "El año debe estar entre 1967 (ya que el ISBN no existía antes) y el año actual")
    private Integer anio;

    @Column(name = "ejemplares", nullable = false)
    @NotNull(message = "El número de ejemplares del libro no puede ser nulo")
    @Min(value = 1, message = "El número mínimo de ejemplares del libro es 1")
    private Integer ejemplares;

    @Column(name = "alta", nullable = false)
    private Boolean alta = true;

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    @NotNull(message = "El autor no puede ser nulo")
    private Autor autor;

    @ManyToOne
    @JoinColumn(name = "editorial_id", nullable = false)
    @NotNull(message = "La editorial no puede ser nula")
    private Editorial editorial;

    public Libro() {
    }

    public Libro(Long isbn, String titulo, Integer anio, Integer ejemplares, Autor autor, Editorial editorial) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.anio = anio;
        this.ejemplares = ejemplares;
        this.autor = autor;
        this.editorial = editorial;
    }

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getEjemplares() {
        return ejemplares;
    }

    public void setEjemplares(Integer ejemplares) {
        this.ejemplares = ejemplares;
    }

    public Boolean getAlta() {
        return alta;
    }

    public void setAlta(Boolean alta) {
        this.alta = alta;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Editorial getEditorial() {
        return editorial;
    }

    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }

    @Override
    public String toString() {
        return "Libro [isbn=" + isbn + ", titulo=" + titulo + ", anio=" + anio + ", ejemplares=" + ejemplares
                + ", alta=" + alta + ", autor=" + autor + ", editorial=" + editorial + "]";
    }

}

package com.carri1x.chess.objetos;

import com.carri1x.chess.enums.Colores;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Entity
@Table(name= "jugadores")
public class Jugador {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotBlank
    @Column(nullable = false)
    private String nombre;
    @NotNull
    @Column(nullable = false)
    private Colores color;
    private UUID cookie;

    protected Jugador () {}

    public Jugador  (String nombre, Colores color) {
        this.nombre = nombre;
        this.color = color;
    }

    public UUID     getId() {
        return id;
    }
    public String   getNombre() {
        return nombre;
    }
    public void     setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Colores  getColor() {
        return color;
    }
    public void     setColor(Colores color) {
        this.color = color;
    }
    public UUID     getCookie() {
        return cookie;
    }
    public void     setCookie(UUID cookie) {
        this.cookie = cookie;
    }
}
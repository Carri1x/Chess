package com.carri1x.chess.objetos;

import com.carri1x.chess.enums.Colores;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name= "jugadores")
public class Jugador {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotBlank
    @Column(nullable = false)
    private String nombre;
    @Column(unique = true)
    private String email;
    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Colores color;
    @Embedded
    private Elo elo;

    protected Jugador() {}

    public Jugador(String nombre, Colores color) {
        this.nombre = nombre;
        this.color = color;
    }
}

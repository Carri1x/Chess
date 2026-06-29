package com.carri1x.chess.piezas;

import com.carri1x.chess.enums.Acciones;
import com.carri1x.chess.enums.Colores;
import com.carri1x.chess.exceptions.AjedrezException;
import com.carri1x.chess.movimientos.Coordenadas;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "tipo")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Alfil.class,   name = "ALFIL"),
    @JsonSubTypes.Type(value = Rey.class,     name = "REY"),
    @JsonSubTypes.Type(value = Reina.class,   name = "REINA"),
    @JsonSubTypes.Type(value = Torre.class,   name = "TORRE"),
    @JsonSubTypes.Type(value = Caballo.class, name = "CABALLO"),
    @JsonSubTypes.Type(value = Peon.class,    name = "PEON")
})
public abstract class Pieza {
    private UUID id;
    private Colores color;
    @Setter(AccessLevel.NONE)
    private Coordenadas posicion;
    private Coordenadas anteriorPosicion = null;

    protected Pieza() {
        this.id = UUID.randomUUID();
    }

    Pieza(Colores color, Coordenadas posicion) {
        this.id = UUID.randomUUID();
        this.color = color;
        this.posicion = posicion;
    }

    public void setPosicion(Coordenadas posicion) throws AjedrezException {
        if (!Coordenadas.isPosicionPermitida(posicion))
            throw new AjedrezException("Lo siento esta posición no está permitida para esta pieza");
        this.posicion = posicion;
    }

    protected int deltaFila(Coordenadas destino) {
        return destino.getFila() - posicion.getFila();
    }

    protected int deltaColumna(Coordenadas destino) {
        return destino.getColumna() - posicion.getColumna();
    }

    public abstract Acciones movimiento(Coordenadas destino);

    public boolean puedeSaltar() {
        return false;
    }

    public static boolean comparePosicionIguales(Pieza p1, Pieza p2) {
        return p1.getPosicion().getFila()    == p2.getPosicion().getFila()
            && p1.getPosicion().getColumna() == p2.getPosicion().getColumna();
    }

    public static boolean comparePosicionIgualesCoordenadas(Pieza p1, Coordenadas coordenadas) {
        return p1.getPosicion().getFila()    == coordenadas.getFila()
            && p1.getPosicion().getColumna() == coordenadas.getColumna();
    }
}

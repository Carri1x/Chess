package com.carri1x.chess.piezas;

import com.carri1x.chess.enums.Acciones;
import com.carri1x.chess.enums.Colores;
import com.carri1x.chess.exceptions.AjedrezException;
import com.carri1x.chess.movimientos.Coordenadas;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.UUID;

/**
 *
 * @author ÁLVARO CARRIÓN ROMERO
 */
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Colores getColor() {
        return color;
    }

    public void setColor(Colores color) {
        this.color = color;
    }

    public Coordenadas getPosicion() { 
        return posicion;
    }

    public void setPosicion(Coordenadas posicion) throws AjedrezException{
        boolean nuevaPosicionPermitida = Coordenadas.isPosicionPermitida(posicion);
        if(!nuevaPosicionPermitida) {
            throw new AjedrezException("Lo siento esta posición no está permitida para esta pieza");
        }
        this.posicion = posicion;
    }

    public Coordenadas getAnteriorPosicion() {
        return this.anteriorPosicion;
    }

    public void setAnteriorPosicion(Coordenadas posicion) {
        this.anteriorPosicion = posicion;
    }

    protected int deltaFila(Coordenadas destino) {
        return destino.getFila() - posicion.getFila();
    }

    protected int deltaColumna(Coordenadas destino) {
        return destino.getColumna() - posicion.getColumna();
    }

    
    public abstract Acciones movimiento(Coordenadas destino);

    /**
     * Normalmente todas las piezas no pueden saltar. Solo una que sobreescribirá esta, el caballo, diciendo que si puede saltar devolviendo true.
     * @return boolean
     */
    public boolean puedeSaltar() {
        return false;
    }

    public static boolean comparePosicionIguales (Pieza p1, Pieza p2) {
        int fila1 = p1.getPosicion().getFila();
        int colum1 = p1.getPosicion().getColumna();
        int fila2 = p2.getPosicion().getFila();
        int colum2 = p2.getPosicion().getColumna();

        if(fila1 == fila2 && colum1 == colum2) return true;
        return false;
    }

    public static boolean comparePosicionIgualesCoordenadas (Pieza p1, Coordenadas coordenadas) {
        int fila1 = p1.getPosicion().getFila();
        int colum1 = p1.getPosicion().getColumna();
        int fila2 = coordenadas.getFila();
        int colum2 = coordenadas.getColumna();

        if(fila1 == fila2 && colum1 == colum2) return true;
        return false;
    }
}

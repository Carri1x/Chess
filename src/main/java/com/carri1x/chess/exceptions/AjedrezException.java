package com.carri1x.chess.exceptions;

import com.carri1x.chess.piezas.Pieza;

public class AjedrezException extends Exception{
    private Pieza pieza;

    public AjedrezException(String mensajeError) {
        super(mensajeError);
    }

    public AjedrezException(String mensajeError, Pieza pieza) {
        this(mensajeError);
        this.pieza = pieza;
    }

    public Pieza getPieza() {
        return pieza;
    }

    public void setPieza(Pieza pieza) {
        this.pieza = pieza;
    }
}

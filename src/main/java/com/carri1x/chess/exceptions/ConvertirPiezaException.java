package com.carri1x.chess.exceptions;

import com.carri1x.chess.objetos.Tablero;
import com.carri1x.chess.piezas.Pieza;;

public class ConvertirPiezaException extends AjedrezException{
    private Pieza pieza;
    private Tablero tablero;

    public  ConvertirPiezaException(String mensaje) {
        super(mensaje);
    }
    public ConvertirPiezaException(String mensaje, Pieza pieza) {
        this(mensaje);
        this.pieza = pieza;
    }

    public Pieza getPieza() {
        return pieza;
    }

    public void setPieza(Pieza pieza) {
        this.pieza = pieza;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }
}

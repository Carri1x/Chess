package com.carri1x.chess.piezas;

import com.carri1x.chess.enums.Acciones;
import com.carri1x.chess.enums.Colores;
import com.carri1x.chess.movimientos.Coordenadas;

public class Alfil extends Pieza {

    protected Alfil() {}

    public Alfil(Colores color, Coordenadas posicion) {
        super(color, posicion);
    }

    @Override
    public Acciones movimiento(Coordenadas destino) {
        int dFila = Math.abs(deltaFila(destino));
        int dColumna = Math.abs(deltaColumna(destino));

        if (dFila == 0 || dFila != dColumna)
            throw new IllegalArgumentException("Movimiento inválido para el alfil");

        return Acciones.MOVERSE;
    }
}

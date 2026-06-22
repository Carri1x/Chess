package com.carri1x.chess.piezas;

import com.carri1x.chess.enums.Acciones;
import com.carri1x.chess.enums.Colores;
import com.carri1x.chess.movimientos.Coordenadas;

public class Rey extends Pieza {

    protected Rey() {}

    public Rey(Colores color, Coordenadas posicion) {
        super(color, posicion);
    }

    @Override
    public Acciones movimiento(Coordenadas destino) {
        int dFila = Math.abs(deltaFila(destino));
        int dColumna = Math.abs(deltaColumna(destino));

        if (dFila > 1 || dColumna > 1 || (dFila == 0 && dColumna == 0))
            throw new IllegalArgumentException("Movimiento inválido para el rey");

        return Acciones.MOVERSE;
    }
}

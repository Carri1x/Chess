package com.carri1x.chess.piezas;

import com.carri1x.chess.enums.Acciones;
import com.carri1x.chess.enums.Colores;
import com.carri1x.chess.movimientos.Coordenadas;

public class Torre extends Pieza {

    protected Torre() {}

    public Torre(Colores color, Coordenadas posicion) {
        super(color, posicion);
    }

    @Override
    public Acciones movimiento(Coordenadas destino) {
        int dFila = deltaFila(destino);
        int dColumna = deltaColumna(destino);

        if ((dFila == 0) == (dColumna == 0))
            throw new IllegalArgumentException("Movimiento inválido para la torre");

        return Acciones.MOVERSE;
    }
}

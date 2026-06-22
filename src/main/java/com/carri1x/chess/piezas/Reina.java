package com.carri1x.chess.piezas;

import com.carri1x.chess.enums.Acciones;
import com.carri1x.chess.enums.Colores;
import com.carri1x.chess.movimientos.Coordenadas;

public class Reina extends Pieza {

    protected Reina() {}

    public Reina(Colores color, Coordenadas posicion) {
        super(color, posicion);
    }

    @Override
    public Acciones movimiento(Coordenadas destino) {
        int dFila = Math.abs(deltaFila(destino));
        int dColumna = Math.abs(deltaColumna(destino));

        if (dFila == 0 && dColumna == 0)
            throw new IllegalArgumentException("Movimiento inválido para la reina: la pieza no se ha movido");

        boolean enLinea = dFila == 0 || dColumna == 0;
        boolean enDiagonal = dFila == dColumna && dFila != 0;

        if (!enLinea && !enDiagonal)
            throw new IllegalArgumentException("Movimiento inválido para la reina");

        return Acciones.MOVERSE;
    }
}

package com.carri1x.chess.piezas;

import com.carri1x.chess.enums.Acciones;
import com.carri1x.chess.enums.Colores;
import com.carri1x.chess.movimientos.Coordenadas;

public class Caballo extends Pieza {

    protected Caballo() {}

    public Caballo(Colores color, Coordenadas posicion) {
        super(color, posicion);
    }

    @Override
    public Acciones movimiento(Coordenadas destino) {
        int dFila = Math.abs(deltaFila(destino));
        int dColumna = Math.abs(deltaColumna(destino));

        boolean esEleValida = (dFila == 2 && dColumna == 1) || (dFila == 1 && dColumna == 2);

        if (!esEleValida)
            throw new IllegalArgumentException("Movimiento inválido para el caballo");

        return Acciones.MOVERSE;
    }

    @Override
    public boolean puedeSaltar() {
        return true;
    }
}

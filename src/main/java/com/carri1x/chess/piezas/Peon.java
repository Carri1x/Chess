package com.carri1x.chess.piezas;

import com.carri1x.chess.enums.Acciones;
import com.carri1x.chess.enums.Colores;
import com.carri1x.chess.movimientos.Coordenadas;

public class Peon extends Pieza {

    private static final int FILA_INICIO_BLANCO = 1;
    private static final int FILA_INICIO_NEGRO  = 6;

    protected Peon() {}

    public Peon(Colores color, Coordenadas posicion) {
        super(color, posicion);
    }

    @Override
    public Acciones movimiento(Coordenadas destino) {
        int dFila    = deltaFila(destino);
        int dColumna = Math.abs(deltaColumna(destino));

        // Dirección: BLANCO avanza hacia filas mayores, NEGRO hacia menores
        int direccion = (getColor() == Colores.BLANCO) ? 1 : -1;
        int filaInicio = (getColor() == Colores.BLANCO) ? FILA_INICIO_BLANCO : FILA_INICIO_NEGRO;

        // Avance simple
        if (dFila == direccion && dColumna == 0)
            return llegaAlExtremo(destino) ? Acciones.CONVERTIRSE : Acciones.MOVERSE;

        // Avance doble desde la fila inicial
        if (dFila == direccion * 2 && dColumna == 0 && getPosicion().getFila() == filaInicio)
            return Acciones.MOVERSE;

        // Captura en diagonal
        if (dFila == direccion && dColumna == 1)
            return Acciones.MATAR;

        throw new IllegalArgumentException("Movimiento inválido para el peón");
    }

    private boolean llegaAlExtremo(Coordenadas destino) {
        return (getColor() == Colores.BLANCO && destino.getFila() == 7)
            || (getColor() == Colores.NEGRO  && destino.getFila() == 0);
    }
}

package com.carri1x.chess.piezas;

import com.carri1x.chess.enums.Acciones;
import com.carri1x.chess.enums.Colores;
import com.carri1x.chess.movimientos.Coordenadas;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReyTest {

    @Test
    void devuelveMoverseCuandoSeDesplazaUnaFilaArriba() {
        // ARRANGE
        Rey rey = new Rey(Colores.BLANCO, new Coordenadas(4, 4));
        // ACT
        Acciones resultado = rey.movimiento(new Coordenadas(5, 4));
        // ASSERT
        assertEquals(Acciones.MOVERSE, resultado);
    }

    @Test
    void devuelveMoverseCuandoSeDesplazaUnaCasillaEnDiagonal() {
        // ARRANGE
        Rey rey = new Rey(Colores.BLANCO, new Coordenadas(4, 4));
        // ACT
        Acciones resultado = rey.movimiento(new Coordenadas(5, 5));
        // ASSERT
        assertEquals(Acciones.MOVERSE, resultado);
    }

    @Test
    void devuelveMoverseCuandoSeDesplazaUnaColumnaALaIzquierda() {
        // ARRANGE
        Rey rey = new Rey(Colores.BLANCO, new Coordenadas(4, 4));
        // ACT
        Acciones resultado = rey.movimiento(new Coordenadas(4, 3));
        // ASSERT
        assertEquals(Acciones.MOVERSE, resultado);
    }

    @Test
    void lanzaExcepcionCuandoSeDesplazaDosCasillas() {
        // ARRANGE
        Rey rey = new Rey(Colores.BLANCO, new Coordenadas(4, 4));
        // ACT & ASSERT
        assertThrows(IllegalArgumentException.class, () ->
            rey.movimiento(new Coordenadas(6, 4))
        );
    }

    @Test
    void lanzaExcepcionCuandoNoSeDesplaza() {
        // ARRANGE
        Rey rey = new Rey(Colores.BLANCO, new Coordenadas(4, 4));
        // ACT & ASSERT
        assertThrows(IllegalArgumentException.class, () ->
            rey.movimiento(new Coordenadas(4, 4))
        );
    }
}
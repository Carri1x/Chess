package com.carri1x.chess.piezas;

import com.carri1x.chess.enums.Acciones;
import com.carri1x.chess.enums.Colores;
import com.carri1x.chess.movimientos.Coordenadas;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TorreTest {
    @Test
    void devuelveElMovimientoDeLaTorreHaciaLaDerecha() {
        //ARRANGE
        Torre torre = new Torre(Colores.BLANCO, new Coordenadas(3,3));
        //ACT
        Acciones resultadoMovimiento = torre.movimiento(new Coordenadas(3, 6));
        //ASSERT
        assertEquals(Acciones.MOVERSE, resultadoMovimiento);
    }

    @Test
    void devuelveElMovimientoDeLaTorreHaciaDelante() {
        //ARRANGE
        Torre torre = new Torre(Colores.BLANCO, new Coordenadas(3,3));
        //ACT
        Acciones resultadoMovimiento = torre.movimiento(new Coordenadas(6, 3));
        //ASSERT
        assertEquals(Acciones.MOVERSE, resultadoMovimiento);
    }
    @Test
    void devuelveElMovimientoDeLaTorreHaciaDetras() {
        //ARRANGE
        Torre torre = new Torre(Colores.BLANCO, new Coordenadas(3,3));
        //ACT
        Acciones resultadoMovimiento = torre.movimiento(new Coordenadas(2, 3));
        //ASSERT
        assertEquals(Acciones.MOVERSE, resultadoMovimiento);
    }

    @Test
    void devuelveElMovimientoDeLaTorreHaciaLaIzquierda() {
        // ARRANGE
        Torre torre = new Torre(Colores.BLANCO, new Coordenadas(3, 3));
        // ACT
        Acciones resultadoMovimiento = torre.movimiento(new Coordenadas(3, 0));
        // ASSERT
        assertEquals(Acciones.MOVERSE, resultadoMovimiento);
    }

    @Test
    void lanzaUnaExcepcionAlMoverseDiagonalmenteALaDerecha() {
        // ARRANGE - preparo la pieza en una posición.
        Torre torre = new Torre(Colores.BLANCO, new Coordenadas(3, 3));
        // ASSERT - compruebo que el resultado es el esperado.
        assertThrows(IllegalArgumentException.class, () -> {
            torre.movimiento(new Coordenadas(6, 6));
        });
    }

    @Test
    void lanzaUnaExcepcionAlMoverseDiagonalmenteALaIzquierda() {
        // ARRANGE - preparo la pieza en una posición.
        Torre torre = new Torre(Colores.BLANCO, new Coordenadas(3, 3));
        // ASSERT - compruebo que el resultado es el esperado.
        assertThrows(IllegalArgumentException.class, () -> {
            torre.movimiento(new Coordenadas(0, 0));
        });
    }
}

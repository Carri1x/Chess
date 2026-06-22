package com.carri1x.chess.piezas;

import com.carri1x.chess.enums.Acciones;
import com.carri1x.chess.enums.Colores;
import com.carri1x.chess.movimientos.Coordenadas;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class AlfilTest {
    @Test
    void devuelveElMovimientoDelAlfil() {
        // ARRANGE - preparo la pieza en una posición.
        Alfil alfil = new Alfil(Colores.BLANCO, new Coordenadas(3, 3));
        // ACT - ejecuto el movimiento (se mueve diagonalmente).
        Acciones resultadoMovimiento = alfil.movimiento(new Coordenadas(5, 5));
        // ASSERT - compruebo que el resultado es el esperado.
        assertEquals(Acciones.MOVERSE, resultadoMovimiento);
    }

    @Test
    void devuelveElMovimientoDelAlfilEnOtraDireccion() {
        // ARRANGE - preparo la pieza en una posición.
        Alfil alfil = new Alfil(Colores.BLANCO, new Coordenadas(3, 3));
        // ACT - ejecuto el movimiento (se mueve diagonalmente).
        Acciones resultadoMovimiento = alfil.movimiento(new Coordenadas(1, 1));
        // ASSERT - compruebo que el resultado es el esperado.
        assertEquals(Acciones.MOVERSE, resultadoMovimiento);
    }

    @Test
    void lanzaUnaExcepcionEseMovimientoNoEsValido() {
        // ARRANGE - preparo la pieza en una posición.
        Alfil alfil = new Alfil(Colores.BLANCO, new Coordenadas(3, 3));
        // ASSERT - compruebo que el resultado es el esperado.
        assertThrows(IllegalArgumentException.class, () -> {
            alfil.movimiento(new Coordenadas(3, 6));
        });
    }

    @Test
    void lanzaUnaExcepcionEseMovimientoNoEsValidoOtraPrueba(){
        // ARRANGE - preparo la pieza en una posición.
        Alfil alfil = new Alfil(Colores.BLANCO, new Coordenadas(3, 3));
        // ASSERT - compruebo que el resultado es el esperado.
        assertThrows(IllegalArgumentException.class, () -> {
            alfil.movimiento(new Coordenadas(4, 5));
        });
    }
}

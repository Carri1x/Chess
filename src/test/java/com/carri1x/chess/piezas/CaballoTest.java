package com.carri1x.chess.piezas;

import com.carri1x.chess.enums.Acciones;
import com.carri1x.chess.enums.Colores;
import com.carri1x.chess.movimientos.Coordenadas;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CaballoTest {

    @Test
    void devuelveElMovimientoDelCaballoEnLDerechaArriba() {
        // ARRANGE - preparo la pieza en una posición.
        Caballo caballo = new Caballo(Colores.BLANCO, new Coordenadas(3, 3));
        // ACT - ejecuto el movimiento.
        Acciones resultadoMovimiento = caballo.movimiento(new Coordenadas(4, 5));
        // ASSERT - compruebo que el resultado es el esperado.
        assertEquals(Acciones.MOVERSE, resultadoMovimiento);
    }

    @Test
    void devuelveElMovimientoDelCaballoEnLIzquierdaAbajo() {
        // ARRANGE - preparo la pieza en una posición.
        Caballo caballo = new Caballo(Colores.BLANCO, new Coordenadas(3, 3));
        // ACT - ejecuto el movimiento.
        Acciones resultadoMovimiento = caballo.movimiento(new Coordenadas(2, 1));
        // ASSERT - compruebo que el resultado es el esperado.
        assertEquals(Acciones.MOVERSE, resultadoMovimiento);
    }

    @Test
    void lanzaExcepcionPorNoMoverseEnL() {
        // ARRANGE - preparo la pieza en una posición.
        Caballo caballo = new Caballo(Colores.BLANCO, new Coordenadas(3, 3));
        // ASSERT - compruebo que el resultado es el esperado.
        assertThrows(IllegalArgumentException.class, () -> {
            caballo.movimiento(new Coordenadas(3, 2));
        });
    }

    @Test
    void lanzaExcepcionPorNoMoverseEnLAleatorio() {
        // ARRANGE - preparo la pieza en una posición.
        Caballo caballo = new Caballo(Colores.BLANCO, new Coordenadas(3, 3));
        // ASSERT - compruebo que el resultado es el esperado.
        assertThrows(IllegalArgumentException.class, () -> {
            caballo.movimiento(new Coordenadas(6, 6));
        });
    }
}

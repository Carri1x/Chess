package com.carri1x.chess.piezas;

import com.carri1x.chess.enums.Acciones;
import com.carri1x.chess.enums.Colores;
import com.carri1x.chess.movimientos.Coordenadas;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReinaTest {

    @Test
    void devuelveMoverseCuandoSeDesplazaHorizontalmente() {
        // ARRANGE
        Reina reina = new Reina(Colores.BLANCO, new Coordenadas(4, 4));
        // ACT
        Acciones resultado = reina.movimiento(new Coordenadas(4, 7));
        // ASSERT
        assertEquals(Acciones.MOVERSE, resultado);
    }

    @Test
    void devuelveMoverseCuandoSeDesplazaVerticalmente() {
        // ARRANGE
        Reina reina = new Reina(Colores.BLANCO, new Coordenadas(4, 4));
        // ACT
        Acciones resultado = reina.movimiento(new Coordenadas(0, 4));
        // ASSERT
        assertEquals(Acciones.MOVERSE, resultado);
    }

    @Test
    void devuelveMoverseCuandoSeDesplazaDiagonalmente() {
        // ARRANGE
        Reina reina = new Reina(Colores.BLANCO, new Coordenadas(4, 4));
        // ACT
        Acciones resultado = reina.movimiento(new Coordenadas(7, 7));
        // ASSERT
        assertEquals(Acciones.MOVERSE, resultado);
    }

    @Test
    void devuelveMoverseCuandoSeDesplazaDiagonalmenteEnSentidoContrario() {
        // ARRANGE
        Reina reina = new Reina(Colores.BLANCO, new Coordenadas(4, 4));
        // ACT
        Acciones resultado = reina.movimiento(new Coordenadas(1, 1));
        // ASSERT
        assertEquals(Acciones.MOVERSE, resultado);
    }

    @Test
    void lanzaExcepcionCuandoSeDesplazaEnL() {
        // ARRANGE
        Reina reina = new Reina(Colores.BLANCO, new Coordenadas(4, 4));
        // ACT & ASSERT
        assertThrows(IllegalArgumentException.class, () ->
            reina.movimiento(new Coordenadas(6, 5))
        );
    }

    @Test
    void lanzaExcepcionCuandoElMovimientoEsArbitrario() {
        // ARRANGE
        Reina reina = new Reina(Colores.BLANCO, new Coordenadas(4, 4));
        // ACT & ASSERT
        assertThrows(IllegalArgumentException.class, () ->
            reina.movimiento(new Coordenadas(6, 7))
        );
    }

    @Test
    void lanzaExcepcionCuandoNoSeDesplaza() {
        // ARRANGE
        Reina reina = new Reina(Colores.BLANCO, new Coordenadas(4, 4));
        // ACT & ASSERT
        assertThrows(IllegalArgumentException.class, () ->
            reina.movimiento(new Coordenadas(4, 4))
        );
    }
}
package com.carri1x.chess.piezas;

import com.carri1x.chess.enums.Acciones;
import com.carri1x.chess.enums.Colores;
import com.carri1x.chess.movimientos.Coordenadas;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PeonTest {

    // -------- BLANCO --------

    @Test
    void blancoDevuelveMoverseCuandoAvanzaUnaCasilla() {
        // ARRANGE
        Peon peon = new Peon(Colores.BLANCO, new Coordenadas(3, 3));
        // ACT
        Acciones resultado = peon.movimiento(new Coordenadas(4, 3));
        // ASSERT
        assertEquals(Acciones.MOVERSE, resultado);
    }

    @Test
    void blancoDevuelveMoverseCuandoAvanzaDosCasillasDesdeLaFilaInicial() {
        // ARRANGE
        Peon peon = new Peon(Colores.BLANCO, new Coordenadas(1, 3));
        // ACT
        Acciones resultado = peon.movimiento(new Coordenadas(3, 3));
        // ASSERT
        assertEquals(Acciones.MOVERSE, resultado);
    }

    @Test
    void blancoDevuelveMatarCuandoCapturaDiagonalmente() {
        // ARRANGE
        Peon peon = new Peon(Colores.BLANCO, new Coordenadas(3, 3));
        // ACT
        Acciones resultado = peon.movimiento(new Coordenadas(4, 4));
        // ASSERT
        assertEquals(Acciones.MATAR, resultado);
    }

    @Test
    void blancoDevuelveConvertirseAlLlegarALaUltimaFila() {
        // ARRANGE
        Peon peon = new Peon(Colores.BLANCO, new Coordenadas(6, 3));
        // ACT
        Acciones resultado = peon.movimiento(new Coordenadas(7, 3));
        // ASSERT
        assertEquals(Acciones.CONVERTIRSE, resultado);
    }

    @Test
    void blancoLanzaExcepcionCuandoAvanzaDosCasillasDesdeFilaNoInicial() {
        // ARRANGE
        Peon peon = new Peon(Colores.BLANCO, new Coordenadas(3, 3));
        // ACT & ASSERT
        assertThrows(IllegalArgumentException.class, () ->
            peon.movimiento(new Coordenadas(5, 3))
        );
    }

    @Test
    void blancoLanzaExcepcionCuandoRetrocede() {
        // ARRANGE
        Peon peon = new Peon(Colores.BLANCO, new Coordenadas(3, 3));
        // ACT & ASSERT
        assertThrows(IllegalArgumentException.class, () ->
            peon.movimiento(new Coordenadas(2, 3))
        );
    }

    @Test
    void blancoLanzaExcepcionCuandoSeDesplazaHorizontalmente() {
        // ARRANGE
        Peon peon = new Peon(Colores.BLANCO, new Coordenadas(3, 3));
        // ACT & ASSERT
        assertThrows(IllegalArgumentException.class, () ->
            peon.movimiento(new Coordenadas(3, 4))
        );
    }

    // -------- NEGRO --------

    @Test
    void negroDevuelveMoverseCuandoAvanzaUnaCasilla() {
        // ARRANGE
        Peon peon = new Peon(Colores.NEGRO, new Coordenadas(5, 3));
        // ACT
        Acciones resultado = peon.movimiento(new Coordenadas(4, 3));
        // ASSERT
        assertEquals(Acciones.MOVERSE, resultado);
    }

    @Test
    void negroDevuelveMoverseCuandoAvanzaDosCasillasDesdeLaFilaInicial() {
        // ARRANGE
        Peon peon = new Peon(Colores.NEGRO, new Coordenadas(6, 3));
        // ACT
        Acciones resultado = peon.movimiento(new Coordenadas(4, 3));
        // ASSERT
        assertEquals(Acciones.MOVERSE, resultado);
    }

    @Test
    void negroDevuelveMatarCuandoCapturaDiagonalmente() {
        // ARRANGE
        Peon peon = new Peon(Colores.NEGRO, new Coordenadas(5, 3));
        // ACT
        Acciones resultado = peon.movimiento(new Coordenadas(4, 2));
        // ASSERT
        assertEquals(Acciones.MATAR, resultado);
    }

    @Test
    void negroDevuelveConvertirseAlLlegarALaPrimeraFila() {
        // ARRANGE
        Peon peon = new Peon(Colores.NEGRO, new Coordenadas(1, 3));
        // ACT
        Acciones resultado = peon.movimiento(new Coordenadas(0, 3));
        // ASSERT
        assertEquals(Acciones.CONVERTIRSE, resultado);
    }

    @Test
    void negroLanzaExcepcionCuandoRetrocede() {
        // ARRANGE
        Peon peon = new Peon(Colores.NEGRO, new Coordenadas(5, 3));
        // ACT & ASSERT
        assertThrows(IllegalArgumentException.class, () ->
            peon.movimiento(new Coordenadas(6, 3))
        );
    }
}
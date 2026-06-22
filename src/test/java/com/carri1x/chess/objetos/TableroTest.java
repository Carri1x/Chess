package com.carri1x.chess.objetos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.carri1x.chess.enums.Acciones;
import com.carri1x.chess.exceptions.AjedrezException;
import com.carri1x.chess.movimientos.Coordenadas;
import com.carri1x.chess.piezas.Pieza;

public class TableroTest {

    Tablero tablero;

    @BeforeEach
    void crearUnTablero() {
        this.tablero = new Tablero();
    }

    @Test
    void devuelveLaAccionDeMoversePeonNegroHaciaDelante() throws AjedrezException {
        // ARRANGE
        Pieza peon = this.tablero.getPieza(new Coordenadas(6, 0)).get();
        // ACT
        Acciones resultado = this.tablero.infoMovimiento(peon, new Coordenadas(5, 0));
        // ASSERT
        assertEquals(Acciones.MOVERSE, resultado);
    }

    @Test
    void devuelveLaAccionDeMoverseCaballoSaltandoLosPeones() throws AjedrezException {
        // ARRANGE - el caballo blanco en (0,1) salta por encima del peón en (1,2) hasta (2,2)
        Pieza caballo = this.tablero.getPieza(new Coordenadas(0, 1)).get();
        // ACT
        Acciones resultado = this.tablero.infoMovimiento(caballo, new Coordenadas(2, 2));
        // ASSERT
        assertEquals(Acciones.MOVERSE, resultado);
    }

    @Test
    void infoMovimientoLanzaExcepcionCuandoHayUnaPiezaEnElCamino() {
        // ARRANGE - Torre blanca en (0,0) intenta ir a (3,0) pero el peón en (1,0) la bloquea
        Pieza torre = this.tablero.getPieza(new Coordenadas(0, 0)).get();
        // ACT & ASSERT
        assertThrows(AjedrezException.class, () ->
            this.tablero.infoMovimiento(torre, new Coordenadas(3, 0))
        );
    }

    @Test
    void infoMovimientoLanzaExcepcionAlIntentarMoverAPiezaPropia() {
        // ARRANGE - Torre blanca en (0,0) intenta moverse a (0,1) donde está el Caballo blanco
        Pieza torre = this.tablero.getPieza(new Coordenadas(0, 0)).get();
        // ACT & ASSERT
        assertThrows(AjedrezException.class, () ->
            this.tablero.infoMovimiento(torre, new Coordenadas(0, 1))
        );
    }

    @Test
    void infoMovimientoDevuelveMatarCuandoHayPiezaEnemigaEnElDestino() throws AjedrezException {
        // ARRANGE - movemos el peón blanco desde (1,3) hasta (5,3) para que pueda capturar al peón negro en (6,4)
        Pieza peon = this.tablero.getPieza(new Coordenadas(1, 3)).get();
        this.tablero.moverPieza(peon, new Coordenadas(5, 3));
        // ACT - captura diagonal hacia (6,4) donde hay un peón negro
        Acciones resultado = this.tablero.infoMovimiento(peon, new Coordenadas(6, 4));
        // ASSERT
        assertEquals(Acciones.MATAR, resultado);
    }

    @Test
    void moverPiezaActualizaLaCasillaEnElTablero() throws AjedrezException {
        // ARRANGE
        Pieza peon = this.tablero.getPieza(new Coordenadas(1, 3)).get();
        // ACT
        this.tablero.moverPieza(peon, new Coordenadas(2, 3));
        // ASSERT - la nueva casilla tiene la pieza y la antigua queda vacía
        assertTrue(this.tablero.getPieza(new Coordenadas(2, 3)).isPresent());
        assertTrue(this.tablero.getPieza(new Coordenadas(1, 3)).isEmpty());
    }

    @Test
    void moverPiezaActualizaLaPosicionDeLaPieza() throws AjedrezException {
        // ARRANGE
        Pieza peon = this.tablero.getPieza(new Coordenadas(1, 3)).get();
        Coordenadas destino = new Coordenadas(2, 3);
        // ACT
        this.tablero.moverPieza(peon, destino);
        // ASSERT
        assertEquals(destino.getFila(), peon.getPosicion().getFila());
        assertEquals(destino.getColumna(), peon.getPosicion().getColumna());
    }
}

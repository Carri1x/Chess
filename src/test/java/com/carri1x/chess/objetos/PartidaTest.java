package com.carri1x.chess.objetos;

import com.carri1x.chess.enums.Colores;
import com.carri1x.chess.exceptions.AjedrezException;
import com.carri1x.chess.exceptions.ConvertirPiezaException;
import com.carri1x.chess.movimientos.Coordenadas;
import com.carri1x.chess.piezas.Pieza;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PartidaTest {

    Partida partida;
    Jugador jugadorBlanco;
    Jugador jugadorNegro;

    @BeforeEach
    void crearPartida() {
        jugadorBlanco = new Jugador("Álvaro", Colores.BLANCO);
        jugadorNegro  = new Jugador("Oponente", Colores.NEGRO);
        partida = new Partida(jugadorBlanco, jugadorNegro);
    }

    @Test
    void turnoInicialEsBlanco() {
        // ASSERT - al crear la partida siempre empiezan las blancas
        assertEquals(Colores.BLANCO, partida.getTurnoActual());
    }

    @Test
    void movimientoValidoCambiaTurnoANegro() throws AjedrezException {
        // ARRANGE
        Pieza peon = partida.getTablero().getPieza(new Coordenadas(1, 3)).get();
        // ACT
        partida.movimiento(peon, new Coordenadas(2, 3), jugadorBlanco);
        // ASSERT
        assertEquals(Colores.NEGRO, partida.getTurnoActual());
    }

    @Test
    void lanzaExcepcionCuandoJuegaFueraDeSuTurno() {
        // ARRANGE - es turno de blancas pero intenta mover el jugador negro
        Pieza peon = partida.getTablero().getPieza(new Coordenadas(6, 3)).get();
        // ACT & ASSERT
        assertThrows(AjedrezException.class, () ->
            partida.movimiento(peon, new Coordenadas(5, 3), jugadorNegro)
        );
    }

    @Test
    void movimientoMatarMueveLaPiezaYCambiaTurno() throws AjedrezException {
        // ARRANGE - colocamos el peón blanco en (5,3) para que pueda capturar el peón negro en (6,4)
        Tablero tablero = partida.getTablero();
        Pieza peon = tablero.getPieza(new Coordenadas(1, 3)).get();
        tablero.moverPieza(peon, new Coordenadas(5, 3));
        // ACT
        partida.movimiento(peon, new Coordenadas(6, 4), jugadorBlanco);
        // ASSERT - el peón blanco ocupa la casilla del rival y el turno cambia
        assertTrue(tablero.getPieza(new Coordenadas(6, 4)).isPresent());
        assertEquals(Colores.NEGRO, partida.getTurnoActual());
    }

    @Test
    void movimientoConvertirse_lanzaConvertirPiezaException() throws AjedrezException {
        // ARRANGE - colocamos el peón blanco en (6,0), un paso antes de la última fila
        Tablero tablero = partida.getTablero();
        Pieza peon = tablero.getPieza(new Coordenadas(1, 0)).get();
        tablero.moverPieza(peon, new Coordenadas(6, 0));
        // ACT & ASSERT - al llegar a fila 7 debe pedir conversión
        assertThrows(ConvertirPiezaException.class, () ->
            partida.movimiento(peon, new Coordenadas(7, 0), jugadorBlanco)
        );
    }
}
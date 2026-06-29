package com.carri1x.chess.objetos;

import com.carri1x.chess.enums.Acciones;
import com.carri1x.chess.enums.Colores;
import com.carri1x.chess.enums.EstadoJuego;
import com.carri1x.chess.exceptions.AjedrezException;
import com.carri1x.chess.exceptions.ConvertirPiezaException;
import com.carri1x.chess.movimientos.Coordenadas;
import com.carri1x.chess.piezas.Pieza;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "partidas")
public class Partida {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Transient
    private Tablero tablero;
    @ManyToOne
    private Jugador jugadorBlancas;
    @ManyToOne
    private Jugador jugadorNegras;
    private Colores turnoActual;
    @Enumerated(EnumType.STRING)
    private EstadoJuego estadoJuego = EstadoJuego.EN_JUEGO;
    @ManyToOne
    private Jugador ganador;
    private Boolean tablas = false;
    @Transient
    private Cementerio cementerioJugadorBlanco;
    @Transient
    private Cementerio cementerioJugadorNegro;

    protected Partida() {}

    public Partida(Jugador j1, Jugador j2) {
        this.tablero = new Tablero();
        if (j1.getColor() == Colores.BLANCO) {
            this.jugadorBlancas = j1;
            this.cementerioJugadorBlanco = new Cementerio(j1);
            this.jugadorNegras = j2;
            this.cementerioJugadorNegro = new Cementerio(j2);
        } else {
            this.jugadorBlancas = j2;
            this.cementerioJugadorBlanco = new Cementerio(j2);
            this.jugadorNegras = j1;
            this.cementerioJugadorNegro = new Cementerio(j1);
        }
        this.turnoActual = Colores.BLANCO;
    }

    public void cambiarTurno() {
        this.turnoActual = this.turnoActual == Colores.BLANCO ? Colores.NEGRO : Colores.BLANCO;
    }

    public void actualizarEstadoJuego(Colores colorQueAcabaDeMover) {
        Colores colorOponente = colorQueAcabaDeMover == Colores.BLANCO ? Colores.NEGRO : Colores.BLANCO;
        boolean enJaque      = tablero.estaEnJaque(colorOponente);
        boolean tieneLegales = tablero.tieneMovimientosLegales(colorOponente);

        if      (enJaque && !tieneLegales)  estadoJuego = EstadoJuego.JAQUE_MATE;
        else if (!enJaque && !tieneLegales) estadoJuego = EstadoJuego.AHOGADO;
        else if (enJaque)                   estadoJuego = EstadoJuego.JAQUE;
        else                                estadoJuego = EstadoJuego.EN_JUEGO;
    }

    public void movimiento(Pieza pieza, Coordenadas coordenadas, Jugador jugador) throws AjedrezException {
        if (estadoJuego == EstadoJuego.JAQUE_MATE || estadoJuego == EstadoJuego.AHOGADO)
            throw new AjedrezException("La partida ya ha terminado");

        if (jugador.getColor() != turnoActual)
            throw new AjedrezException("Lo siento espera tu turno para poder mover");

        Acciones posibilidad = tablero.infoMovimiento(pieza, coordenadas);

        if (!tablero.esMovimientoLegal(pieza, coordenadas))
            throw new AjedrezException("Movimiento ilegal: dejarías a tu propio rey en jaque");

        switch (posibilidad) {
            case Acciones.MOVERSE -> {
                tablero.moverPieza(pieza, coordenadas);
                cambiarTurno();
                actualizarEstadoJuego(jugador.getColor());
            }
            case Acciones.MATAR -> {
                Optional<Pieza> piezaBorrada = tablero.getAndDeletePieza(coordenadas);
                if (piezaBorrada.isPresent()) {
                    insertarEnCementerio(piezaBorrada.get());
                }
                tablero.moverPieza(pieza, coordenadas);
                cambiarTurno();
                actualizarEstadoJuego(jugador.getColor());
            }
            case Acciones.CONVERTIRSE -> {
                tablero.moverPieza(pieza, coordenadas);
                throw new ConvertirPiezaException("Escoge cuál es la pieza a la que quieres convertir", pieza);
            }
        }
    }

    public void insertarEnCementerio(Pieza pieza) throws AjedrezException {
        if (pieza.getColor() == Colores.BLANCO) {
            if (!insertarCementerioJugadorNegro(pieza))
                throw new AjedrezException("Ha surgido un error al intentar insertar la pieza en el cementerio negro", pieza);
        } else {
            if (!insertarCementerioJugadorBlanco(pieza))
                throw new AjedrezException("Ha surgido un error al intentar insertar la pieza en el cementerio blanco", pieza);
        }
    }

    private boolean insertarCementerioJugadorBlanco(Pieza pieza) {
        return this.cementerioJugadorBlanco.insertarPieza(pieza);
    }

    private boolean insertarCementerioJugadorNegro(Pieza pieza) {
        return this.cementerioJugadorNegro.insertarPieza(pieza);
    }
}

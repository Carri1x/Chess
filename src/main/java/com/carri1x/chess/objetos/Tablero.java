package com.carri1x.chess.objetos;

import java.util.Optional;
import java.util.UUID;

import com.carri1x.chess.enums.Acciones;
import com.carri1x.chess.enums.Colores;
import com.carri1x.chess.exceptions.AjedrezException;
import com.carri1x.chess.exceptions.ConvertirPiezaException;
import com.carri1x.chess.movimientos.Coordenadas;
import com.carri1x.chess.piezas.Alfil;
import com.carri1x.chess.piezas.Caballo;
import com.carri1x.chess.piezas.Peon;
import com.carri1x.chess.piezas.Pieza;
import com.carri1x.chess.piezas.Reina;
import com.carri1x.chess.piezas.Rey;
import com.carri1x.chess.piezas.Torre;

public class Tablero {
    private Pieza[][] casillas;

    public Tablero() {
        this.casillas = new Pieza[8][8];
        // --------- CREAMOS BLANCAS ---------
        for (int i = 0; i < casillas.length; i++) {
            casillas[1][i] = new Peon(Colores.BLANCO, new Coordenadas(1, i));
        }
        casillas[0][0] = new Torre(Colores.BLANCO, new Coordenadas(0,0));
        casillas[0][1] = new Caballo(Colores.BLANCO, new Coordenadas(0, 1));
        casillas[0][2] = new Alfil(Colores.BLANCO, new Coordenadas(0, 2));
        casillas[0][3] = new Reina(Colores.BLANCO, new Coordenadas(0, 3));
        casillas[0][4] = new Rey(Colores.BLANCO, new Coordenadas(0, 4));
        casillas[0][5] = new Alfil(Colores.BLANCO, new Coordenadas(0, 5));
        casillas[0][6] = new Caballo(Colores.BLANCO, new Coordenadas(0, 6));
        casillas[0][7] = new Torre(Colores.BLANCO, new Coordenadas(0, 7));
        // --------- CREAMOS NEGRAS ---------
        for (int i = 0; i < casillas.length; i++) {
            casillas[6][i] = new Peon(Colores.NEGRO, new Coordenadas(6, i));
        }
        casillas[7][0] = new Torre(Colores.NEGRO, new Coordenadas(7,0));
        casillas[7][1] = new Caballo(Colores.NEGRO, new Coordenadas(7, 1));
        casillas[7][2] = new Alfil(Colores.NEGRO, new Coordenadas(7, 2));
        casillas[7][3] = new Reina(Colores.NEGRO, new Coordenadas(7, 3));
        casillas[7][4] = new Rey(Colores.NEGRO, new Coordenadas(7, 4));
        casillas[7][5] = new Alfil(Colores.NEGRO, new Coordenadas(7, 5));
        casillas[7][6] = new Caballo(Colores.NEGRO, new Coordenadas(7, 6));
        casillas[7][7] = new Torre(Colores.NEGRO, new Coordenadas(7, 7));
        // --------- AMBOS CEMENTERIOS ---------
    }

    public Pieza[][] getCasillas() {
        return casillas;
    }

    public void setCasillas(Pieza[][] casillas) {
        this.casillas = casillas;
    }

    public Acciones infoMovimiento (Pieza pieza, Coordenadas coordenadas) throws AjedrezException{
        // Comprobamos que las coordenadas del movimiento no se exceden fuera del tablero.
        if(!Coordenadas.isPosicionPermitida(coordenadas)) throw new AjedrezException("No se puede mover una pieza fuera del tablero");

        int filaDestino = coordenadas.getFila();
        int columnaDestino = coordenadas.getColumna();
        Pieza casillaDestino = casillas[filaDestino][columnaDestino];

        // Si es diferente de null es que hay una pieza en esa casilla (vamos a comprobar que sea nuestra o del oponente).
        if(casillaDestino != null) {
            if(pieza.getColor() == casillaDestino.getColor()) throw new AjedrezException("Lo siento no puedes matarte a ti mismo");
            try {
                Acciones accion = pieza.movimiento(coordenadas);
                // Comprobamos el camino si no se puede pasar porque esa pieza no puede saltar (caballo) entonces lanzará una excepción.
                if (!pieza.puedeSaltar()) comprobarCaminoLibre(pieza, coordenadas);
                return accion;
            } catch (IllegalArgumentException ex) {
                throw new AjedrezException(ex.getMessage());
            }
        }
        try {
            Acciones accion = pieza.movimiento(coordenadas);
            // Comprobamos el camino si no se puede pasar porque esa pieza no puede saltar (caballo) entonces lanzará una excepción.
            if (!pieza.puedeSaltar()) comprobarCaminoLibre(pieza, coordenadas);
            return accion;
        } catch (IllegalArgumentException ex) {
            throw new AjedrezException(ex.getMessage());
        }
    }

    private void comprobarCaminoLibre(Pieza pieza, Coordenadas destino) throws AjedrezException {
        int stepFila    = Integer.signum(destino.getFila()    - pieza.getPosicion().getFila());
        int stepColumna = Integer.signum(destino.getColumna() - pieza.getPosicion().getColumna());

        int fila    = pieza.getPosicion().getFila()    + stepFila;
        int columna = pieza.getPosicion().getColumna() + stepColumna;

        while (fila != destino.getFila() || columna != destino.getColumna()) {
            if (casillas[fila][columna] != null) {
                throw new AjedrezException("Hay una pieza en el camino que bloquea el movimiento");
            }
            fila    += stepFila;
            columna += stepColumna;
        }
    }

    public Optional<Pieza> getPiezaById(UUID id) {
        for (Pieza[] fila : casillas) {
            for (Pieza pieza : fila) {
                if (pieza != null && pieza.getId().equals(id)) {
                    return Optional.of(pieza);
                }
            }
        }
        return Optional.empty();
    }

    public Optional<Pieza> getPieza(Coordenadas coordenadas) {
        return Optional.ofNullable(
                this.casillas[coordenadas.getFila()][coordenadas.getColumna()]
        );
    }

    public Optional<Pieza> getAndDeletePieza(Coordenadas coordenadas) {
        Optional<Pieza> pieza = getPieza(coordenadas);
        // Si hay una pieza, la borramos
        if(pieza.isPresent()){
            this.casillas[coordenadas.getFila()][coordenadas.getColumna()] = null;
        }
        // Tanto si hay una pieza como si es un empty devolvemos esa info.
        return pieza;
    }

    public void moverPieza (Pieza pieza, Coordenadas coordenadas) throws AjedrezException {
        Coordenadas posicionAnterior = pieza.getPosicion();
        this.casillas[coordenadas.getFila()][coordenadas.getColumna()] = pieza;
        this.casillas[posicionAnterior.getFila()][posicionAnterior.getColumna()] = null;
        pieza.setAnteriorPosicion(posicionAnterior);
        pieza.setPosicion(coordenadas);
    }

    public boolean convertirPieza (Pieza pieza, Pieza piezaElegida, Coordenadas coordenadasDondeConvertir) throws ConvertirPiezaException, AjedrezException {
        Pieza piezaAReemplazar = casillas[coordenadasDondeConvertir.getFila()][coordenadasDondeConvertir.getColumna()];
        if(!pieza.getId().equals(piezaAReemplazar.getId())) throw new ConvertirPiezaException("No son las mismas piezas las que se quieren reemplazar");
        // Borramos la pieza a remplazar, para luego proceder al movimiento de esta pieza.
        this.casillas[coordenadasDondeConvertir.getFila()][coordenadasDondeConvertir.getColumna()] = null;
        // Asignamos posiciones de la anterior pieza a la piezaElegida.
        piezaElegida.setAnteriorPosicion(pieza.getAnteriorPosicion());
        piezaElegida.setPosicion(coordenadasDondeConvertir);
        // Insertamos la pieza elegida en las coordenadas.
        this.casillas[coordenadasDondeConvertir.getFila()][coordenadasDondeConvertir.getColumna()] = piezaElegida;
        return true;
    }

    public boolean estaEnJaque(Colores colorRey) {
        // 1. Localizamos al rey del color indicado
        Coordenadas posicionRey = null;
        outer:
        for (Pieza[] fila : casillas) {
            for (Pieza pieza : fila) {
                if (pieza instanceof Rey && pieza.getColor() == colorRey) {
                    posicionRey = pieza.getPosicion();
                    break outer;
                }
            }
        }
        if (posicionRey == null) return false;

        // 2. Comprobamos si alguna pieza enemiga puede atacar esa casilla
        Colores colorEnemigo = colorRey == Colores.BLANCO ? Colores.NEGRO : Colores.BLANCO;
        for (Pieza[] fila : casillas) {
            for (Pieza pieza : fila) {
                if (pieza == null || pieza.getColor() != colorEnemigo) continue;
                try {
                    Acciones accion = pieza.movimiento(posicionRey);
                    // El peón solo amenaza en diagonal (MATAR), no en línea recta (MOVERSE)
                    if (pieza instanceof Peon && accion != Acciones.MATAR) continue;
                    if (!pieza.puedeSaltar()) {
                        try {
                            comprobarCaminoLibre(pieza, posicionRey);
                        } catch (AjedrezException e) {
                            continue;
                        }
                    }
                    return true;
                } catch (IllegalArgumentException ignored) {}
            }
        }
        return false;
    }

    public boolean esMovimientoLegal(Pieza pieza, Coordenadas destino) {
        Coordenadas posicionOriginal = pieza.getPosicion();
        Coordenadas anteriorOriginal = pieza.getAnteriorPosicion();
        Pieza piezaCapturada = casillas[destino.getFila()][destino.getColumna()];

        // Simulamos el movimiento
        casillas[destino.getFila()][destino.getColumna()] = pieza;
        casillas[posicionOriginal.getFila()][posicionOriginal.getColumna()] = null;
        pieza.setAnteriorPosicion(posicionOriginal);
        try { pieza.setPosicion(destino); } catch (AjedrezException ignored) {}

        boolean enJaque = estaEnJaque(pieza.getColor());

        // Restauramos el estado
        casillas[posicionOriginal.getFila()][posicionOriginal.getColumna()] = pieza;
        casillas[destino.getFila()][destino.getColumna()] = piezaCapturada;
        pieza.setAnteriorPosicion(anteriorOriginal);
        try { pieza.setPosicion(posicionOriginal); } catch (AjedrezException ignored) {}

        return !enJaque;
    }

    public boolean tieneMovimientosLegales(Colores color) {
        for (Pieza[] fila : casillas) {
            for (Pieza pieza : fila) {
                if (pieza == null || pieza.getColor() != color) continue;
                for (int f = 0; f < 8; f++) {
                    for (int c = 0; c < 8; c++) {
                        Coordenadas destino = new Coordenadas(f, c);
                        try {
                            infoMovimiento(pieza, destino);
                            if (esMovimientoLegal(pieza, destino)) return true;
                        } catch (AjedrezException ignored) {}
                    }
                }
            }
        }
        return false;
    }
}

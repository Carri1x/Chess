package com.carri1x.chess.objetos;

import com.carri1x.chess.piezas.Pieza;

import java.util.ArrayList;
import java.util.List;

public class Cementerio {
    private List<Pieza> piezas;
    private Jugador jugador;

    protected Cementerio() {
        this.piezas = new ArrayList<>();
    }

    public Cementerio(Jugador jugador) {
        this.piezas = new ArrayList<Pieza>();
        this.jugador = jugador;
    }

    public List<Pieza> getPiezas() {
        return piezas;
    }

    public boolean insertarPieza(Pieza pieza) {
        this.piezas.add(pieza);
        return true;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }
}

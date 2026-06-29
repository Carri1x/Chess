package com.carri1x.chess.objetos;

import com.carri1x.chess.piezas.Pieza;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Cementerio {
    private List<Pieza> piezas;
    private Jugador jugador;

    protected Cementerio() {
        this.piezas = new ArrayList<>();
    }

    public Cementerio(Jugador jugador) {
        this.piezas = new ArrayList<>();
        this.jugador = jugador;
    }

    public boolean insertarPieza(Pieza pieza) {
        this.piezas.add(pieza);
        return true;
    }
}

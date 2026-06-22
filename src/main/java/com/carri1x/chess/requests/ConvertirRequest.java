package com.carri1x.chess.requests;

import com.carri1x.chess.movimientos.Coordenadas;
import com.carri1x.chess.piezas.Pieza;

import java.util.UUID;

public class ConvertirRequest extends Request{
    private Pieza piezaElegida;

    public ConvertirRequest() {
        super(null);
    }

    public ConvertirRequest(Pieza piezaElegida, Coordenadas coordenadas, UUID partidaId) {
        super(partidaId, coordenadas);
        this.piezaElegida = piezaElegida;
    }

    public Pieza getPiezaElegida() {
        return piezaElegida;
    }

    public void setPiezaElegida(Pieza piezaElegida) {
        this.piezaElegida = piezaElegida;
    }
}

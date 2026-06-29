package com.carri1x.chess.requests;

import com.carri1x.chess.movimientos.Coordenadas;
import com.carri1x.chess.piezas.Pieza;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ConvertirRequest extends Request {
    private Pieza piezaElegida;

    public ConvertirRequest() {
        super(null);
    }

    public ConvertirRequest(Pieza piezaElegida, Coordenadas coordenadas, UUID partidaId) {
        super(partidaId, coordenadas);
        this.piezaElegida = piezaElegida;
    }
}

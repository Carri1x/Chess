package com.carri1x.chess.requests;

import com.carri1x.chess.movimientos.Coordenadas;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AjedrezRequest extends Request {
    private UUID piezaId;

    public AjedrezRequest() {
        super(null);
    }

    public AjedrezRequest(UUID piezaId, Coordenadas coordenadas, UUID partidaId) {
        super(partidaId, coordenadas);
        this.piezaId = piezaId;
    }
}

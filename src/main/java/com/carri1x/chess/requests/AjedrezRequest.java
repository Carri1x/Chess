package com.carri1x.chess.requests;

import com.carri1x.chess.movimientos.Coordenadas;

import java.util.UUID;

public class AjedrezRequest extends Request{
    private UUID piezaId;

    public AjedrezRequest() {
        super(null);
    }

    public AjedrezRequest(UUID piezaId, Coordenadas coordenadas, UUID partidaId) {
        super(partidaId, coordenadas);
        this.piezaId = piezaId;
    }

    public UUID getPiezaId() {
        return piezaId;
    }

    public void setPiezaId(UUID piezaId) {
        this.piezaId = piezaId;
    }
}

package com.carri1x.chess.responses;

import com.carri1x.chess.objetos.Partida;

public class PartidaResponse extends Response{
    private Partida partida;

    public PartidaResponse(Boolean suceso, Integer status, String mensaje, Partida partida) {
        super(suceso, status, mensaje);
        this.partida = partida;
    }

    public Partida getPartida() {
        return partida;
    }
    public void setPartida(Partida partida) {
        this.partida = partida;
    }
}

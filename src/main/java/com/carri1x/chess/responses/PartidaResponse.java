package com.carri1x.chess.responses;

import com.carri1x.chess.objetos.Partida;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartidaResponse extends Response {
    private Partida partida;

    public PartidaResponse(Boolean suceso, Integer status, String mensaje, Partida partida) {
        super(suceso, status, mensaje);
        this.partida = partida;
    }
}

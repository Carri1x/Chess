package com.carri1x.chess.requests;

import com.carri1x.chess.movimientos.Coordenadas;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;
import java.util.UUID;

@Getter
public abstract class Request {
    private Optional<UUID> partidaId;
    @Setter
    private Coordenadas coordenadas;

    public Request(UUID partidaId) {
        this.partidaId = partidaId != null ? Optional.of(partidaId) : Optional.empty();
    }

    public Request(UUID partidaId, Coordenadas coordenadas) {
        this(partidaId);
        this.coordenadas = coordenadas;
    }

    public void setPartidaId(UUID partidaId) {
        this.partidaId = partidaId != null ? Optional.of(partidaId) : Optional.empty();
    }
}

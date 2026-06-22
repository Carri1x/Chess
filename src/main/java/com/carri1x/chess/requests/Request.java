package com.carri1x.chess.requests;

import com.carri1x.chess.movimientos.Coordenadas;

import java.util.Optional;
import java.util.UUID;

public abstract class Request {
    private Optional<UUID> partidaId;
    private Coordenadas coordenadas;

    public Request(UUID partidaId) {
        Optional<UUID> id = partidaId != null ? Optional.of(partidaId) : Optional.empty();
        this.partidaId = id;
    }

    public Request(UUID partidaId, Coordenadas coordenadas) {
        this(partidaId);
        this.coordenadas = coordenadas;
    }

    public Optional<UUID> getPartidaId() {
        return partidaId;
    }

    public void setPartidaId(UUID partidaId) {
        Optional<UUID> id = partidaId != null ? Optional.of(partidaId) : Optional.empty();
        this.partidaId = id;
    }

    public Coordenadas getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(Coordenadas coordenadas) {
        this.coordenadas = coordenadas;
    }
}

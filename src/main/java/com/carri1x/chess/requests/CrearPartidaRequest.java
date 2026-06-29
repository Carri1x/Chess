package com.carri1x.chess.requests;

import lombok.Data;

import java.util.UUID;

@Data
public class CrearPartidaRequest {
    private String nombre;
    private String codigoEntrada;
    private UUID cookie;
}

package com.carri1x.chess.requests;

import java.util.UUID;

public class CrearPartidaRequest {
    private String nombre;
    private String codigoEntrada;
    private UUID cookie;

    public CrearPartidaRequest(String nombre, String codigoEntrada, UUID cookie) {
        this.nombre = nombre;
        this.codigoEntrada = codigoEntrada;
        this.cookie = cookie;
    }

    public String   getNombre() {
        return nombre;
    }
    public void     setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String   getCodigoEntrada() {
        return codigoEntrada;
    }
    public void     setCodigoEntrada(String codigoEntrada) {
        this.codigoEntrada = codigoEntrada;
    }
    public UUID     getCookie() {
        return cookie;
    }
    public void     setCookie(UUID cookie) {
        this.cookie = cookie;
    }
}

package com.carri1x.chess.responses;

import com.carri1x.chess.enums.EstadoJuego;
import com.carri1x.chess.objetos.Tablero;

public class AjedrezResponse {
    private Boolean suceso;
    private Integer status;
    private String mensaje;
    private Tablero tablero;
    private EstadoJuego estadoJuego;
    private Boolean convertido;

    public AjedrezResponse() {
        this.suceso = true;
        this.status = 200;
        this.mensaje = "Se ha ejecutado bien la petición en el servidor";
    }

    public AjedrezResponse(Boolean suceso) {
        this.suceso = suceso;
    }

    public AjedrezResponse(Boolean suceso, Integer status) {
        this(suceso);
        this.status = status;
    }

    public AjedrezResponse(Boolean suceso, Integer status, String mensaje) {
        this(suceso, status);
        this.mensaje = mensaje;
    }

    public AjedrezResponse(Boolean suceso, Integer status, String mensaje, Tablero tablero, EstadoJuego estadoJuego) {
        this(suceso, status, mensaje);
        this.tablero = tablero;
        this.estadoJuego = estadoJuego;
    }

    public AjedrezResponse(Boolean suceso, Integer status, String mensaje, Tablero tablero, EstadoJuego estadoJuego, Boolean convertido) {
        this(suceso, status, mensaje, tablero, estadoJuego);
        this.convertido = convertido;
    }

    public Boolean getSuceso() { return suceso; }
    public void setSuceso(Boolean suceso) { this.suceso = suceso; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public Tablero getTablero() { return tablero; }
    public void setTablero(Tablero tablero) { this.tablero = tablero; }

    public EstadoJuego getEstadoJuego() { return estadoJuego; }
    public void setEstadoJuego(EstadoJuego estadoJuego) { this.estadoJuego = estadoJuego; }

    public Boolean getConvertido() { return convertido; }
    public void setConvertido(Boolean convertido) { this.convertido = convertido; }
}

package com.carri1x.chess.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {
    private Boolean suceso;
    private Integer status;
    private String mensaje;

    public Response() {
        this.suceso = true;
        this.status = 200;
        this.mensaje = "Se ha ejecutado bien la petición en el servidor";
    }

    public Response(Boolean suceso) {
        this.suceso = suceso;
    }

    public Response(Boolean suceso, Integer status) {
        this(suceso);
        this.status = status;
    }

    public Response(Boolean suceso, Integer status, String mensaje) {
        this(suceso, status);
        this.mensaje = mensaje;
    }
}

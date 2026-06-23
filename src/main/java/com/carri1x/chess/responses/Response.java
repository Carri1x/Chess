package com.carri1x.chess.responses;

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


    public Boolean getSuceso() { return suceso; }
    public void setSuceso(Boolean suceso) { this.suceso = suceso; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

}

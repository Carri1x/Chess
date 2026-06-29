package com.carri1x.chess.requests;

import lombok.Data;

import java.util.UUID;

import com.carri1x.chess.utils.CodigoEntrada;

@Data
public class CrearPartidaRequest {
    private String nombre;
    private String codigoEntrada;
    private UUID cookie;

    public String getCodigoEntrada () {
        // Comprobamos que el código de entrada no tenga caracteres y se guarde correctamente en el .
        return CodigoEntrada.cambiarEspaciosAGuiones(this.codigoEntrada);
    }

}

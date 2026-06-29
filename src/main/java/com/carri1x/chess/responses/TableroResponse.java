package com.carri1x.chess.responses;

import com.carri1x.chess.enums.EstadoJuego;
import com.carri1x.chess.objetos.Tablero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TableroResponse extends Response {
    private Tablero tablero;
    private EstadoJuego estadoJuego;

    public TableroResponse(Boolean suceso, Integer status, String mensaje, Tablero tablero) {
        super(suceso, status, mensaje);
        this.tablero = tablero;
    }

    public TableroResponse(Boolean suceso, Integer status, String mensaje, Tablero tablero, EstadoJuego estadoJuego) {
        this(suceso, status, mensaje, tablero);
        this.estadoJuego = estadoJuego;
    }
}

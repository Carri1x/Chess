package com.carri1x.chess.utils;

public class CodigoEntrada {
    public static String cambiarEspaciosAGuiones (String codigo) {
        return codigo.trim().replace(" ", "-");
    }

    public static String cambiarGuionesAEspacios (String codigo) {
        return codigo.trim().replace("-", " ");
    }
}

package com.carri1x.chess.movimientos;

import com.carri1x.chess.exceptions.AjedrezException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Coordenadas {
    private int fila;
    private int columna;
    private final static int DESDE = 0;
    private final static int HASTA = 7;
    private final static String[] letras = {"a", "b", "c", "d", "e", "f", "g", "h"};

    @JsonCreator
    public Coordenadas(@JsonProperty("fila") int fila, @JsonProperty("columna") int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    public static boolean isPosicionPermitida(Coordenadas nuevaPosicion) {
        return nuevaPosicion.getFila() >= DESDE && nuevaPosicion.getFila() <= HASTA
            && nuevaPosicion.getColumna() >= DESDE && nuevaPosicion.getColumna() <= HASTA;
    }

    public static String coordenadasToString(Coordenadas coordenadas) {
        return String.format("%s%d", letras[coordenadas.getColumna()], coordenadas.getFila() + 1);
    }

    public static Coordenadas stringToCoordenadas(String coordStr) throws AjedrezException {
        if (coordStr == null || coordStr.length() != 2)
            throw new AjedrezException("La coordenada debe tener exactamente dos caracteres (Ej: b1).");

        String[] caracteres = coordStr.split("");
        String columnaStr = caracteres[0];
        Integer columna = null;

        int fila;
        try {
            fila = Integer.parseInt(caracteres[1]) - 1;
        } catch (NumberFormatException e) {
            throw new AjedrezException("El segundo carácter debe ser un número entero válido.");
        }

        for (int i = 0; i < letras.length; i++) {
            if (letras[i].equalsIgnoreCase(columnaStr)) {
                columna = i;
                break;
            }
        }

        if (columna == null)
            throw new AjedrezException("No hay ningún valor que sea referente a esta columna: " + columnaStr);

        Coordenadas resultado = new Coordenadas(fila, columna);
        if (!isPosicionPermitida(resultado))
            throw new AjedrezException("La posición está fuera de los límites del tablero (debe ser de a1 a h8).");

        return resultado;
    }
}

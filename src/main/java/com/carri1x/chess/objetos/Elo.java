package com.carri1x.chess.objetos;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Elo {
    private String clasificacion;
    private Integer puntos;
    private Integer mmr;
}

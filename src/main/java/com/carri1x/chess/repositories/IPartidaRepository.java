package com.carri1x.chess.repositories;

import com.carri1x.chess.objetos.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface IPartidaRepository extends JpaRepository<Partida, UUID> {
    @Query("SELECT p FROM Partida p WHERE p.jugadorBlancas.id = :idJugador OR p.jugadorNegras.id = :idJugador")
    Optional<Partida> getPartidaByIdJugador(@Param("idJugador") UUID idJugador);
}

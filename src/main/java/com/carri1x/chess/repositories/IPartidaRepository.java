package com.carri1x.chess.repositories;

import com.carri1x.chess.objetos.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IPartidaRepository extends JpaRepository<Partida, UUID> {
    @Query("SELECT p FROM Partida p WHERE p.jugadorBlancas.id = :idJugador OR p.jugadorNegras.id = :idJugador")
    Optional<Partida> getPartidaByIdJugador(@Param("idJugador") UUID idJugador);
}

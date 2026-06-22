package com.carri1x.chess.repositories;

import com.carri1x.chess.objetos.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IJugadorRepository extends JpaRepository<Jugador, UUID> {
    public Optional<Jugador> getJugadorById (UUID idJugador);
    public Optional<Jugador> getJugadorByCookie (UUID cookie);
    public Optional<Jugador> getJugadorByNombre (String nombre);
}

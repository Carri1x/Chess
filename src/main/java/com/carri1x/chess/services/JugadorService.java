package com.carri1x.chess.services;

import com.carri1x.chess.RedisService;
import com.carri1x.chess.objetos.Jugador;
import com.carri1x.chess.repositories.IJugadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class JugadorService {
    @Autowired
    private IJugadorRepository jugadorRepository;
    @Autowired
    RedisService redisService;

    public Optional<Jugador> getJugadorById(UUID idJugador) {
        return jugadorRepository.getJugadorById(idJugador);
    }
    public Optional<Jugador> getJugadorByNombre (String nombre) {
        return jugadorRepository.getJugadorByNombre(nombre);
    }
    public  Optional<Jugador> getJugadorByCookie(UUID cookie) {
        return jugadorRepository.getJugadorByCookie(cookie);
    }
    public Jugador save(Jugador jugador) {
        return jugadorRepository.save(jugador);
    }

    // ------------- REDIS -------------
    public void guardarJugadorRedis(Jugador jugador) throws RuntimeException{
        String key = "jugador:"+jugador.getId();
        redisService.save(key, jugador);
    }
    public Optional<Jugador> getJugadorRedisById(UUID idJugador) {
        String key = "jugador:"+idJugador;
        return redisService.getById(key, Jugador.class);
    }

}

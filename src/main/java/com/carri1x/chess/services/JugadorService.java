package com.carri1x.chess.services;

import com.carri1x.chess.RedisService;
import com.carri1x.chess.enums.Colores;
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
    public Jugador save(Jugador jugador) {
        return jugadorRepository.save(jugador);
    }
    public Jugador buscarOCrearJugador(UUID idJugador, String nombre) {
        Jugador jugador = null;
        Optional<Jugador> jugadorOptional;
        if(idJugador != null) {
            // Si previamente hay una cookie, cogemos el jugador.
            jugadorOptional = getJugadorById(idJugador);
            // En caso de que haya o no algún error creamos otro jugador y lo guardamos en la base de datos.
            jugador = jugadorOptional.orElseGet(
                    () -> save(new Jugador(nombre, Colores.BLANCO))
            );
        } else {
            // Si no hay cookie, es porque previamente el jugador no estaba guardado en nuestra base de datos y lo que hacemos es guardarlo y dejar una cookie en su memoria para que su experiencia de usuario sea mejor.
            jugador = save(new Jugador(nombre, Colores.BLANCO));
        }
        return jugador;
    }

    // ------------- REDIS -------------
    public void guardarJugadorRedis(Jugador jugador) throws RuntimeException{
        String key = "jugador:"+jugador.getId();
        redisService.save(key, jugador);
    }
    public Optional<Jugador> getJugadorRedisById(UUID idJugador) {
        String key = "jugador:"+idJugador;
        return redisService.getByKey(key, Jugador.class);
    }

}

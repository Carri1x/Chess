package com.carri1x.chess.services;

import com.carri1x.chess.enums.Colores;
import com.carri1x.chess.exceptions.AjedrezException;
import com.carri1x.chess.exceptions.ConvertirPiezaException;
import com.carri1x.chess.movimientos.Coordenadas;
import com.carri1x.chess.objetos.Jugador;
import com.carri1x.chess.objetos.Partida;
import com.carri1x.chess.objetos.Tablero;
import com.carri1x.chess.piezas.Peon;
import com.carri1x.chess.piezas.Pieza;
import com.carri1x.chess.repositories.IPartidaRepository;
import com.carri1x.chess.requests.AjedrezRequest;
import com.carri1x.chess.requests.ConvertirRequest;
import com.carri1x.chess.requests.CrearPartidaRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PartidaService {

    @Autowired
    IPartidaRepository partidaRepository;
    @Autowired
    JugadorService jugadorService;
    @Autowired
    RedisService redisService;

    public void crearPartida(CrearPartidaRequest crearPartidaRequest) {
        String nombre = crearPartidaRequest.getNombre();
        String codigoEntrada = crearPartidaRequest.getCodigoEntrada();
        UUID cookie = crearPartidaRequest.getCookie();
        Jugador jugador = jugadorService.buscarOCrearJugador(cookie, nombre);

        // Guardamos el código/contraseña de la partida asociado al jugador que está esperando la nueva partida.
        redisService.save(codigoEntrada, jugador);
    }

    public Partida unirsePartida(CrearPartidaRequest crearPartidaRequest) throws AjedrezException {
        String nombre = crearPartidaRequest.getNombre();
        String codigoEntrada = crearPartidaRequest.getCodigoEntrada();
        UUID cookie = crearPartidaRequest.getCookie();
        Jugador jugador = jugadorService.buscarOCrearJugador(cookie, nombre);

        // Aquí cogemos el jugador gracias al código de la entrada y creamos la partida. Si no existiera con ese código lanzamos la excepción.
        Jugador creador = redisService.getByKey(codigoEntrada, Jugador.class).orElseThrow(
                () -> new AjedrezException("No hay ninguna partida creada con este código: "+codigoEntrada)
        );

        // Si coinciden los colores dejamos que el color del creador sean las blancas.
        if(jugador.getColor() == creador.getColor()) {
            creador.setColor(Colores.BLANCO);
            jugador.setColor(Colores.NEGRO);
        }

        return new Partida(creador, jugador);
    }

    public Partida ejecutarMovimiento(UUID idJugador, AjedrezRequest request) throws AjedrezException {
        Optional<UUID> partidaIdRedis = request.getPartidaId();
        Jugador jugador;
        Partida partida;

        if(partidaIdRedis.isEmpty()) {
            // 1. PostgreSQL: quién es el jugador y en qué partida está.
            jugador = jugadorService.getJugadorById(idJugador)
                    .orElseThrow(() -> new AjedrezException("No existe este jugador"));

            partida = getPartidaByJugador(jugador)
                    .orElseThrow(() -> new AjedrezException("No se ha iniciado aún una partida."));
            // Guardamos tanto el jugador como la partida en Redis para así hacer las peticiones muy rápidas.
            jugadorService.guardarJugadorRedis(jugador);
            guardarPartidaRedis(partida);
        } else {
            // 1. Buscamos la partida en Redis.
            partida = getPartidaRedisById(partidaIdRedis.get())
                    .orElseThrow(() -> new AjedrezException("No hay ninguna partida iniciada"));
            // Si está el jugador en Redis, si no hacemos el else.
            Optional<Jugador> jugadorOptional = jugadorService.getJugadorRedisById(idJugador);
            if(jugadorOptional.isPresent()) {
                jugador = jugadorOptional.get();
            } else {
                // Si no está en redis, pues no nos queda otra que buscarlo otra vez en la base de datos y a posteriori guardarlo en Redis.
                jugador = jugadorService.getJugadorById(idJugador)
                        .orElseThrow(() -> new AjedrezException("No existe este jugador"));
                jugadorService.guardarJugadorRedis(jugador);
            }
        }

        // 3. Buscamos la pieza en el tablero cargado de Redis
        Pieza pieza = partida.getTablero().getPiezaById(request.getPiezaId())
                .orElseThrow(() -> new AjedrezException("No se encontró ninguna pieza con ese ID en el tablero"));

        if (pieza.getColor() != jugador.getColor())
            throw new AjedrezException("No puedes mover una ficha que no es tuya");

        // 4. Ejecutamos el movimiento
        try {
            partida.movimiento(pieza, request.getCoordenadas(), jugador);
        } catch (ConvertirPiezaException ex) {
            // El peón llegó a última fila: guardamos el estado (el peón ya se movió) y propagamos
            guardarPartidaRedis(partida);
            ex.setTablero(partida.getTablero());
            throw ex;
        }

        // 5. Redis: guardamos el nuevo estado de la partida
        guardarPartidaRedis(partida);
        return partida;
    }

    public Partida ejecutarConversion(UUID idJugador, ConvertirRequest request) throws AjedrezException {
        Optional<UUID> partidaIdRedis = request.getPartidaId();
        Jugador jugador;
        Partida partida;
        if(partidaIdRedis.isEmpty()) {
            // 1. PostgreSQL: quién es el jugador y en qué partida está.
            jugador = jugadorService.getJugadorById(idJugador)
                    .orElseThrow(() -> new AjedrezException("No existe este jugador"));

            partida = getPartidaByJugador(jugador)
                    .orElseThrow(() -> new AjedrezException("No se ha iniciado aún una partida."));
            // Guardamos tanto el jugador como la partida en Redis para así hacer las peticiones muy rápidas.
            jugadorService.guardarJugadorRedis(jugador);
            guardarPartidaRedis(partida);
        } else {
            // 1. Buscamos la partida en Redis.
            partida = getPartidaRedisById(partidaIdRedis.get())
                    .orElseThrow(() -> new AjedrezException("No hay ninguna partida iniciada"));
            // Si está el jugador en Redis, si no hacemos el else.
            Optional<Jugador> jugadorOptional = jugadorService.getJugadorRedisById(idJugador);
            if(jugadorOptional.isPresent()) {
                jugador = jugadorOptional.get();
            } else {
                // Si no está en redis, pues no nos queda otra que buscarlo otra vez en la base de datos y a posteriori guardarlo en Redis.
                jugador = jugadorService.getJugadorById(idJugador)
                        .orElseThrow(() -> new AjedrezException("No existe este jugador"));
            }
            jugadorService.guardarJugadorRedis(jugador);
        }
        Tablero tablero = partida.getTablero();

        // Recogemos los datos que se han pasado en la request.
        Coordenadas coordenadas = request.getCoordenadas();
        Pieza piezaElegida = request.getPiezaElegida();

        // Buscamos si hay realmente una pieza a convertir en esas coordenadas...
        Pieza piezaAReemplazar = tablero.getPieza(coordenadas)
                .orElseThrow(() -> new ConvertirPiezaException("No existe ninguna pieza que promocionar en esta ubicación: "
                        + Coordenadas.coordenadasToString(coordenadas)));

        if (!Pieza.comparePosicionIgualesCoordenadas(piezaAReemplazar, coordenadas))
            throw new AjedrezException("No coincide la posición de la pieza con las coordenadas indicadas");

        if (!(piezaAReemplazar instanceof Peon))
            throw new ConvertirPiezaException("No se puede convertir una pieza que no sea un peón");

        // 3. Convertimos, cambiamos turno, calculamos estado y guardamos en Redis
        tablero.convertirPieza(piezaAReemplazar, piezaElegida, coordenadas);
        partida.cambiarTurno();
        partida.actualizarEstadoJuego(jugador.getColor());
        guardarPartidaRedis(partida);
        return partida;
    }


    public Optional<Partida> getPartidaByJugador(Jugador jugador) {
        return partidaRepository.getPartidaByIdJugador(jugador.getId());
    }

    public void guardarPartidaRedis(Partida partida) throws RuntimeException{
        String key = "partida:"+partida.getId();
        redisService.save(key, partida);
    }

    public Optional<Partida> getPartidaRedisById(UUID partidaId) throws RuntimeException{
        String key = "partida:"+partidaId;
        return redisService.getByKey(key, Partida.class);
    }
}

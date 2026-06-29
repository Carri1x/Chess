package com.carri1x.chess.controllers;

import com.carri1x.chess.enums.Colores;
import com.carri1x.chess.enums.EstadoJuego;
import com.carri1x.chess.exceptions.AjedrezException;
import com.carri1x.chess.exceptions.ConvertirPiezaException;
import com.carri1x.chess.objetos.Jugador;
import com.carri1x.chess.objetos.Partida;
import com.carri1x.chess.requests.AjedrezRequest;
import com.carri1x.chess.requests.ConvertirRequest;
import com.carri1x.chess.requests.CrearPartidaRequest;
import com.carri1x.chess.responses.PartidaResponse;
import com.carri1x.chess.responses.Response;
import com.carri1x.chess.responses.TableroResponse;
import com.carri1x.chess.services.PartidaService;
import com.carri1x.chess.services.RedisService;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chess")
public class AjedrezController {

    @Autowired
    PartidaService partidaService;
    @Autowired
    RedisService redisService; // Por ahora este servicio es para ver si redis está usandose bien en mi app.

    @PostMapping("/create")
    public ResponseEntity<Response> crear(@RequestBody CrearPartidaRequest crearPartidaRequest) {
        partidaService.crearPartida(crearPartidaRequest);
        return ResponseEntity.ok(new Response(true, 201, "Partida creada, esperando al oponente"));
    }

    @PostMapping("/join")
    public ResponseEntity<Response> unirse(@RequestBody CrearPartidaRequest crearPartidaRequest) {
        Partida partida = null;
        try {
            partida = partidaService.unirsePartida(crearPartidaRequest);
        } catch (AjedrezException e) {
            return ResponseEntity.ok(new Response(false, 400, e.getMessage()));
        }
        return ResponseEntity.ok(new PartidaResponse(true, 200, "Unido a la partida correctamente", partida));
    }

    @PostMapping("/movimiento/{idJugador}")
    public ResponseEntity<Response> mover(
            @RequestBody AjedrezRequest ajedrezRequest,
            @PathVariable UUID idJugador) {

        try {
            Partida partida = partidaService.ejecutarMovimiento(idJugador, ajedrezRequest);
            return ResponseEntity.ok(new TableroResponse(true, 200, "Movimiento ejecutado correctamente", partida.getTablero(), partida.getEstadoJuego()));
        } catch (ConvertirPiezaException ex) {
            return ResponseEntity.ok(new TableroResponse(true, 200, ex.getMessage(), ex.getTablero()));
        } catch (AjedrezException ex) {
            return ResponseEntity.ok(new Response(false, 400, ex.getMessage()));
        }
    }

    @PostMapping("/convertir/{idJugador}")
    public ResponseEntity<Response> convertirFicha(
            @RequestBody ConvertirRequest convertirRequest,
            @PathVariable UUID idJugador) {

        try {
            Partida partida = partidaService.ejecutarConversion(idJugador, convertirRequest);
            return ResponseEntity.ok(new TableroResponse(true, 200, "Pieza convertida correctamente", partida.getTablero()));
        } catch (AjedrezException ex) {
            return ResponseEntity.ok(new Response(false, 400, ex.getMessage()));
        }
    }

    @GetMapping("/redis/jugador/{key}")
    public ResponseEntity<?> getRedisJugador (@PathVariable String key) {
        return ResponseEntity.ok(redisService.getByKey(key, Jugador.class).orElse( new Jugador("No encontrado", Colores.BLANCO)));
    }
    @GetMapping("/redis/partida/{key}")
    public ResponseEntity<?> getRedisObjects (@PathVariable String key) {
        return ResponseEntity.ok(redisService.getByKey(key, Partida.class).orElseThrow());
    }
}



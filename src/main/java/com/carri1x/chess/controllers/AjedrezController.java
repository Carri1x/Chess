package com.carri1x.chess.controllers;

import com.carri1x.chess.enums.EstadoJuego;
import com.carri1x.chess.exceptions.AjedrezException;
import com.carri1x.chess.exceptions.ConvertirPiezaException;
import com.carri1x.chess.objetos.Partida;
import com.carri1x.chess.requests.AjedrezRequest;
import com.carri1x.chess.requests.ConvertirRequest;
import com.carri1x.chess.requests.CrearPartidaRequest;
import com.carri1x.chess.responses.AjedrezResponse;
import com.carri1x.chess.services.PartidaService;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chess")
public class AjedrezController {

    @Autowired
    PartidaService partidaService;

    @PostMapping("/create")
    public ResponseEntity<?> crear(@RequestBody CrearPartidaRequest crearPartidaRequest) {
        Partida partida = partidaService.crearPartida(crearPartidaRequest);

        return ResponseEntity.ok(true);
    }

    @PostMapping("/movimiento/{idJugador}")
    public ResponseEntity<AjedrezResponse> mover(
            @RequestBody AjedrezRequest ajedrezRequest,
            @PathVariable UUID idJugador) {

        try {
            Partida partida = partidaService.ejecutarMovimiento(idJugador, ajedrezRequest);
            return ResponseEntity.ok(new AjedrezResponse(true, 200, "Movimiento ejecutado correctamente", partida.getTablero(), partida.getEstadoJuego()));
        } catch (ConvertirPiezaException ex) {
            return ResponseEntity.ok(new AjedrezResponse(true, 200, ex.getMessage(), ex.getTablero(), EstadoJuego.EN_JUEGO, true));
        } catch (AjedrezException ex) {
            return ResponseEntity.ok(new AjedrezResponse(false, 400, ex.getMessage()));
        }
    }

    @PostMapping("/convertir/{idJugador}")
    public ResponseEntity<AjedrezResponse> convertirFicha(
            @RequestBody ConvertirRequest convertirRequest,
            @PathVariable UUID idJugador) {

        try {
            Partida partida = partidaService.ejecutarConversion(idJugador, convertirRequest);
            return ResponseEntity.ok(new AjedrezResponse(true, 200, "Pieza convertida correctamente", partida.getTablero(), partida.getEstadoJuego(), true));
        } catch (AjedrezException ex) {
            return ResponseEntity.ok(new AjedrezResponse(false, 400, ex.getMessage()));
        }
    }
}



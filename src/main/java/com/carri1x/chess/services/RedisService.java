package com.carri1x.chess.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public <T> void save(String key, T objeto) {
        try {
            String json = objectMapper.writeValueAsString(objeto);
            redisTemplate.opsForValue().set(key, json);
        } catch (Exception e) {
            throw new RuntimeException("Error al serializar en Redis",e);
        }
    }

    public <T> Optional<T> getByKey(String key, Class<T> claseDestino) {
        try {
            String json = redisTemplate.opsForValue().get(key);
            if (json == null) {
                return Optional.empty();
            }
            // Aquí uso la clase que paso por parámetro (Partida.class, Jugador.class, etc.)
            T objeto = objectMapper.readValue(json, claseDestino);
            return Optional.of(objeto);
        } catch (Exception e) {
            throw new RuntimeException("Error al deserializar el objeto desde Redis", e);
        }
    }
}

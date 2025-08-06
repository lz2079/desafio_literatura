package com.alura.desafio.literatura.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.stereotype.Component;

@Component
public class ConvierteDatos implements IConvierteDatos {
    private ObjectMapper objectMapper = JsonMapper.builder().build();

    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {

            return objectMapper.readValue(json, clase);
        } catch (Exception e) {

            throw new RuntimeException("Error al convertir JSON a objeto Java: " + e.getMessage(), e);

        }
    }

}

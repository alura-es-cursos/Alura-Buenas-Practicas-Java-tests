package com.aluracursos.adopet.api.controller;

import com.aluracursos.adopet.api.service.AdopcionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class AdopcionControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AdopcionService adopcionService;


    @Test
    void deberiaDevolverCodigo400ParaSolicitudConErrores() throws Exception {
        //ARRANGE
        String json = "{}";

        //ACT
        var response = mvc.perform(
            post("/adopciones")
                    .content(json)
                    .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(400,response.getStatus());
    }


    @Test
    void deberiaDevolverCodigo200ParaSolicitudSinErrores() throws Exception {
        //ARRANGE
        String json = """
                {
                    "idMascota": 1,
                    "idTutor": 1,
                    "motivo": "Cualquier motivo"
                }
                """;

        //ACT
        var response = mvc.perform(
                post("/adopciones")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(200,response.getStatus());
    }
}
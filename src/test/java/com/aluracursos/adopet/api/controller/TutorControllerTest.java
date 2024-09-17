package com.aluracursos.adopet.api.controller;

import com.aluracursos.adopet.api.service.TutorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
class TutorControllerTest {

    @MockBean
    private TutorService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deberiaDevolverCodigo200ParaRequisicionDeRegistrarTutor() throws Exception {
        //Arrange
        String json = """
                {
                    "nombre": "Rodrigo",
                    "telefono": "(21)0000-9090",
                    "email": "email@example.com.br"
                }
                """;
        //Act
        MockHttpServletResponse response = mockMvc.perform(
                post("/tutores")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //Assert
        assertEquals(200,response.getStatus());
    }

    @Test
    void deberiaDevolverCodigo400ParaRequisicionRegistrarTutorDatosInvalidos() throws Exception {
        //Arrange
        String json = """
                {
                    "nombre": "Rodrigo",
                    "telefono": "(21)0000-90900",
                    "email":"email@example.com.br"
                }
                """;

        //Act
        MockHttpServletResponse response = mockMvc.perform(
                post("/tutores")
                        .contentType(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //Assert
        assertEquals(400,response.getStatus());
    }

    @Test
    void deberiaDevolverCodigo200ParaRequisicionDeActualizarTutor() throws Exception {
        //Arrange
        String json = """
                {
                    "id" : "1",
                    "nombre": "Rodrigo",
                    "telefono": "(21)0000-9090",
                    "email": "email@example.com"
                }
                """;

        //Act
        MockHttpServletResponse response = mockMvc.perform(
                put("/tutores")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //Assert
        assertEquals(200,response.getStatus());
    }

    @Test
    void deberiaDevolverCodigo400ParaRequisicionDeActualizarTutor() throws Exception {
        //Arrange
        String json = """
                {
                    "id" : "2",
                    "nombre": "Rodrigo",
                    "telefono": "(21)0000-90900",
                    "email":"email@example.com.br"
                }
                """;

        //Act
        MockHttpServletResponse response = mockMvc.perform(
                put("/tutores")
                        .contentType(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //Assert
        assertEquals(400,response.getStatus());
    }

}
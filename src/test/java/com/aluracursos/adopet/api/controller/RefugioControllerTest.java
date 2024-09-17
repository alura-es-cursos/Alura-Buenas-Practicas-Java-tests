package com.aluracursos.adopet.api.controller;

import com.aluracursos.adopet.api.exceptions.ValidacionException;
import com.aluracursos.adopet.api.model.Refugio;
import com.aluracursos.adopet.api.service.MascotaService;
import com.aluracursos.adopet.api.service.RefugioService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@SpringBootTest
@AutoConfigureMockMvc
class RefugioControllerTest {

    @MockBean
    private RefugioService refugioService;

    @MockBean
    private MascotaService mascotaService;

    @Mock
    private Refugio refugio;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deberiaDevolverCodigo200ParaRequisicionDeListarRefugios() throws Exception {
        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                get("/refugios")
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200,response.getStatus());
    }

    @Test
    void deberiaDevolverCodigo200ParaRequisicionDeRegistrarRefugio() throws Exception {
        //ARRANGE
        String json = """
                {
                    "nombre": "Refugio feliz",
                    "telefono": "(94)0000-9090",
                    "email": "email@example.com.br"
                }
                """;

        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                post("/refugios")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200,response.getStatus());
    }

    @Test
    void deberiaDevolverCodigo400ParaRequisicionDeRegistrarRefugio() throws Exception {
        //ARRANGE
        String json = """
                {
                    "nombre": "Refugio feliz",
                    "telefono": "(94)0000-90900",
                    "email": "email@example.com.br"
                }
                """;

        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                post("/refugios")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(400,response.getStatus());
    }

    @Test
    void deberiaDevolverCodigo200ParaRequisicionDeListarMascotasDelRefugioPorNombre() throws Exception {
        //Arrange
        String nombre = "Refugio feliz";

        //Act
        MockHttpServletResponse response = mockMvc.perform(
                get("/refugios/{nombre}/mascotas",nombre)
        ).andReturn().getResponse();

        //Assert
        assertEquals(200,response.getStatus());
    }

    @Test
    void deberiaDevolverCodigo200ParaRequisicionDeListarMascotasDelRefugioPorId() throws Exception {
        //Arrange
        String id = "1";

        //Act
        MockHttpServletResponse response = mockMvc.perform(
                get("/refugios/{id}/mascotas",id)
        ).andReturn().getResponse();

        //Assert
        assertEquals(200,response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo400ParaRequisicionDeListarMascotasDelRefugioPorIdInvalido() throws Exception {
        //Arrange
        String id = "1";
        given(refugioService.listarMascotasDelRefugio(id)).willThrow(ValidacionException.class);

        //Act
        MockHttpServletResponse response = mockMvc.perform(
                get("/refugios/{id}/mascotas",id)
        ).andReturn().getResponse();

        //Assert
        assertEquals(404,response.getStatus());
    }

    @Test
    void deberiaDevolverCodigo400ParaRequisicionDeListarMascotasDelRefugioPorNombreInvalido() throws Exception {
        //Arrange
        String nombre = "Miau";
        given(refugioService.listarMascotasDelRefugio(nombre)).willThrow(ValidacionException.class);

        //Act
        MockHttpServletResponse response = mockMvc.perform(
                get("/refugios/{nombre}/mascotas",nombre)
        ).andReturn().getResponse();

        //Assert
        assertEquals(404,response.getStatus());
    }

    @Test
    void deberiaDevolverCodigo200ParaRequisicionDeRegistrarMascotasPorId() throws Exception {
        //Arrange
        String json = """
                {
                    "tipo": "GATO",
                    "nombre": "Miau",
                    "raza": "normal",
                    "edad": "5",
                    "color" : "Parda",
                    "peso": "6.4"
                }
                """;

        String abrigoId = "1";

        //Act
        MockHttpServletResponse response = mockMvc.perform(
                post("/refugios/idONombre}/mascotas",abrigoId)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //Assert
        assertEquals(200,response.getStatus());
    }

    @Test
    void deberiaDevolverCodigo200ParaRequisicionDeRegistrarMascotaPorNombre() throws Exception {
        //Arrange
        String json = """
                {
                    "tipo": "GATO",
                    "nombre": "Miau",
                    "raza": "normal",
                    "edad": "5",
                    "color" : "Parda",
                    "peso": "6.4"
                }
                """;

        String abrigoNombre = "Refugio feliz";

        //Act
        MockHttpServletResponse response = mockMvc.perform(
                post("/refugios/{idONombre}/mascotas",abrigoNombre)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //Assert
        assertEquals(200,response.getStatus());
    }


    @Test
    void deberiaDevolverCodigo404ParaRequisicionDeRegistrarMascotaRefugioNoEncontradoPorId() throws Exception {
        //Arrange
        String json = """
                {
                    "tipo": "GATO",
                    "nombre": "Miau",
                    "raza": "normal",
                    "edad": "5",
                    "color" : "Parda",
                    "peso": "6.4"
                }
                """;

        String idONombre = "1";

        given(refugioService.cargarRefugio(idONombre)).willThrow(ValidacionException.class);

        //Act
        MockHttpServletResponse response = mockMvc.perform(
                post("/refugios/{idONombre}/mascotas",idONombre)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //Assert
        assertEquals(404,response.getStatus());
    }

    @Test
    void deberiaDevolverCodigo404ParaRequisicionDeRegistarMascotaRefugioNoEncontradoPorNombre() throws Exception {
        //Arrange
        String json = """
                {
                    "tipo": "GATO",
                    "nombre": "Miau",
                    "raza": "normal",
                    "edad": "5",
                    "color" : "Parda",
                    "peso": "6.4"
                }
                """;

        String refugioNombre = "Abrigo legal";

        given(refugioService.cargarRefugio(refugioNombre)).willThrow(ValidacionException.class);

        //Act
        MockHttpServletResponse response = mockMvc.perform(
                post("/refugios/{idONombre}/mascotas",refugioNombre)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //Assert
        assertEquals(404,response.getStatus());
    }

}
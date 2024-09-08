package com.aluracursos.adopet.api.service;

import com.aluracursos.adopet.api.dto.RegistroMascotaDto;
import com.aluracursos.adopet.api.dto.RegistroRefugioDto;
import com.aluracursos.adopet.api.model.Mascota;
import com.aluracursos.adopet.api.model.ProbabilidadAdopcion;
import com.aluracursos.adopet.api.model.Refugio;
import com.aluracursos.adopet.api.model.TipoMascota;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CalculadoraProbabilidadAdopcionTest {


    @Test
    void deberiaDevolverProbabilidadAltaParaMascotaConEdadBajaPesoBajo(){
        //Edad 4 años y 4 kilos -> ALTA
        //ARRANGE (organizar)
        RegistroMascotaDto registroMascotaDto= new RegistroMascotaDto(
                TipoMascota.GATO,
                "Miau",
                "Siamés",
                4,
                "Gris",
                4.0f
        );
        Refugio refugio = new Refugio(new RegistroRefugioDto(
                "Refugio feliz",
                "94999999999",
                "refugiofeliz@email.com.br"
        ));
        Mascota mascota = new Mascota(registroMascotaDto, refugio);

        //ACT (acción)
        CalculadoraProbabilidadAdopcion calculadora = new CalculadoraProbabilidadAdopcion();
        ProbabilidadAdopcion probabilidad = calculadora.calcular(mascota);

        //ASSERT (comprobación)
        Assertions.assertEquals(ProbabilidadAdopcion.ALTA, probabilidad);
    }


    @Test
    void deberiaDevolverProbabilidadMediaParaMascotaConEdadAltaPesoBajo(){
        //Edad 15 años y 4 kilos -> MEDIA

        RegistroMascotaDto registroMascotaDto= new RegistroMascotaDto(
                TipoMascota.GATO,
                "Miau",
                "Siamés",
                15,
                "Gris",
                4.0f
        );
        Refugio refugio = new Refugio(new RegistroRefugioDto(
                "Refugio feliz",
                "94999999999",
                "refugiofeliz@email.com.br"
        ));
        Mascota mascota = new Mascota(registroMascotaDto, refugio);

        CalculadoraProbabilidadAdopcion calculadora = new CalculadoraProbabilidadAdopcion();
        ProbabilidadAdopcion probabilidad = calculadora.calcular(mascota);

        Assertions.assertEquals(ProbabilidadAdopcion.MEDIA, probabilidad);
    }

}
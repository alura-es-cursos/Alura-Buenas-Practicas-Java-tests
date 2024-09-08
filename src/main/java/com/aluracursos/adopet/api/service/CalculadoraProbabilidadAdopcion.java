package com.aluracursos.adopet.api.service;

import com.aluracursos.adopet.api.model.Mascota;
import com.aluracursos.adopet.api.model.ProbabilidadAdopcion;
import com.aluracursos.adopet.api.model.TipoMascota;

public class CalculadoraProbabilidadAdopcion {

    public ProbabilidadAdopcion calcular(Mascota mascota) {
        int nota = calcularNota(mascota);

        if (nota >= 8) {
            return ProbabilidadAdopcion.ALTA;
        }

        if (nota >= 5) {
            return ProbabilidadAdopcion.MEDIA;
        }

        return ProbabilidadAdopcion.BAJA;
    }

    private int calcularNota(Mascota mascota) {
        int peso = mascota.getPeso().intValue();
        int edad = mascota.getEdad();
        TipoMascota tipo = mascota.getTipo();

        int nota = 10;

        // penalizando por el peso muy alto
        if (tipo == TipoMascota.PERRO && peso > 15) {
            nota -= 2;
        }
        if (tipo == TipoMascota.GATO && peso > 10) {
            nota -= 2;
        }

        // penalizando por la edad avanzada
        if (edad >= 15) {
            nota -= 5;
        }else if (edad >= 10) {
            nota -= 4;
        }

        return nota;
    }
}

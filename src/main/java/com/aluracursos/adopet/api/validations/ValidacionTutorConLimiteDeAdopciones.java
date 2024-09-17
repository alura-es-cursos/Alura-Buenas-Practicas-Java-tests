package com.aluracursos.adopet.api.validations;

import com.aluracursos.adopet.api.dto.SolicitudAdopcionDTO;
import com.aluracursos.adopet.api.exceptions.ValidacionException;
import com.aluracursos.adopet.api.model.Adopcion;
import com.aluracursos.adopet.api.model.StatusAdopcion;
import com.aluracursos.adopet.api.model.Tutor;
import com.aluracursos.adopet.api.repository.AdopcionRepository;
import com.aluracursos.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ValidacionTutorConLimiteDeAdopciones implements ValidacionesSolicitudAdopcion{

    @Autowired
    private AdopcionRepository adopcionRepository;

    @Autowired
    private TutorRepository tutorRepository;

    /*public void validar(SolicitudAdopcionDTO dto) {
        Tutor tutor = tutorRepository.getReferenceById(dto.idTutor());
        List<Adopcion> adopciones = adopcionRepository.findAll();
        for (Adopcion a : adopciones) {
            int contador = 0;
            if (a.getTutor() == tutor && a.getStatus() == StatusAdopcion.APROBADO) {
                contador = contador + 1;
            }
            if (contador == 5) {
                throw new ValidacionException("Tutor llegó al limite máximo de 5 adopciones!");
            }
        }
    }*/
    public void validar(SolicitudAdopcionDTO dto) {
        Integer adocoesTutor = adopcionRepository.countByTutorIdAndStatus(dto.idTutor(), StatusAdopcion.APROBADO);

        if (adocoesTutor == 5) {
            throw new ValidacionException("Tutor llegó al limite máximo de 5 adopciones!");
        }
    }
}

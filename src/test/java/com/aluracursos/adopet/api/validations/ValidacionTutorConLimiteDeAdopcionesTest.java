package com.aluracursos.adopet.api.validations;

import com.aluracursos.adopet.api.dto.SolicitudAdopcionDTO;
import com.aluracursos.adopet.api.exceptions.ValidacionException;
import com.aluracursos.adopet.api.model.StatusAdopcion;
import com.aluracursos.adopet.api.model.Tutor;
import com.aluracursos.adopet.api.repository.AdopcionRepository;
import com.aluracursos.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ValidacionTutorConLimiteDeAdopcionesTest {
    @InjectMocks
    private ValidacionTutorConLimiteDeAdopciones validador;

    @Mock
    private AdopcionRepository adopcionRepository;

    @Mock
    private SolicitudAdopcionDTO dto;


    @Test
    void noDeberiaPermitirSolicitudDeAdopcionTutorConLimiteDe5Adopciones() {
        //Arrange
        given(adopcionRepository.countByTutorIdAndStatus(dto.idTutor(), StatusAdopcion.APROBADO)).willReturn(5);

        //Act + Assert
        assertThrows(ValidacionException.class,() ->validador.validar(dto));
    }

    @Test
    void deberiaPermitirSolicitudDeAdopcionTutorSinLimiteDe5Adopciones() {
        //Arrange
        given(adopcionRepository.countByTutorIdAndStatus(dto.idTutor(),StatusAdopcion.APROBADO)).willReturn(4);

        //Act + Assert
        assertDoesNotThrow(() -> validador.validar(dto));
    }

}
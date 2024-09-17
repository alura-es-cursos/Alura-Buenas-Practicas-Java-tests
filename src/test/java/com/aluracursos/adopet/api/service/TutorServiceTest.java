package com.aluracursos.adopet.api.service;

import com.aluracursos.adopet.api.dto.ActualizacionTutorDto;
import com.aluracursos.adopet.api.dto.RegistroTutorDto;
import com.aluracursos.adopet.api.exceptions.ValidacionException;
import com.aluracursos.adopet.api.model.Tutor;
import com.aluracursos.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class TutorServiceTest {

    @InjectMocks
    private TutorService service;

    @Mock
    private TutorRepository repository;

    @Mock
    private RegistroTutorDto dto;

    @Mock
    private Tutor tutor;

    @Mock
    private ActualizacionTutorDto atualizacionTutorDto;

    @Test
    void NoDeberiaRegistrarTutorTelefonoOEmailYaRegistrado() {
        //Arrange + Act
        given(repository.findByTelefonoOrEmail(dto.telefono(), dto.email())).willReturn(true);

        //Assert
        assertThrows(ValidacionException.class, () -> service.registrar(dto));
    }

    @Test
    void deberiaRegistrarTutor() {
        //Arrange
        given(repository.findByTelefonoOrEmail(dto.telefono(), dto.email())).willReturn(false);

        //Act + Assert
        assertDoesNotThrow(() -> service.registrar(dto));
        then(repository).should().save(new Tutor(dto));
    }

    @Test
    void deveriaAtualizarDadosTutor() {
        //Arrange
        given(repository.getReferenceById(atualizacionTutorDto.id())).willReturn(tutor);

        //Act
        service.actualizar(atualizacionTutorDto);

        //Assert
        then(tutor).should().actualizarDatos(atualizacionTutorDto);
    }

}
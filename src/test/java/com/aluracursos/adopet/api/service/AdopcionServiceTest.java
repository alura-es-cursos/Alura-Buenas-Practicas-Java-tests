package com.aluracursos.adopet.api.service;

import com.aluracursos.adopet.api.dto.SolicitudAdopcionDTO;
import com.aluracursos.adopet.api.model.Adopcion;
import com.aluracursos.adopet.api.model.Mascota;
import com.aluracursos.adopet.api.model.Refugio;
import com.aluracursos.adopet.api.model.Tutor;
import com.aluracursos.adopet.api.repository.AdopcionRepository;
import com.aluracursos.adopet.api.repository.MascotaRepository;
import com.aluracursos.adopet.api.repository.TutorRepository;
import com.aluracursos.adopet.api.validations.ValidacionesSolicitudAdopcion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class AdopcionServiceTest {

    @InjectMocks
    private AdopcionService service;

    @Mock
    private AdopcionRepository adopcionRepository;

    @Mock
    private MascotaRepository mascotaRepository;

    @Mock
    private TutorRepository tutorRepository;

    @Mock
    private EmailService emailService;

    @Spy
    private List<ValidacionesSolicitudAdopcion> validaciones = new ArrayList<>();

    @Mock
    private  ValidacionesSolicitudAdopcion validador1;

    @Mock
    private  ValidacionesSolicitudAdopcion validador2;

    @Mock
    private Mascota mascota;

    @Mock
    private Tutor tutor;

    @Mock
    private Refugio refugio;

    private SolicitudAdopcionDTO dto;

    @Captor
    private ArgumentCaptor<Adopcion> adopcionCaptor;


    @Test
    void deberiaGuardarAdopcionAlSolicitar(){
        //ARRANGE
        this.dto = new SolicitudAdopcionDTO(10L, 20L, "Cualquier motivo");
        given(mascotaRepository.getReferenceById(dto.idMascota())).willReturn(mascota);
        given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
        given(mascota.getRefugio()).willReturn(refugio);

        //ACT
        service.solicitar(dto);

        //ASSERT
        then(adopcionRepository).should().save(adopcionCaptor.capture());
        Adopcion adopcionGuardada = adopcionCaptor.getValue();
        Assertions.assertEquals(mascota, adopcionGuardada.getMascota());
        Assertions.assertEquals(tutor, adopcionGuardada.getTutor());
        Assertions.assertEquals(dto.motivo(), adopcionGuardada.getMotivo());
    }

    @Test
    void deberiaLlamarAValidadoresAlSolicitar(){
        //ARRANGE
        this.dto = new SolicitudAdopcionDTO(10L, 20L, "Cualquier motivo");
        given(mascotaRepository.getReferenceById(dto.idMascota())).willReturn(mascota);
        given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
        given(mascota.getRefugio()).willReturn(refugio);
        this.validaciones.add(validador1);
        this.validaciones.add(validador2);

        //ACT
        service.solicitar(dto);

        //ASSERT
        BDDMockito.then(validador1).should().validar(dto);
        BDDMockito.then(validador2).should().validar(dto);
    }

}
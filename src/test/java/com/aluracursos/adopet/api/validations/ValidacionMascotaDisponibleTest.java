package com.aluracursos.adopet.api.validations;

import com.aluracursos.adopet.api.dto.SolicitudAdopcionDTO;
import com.aluracursos.adopet.api.exceptions.ValidacionException;
import com.aluracursos.adopet.api.model.Mascota;
import com.aluracursos.adopet.api.repository.MascotaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidacionMascotaDisponibleTest {

    @InjectMocks
    private ValidacionMascotaDisponible validacion;

    @Mock
    private MascotaRepository mascotaRepository;

    @Mock
    private Mascota mascota;

    @Mock
    private SolicitudAdopcionDTO dto;

    @Test
    void deberiaPermitirSolicitudAdopcionMascota(){
        //ARRANGE
        BDDMockito.given(mascotaRepository.getReferenceById(dto.idMascota())).willReturn(mascota);
        BDDMockito.given(mascota.getAdoptada()).willReturn(false);

        //ASSERT + ACT
        Assertions.assertDoesNotThrow(() -> validacion.validar(dto));
    }

    @Test
    void noDeberiaPermitirSolicitudAdopcionMascota(){
        //ARRANGE
        BDDMockito.given(mascotaRepository.getReferenceById(dto.idMascota())).willReturn(mascota);
        BDDMockito.given(mascota.getAdoptada()).willReturn(true);

        //ASSERT + ACT
        Assertions.assertThrows(ValidacionException.class, () -> validacion.validar(dto));
    }

}
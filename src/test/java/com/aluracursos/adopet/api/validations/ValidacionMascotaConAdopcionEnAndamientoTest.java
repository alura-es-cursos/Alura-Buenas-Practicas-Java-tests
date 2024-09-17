package com.aluracursos.adopet.api.validations;

import com.aluracursos.adopet.api.dto.SolicitudAdopcionDTO;
import com.aluracursos.adopet.api.exceptions.ValidacionException;
import com.aluracursos.adopet.api.model.StatusAdopcion;
import com.aluracursos.adopet.api.repository.AdopcionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ValidacionMascotaConAdopcionEnAndamientoTest {

    @InjectMocks
    private ValidacionMascotaConAdopcionEnAndamiento validador;

    @Mock
    private AdopcionRepository adopcionRepository;

    @Mock
    private SolicitudAdopcionDTO dto;

    @Test
    void noDeberiaPermitirSolicitudDeAdopcionDeMascoraConPedidoEnAndamiento() {
        //Arrange
        given(adopcionRepository.existsByMascotaIdAndStatus(
                dto.idMascota(),
                StatusAdopcion.ESPERANDO_EVALUACION)
        ).willReturn(true);

        //Act + Assert
        assertThrows(ValidacionException.class, () -> validador.validar(dto));
    }

    @Test
    void deberiaPermitirSolicituDeAdopcionDeMascotaConPedidoInexistente() {
        //Arrange
        given(adopcionRepository.existsByMascotaIdAndStatus(
                dto.idMascota(),
                StatusAdopcion.ESPERANDO_EVALUACION
        )).willReturn(false);

        //Act + Assert
        assertDoesNotThrow(()->validador.validar(dto));
    }

}
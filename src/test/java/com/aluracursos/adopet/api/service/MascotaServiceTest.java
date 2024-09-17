package com.aluracursos.adopet.api.service;

import com.aluracursos.adopet.api.dto.RegistroMascotaDto;
import com.aluracursos.adopet.api.model.Mascota;
import com.aluracursos.adopet.api.model.Refugio;
import com.aluracursos.adopet.api.repository.MascotaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class MascotaServiceTest {

    @InjectMocks
    private MascotaService service;

    @Mock
    private RegistroMascotaDto registroMascotaDto;

    @Mock
    private MascotaRepository repository;

    @Mock
    private Refugio refugio;

    @Test
    void deberiaRegistrarMascota() {
        //Act
        service.registrarMascota(refugio,registroMascotaDto);

        //Assert
        then(repository).should().save(new Mascota(registroMascotaDto,refugio));
    }

    @Test
    void deberiaRetornarTodosLasMascotasDisponibles() {
        //Act
        service.buscarMascotasDisponibles();

        //Assert
        then(repository).should().findAllByAdoptadaFalse();
    }

}
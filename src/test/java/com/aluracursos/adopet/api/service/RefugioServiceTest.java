package com.aluracursos.adopet.api.service;

import com.aluracursos.adopet.api.model.Refugio;
import com.aluracursos.adopet.api.repository.MascotaRepository;
import com.aluracursos.adopet.api.repository.RefugioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class RefugioServiceTest {

    @InjectMocks
    private RefugioService service;

    @Mock
    private RefugioRepository repository;

    @Mock
    private Refugio refugio;

    @Mock
    private MascotaRepository mascotaRepository;

    @Test
    void deberiaLlamarListaDeTodosLosRefugios() {
        //Act
        service.listar();

        //Assert
        then(repository).should().findAll();
    }

    @Test
    void deberiaLlamarListaDeMascotasDelRefugioAtravesDelNombre() {
        //Arrange
        String nombre = "Miau";
        given(repository.findByNombre(nombre)).willReturn(Optional.of(refugio));

        //Act
        service.listarMascotasDelRefugio(nombre);

        //Assert
        then(mascotaRepository).should().findByRefugio(refugio);
    }

}
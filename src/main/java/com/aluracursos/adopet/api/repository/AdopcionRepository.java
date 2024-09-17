package com.aluracursos.adopet.api.repository;

import com.aluracursos.adopet.api.model.Adopcion;
import com.aluracursos.adopet.api.model.StatusAdopcion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdopcionRepository extends JpaRepository<Adopcion, Long> {

    boolean existsByMascotaIdAndStatus(Long mascotaId, StatusAdopcion status);

    boolean existsByTutorIdAndStatus(Long idPet, StatusAdopcion status);

    Integer countByTutorIdAndStatus(Long idTutor, StatusAdopcion status);
}

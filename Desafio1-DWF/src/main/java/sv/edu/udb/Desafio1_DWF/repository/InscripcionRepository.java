package sv.edu.udb.Desafio1_DWF.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sv.edu.udb.Desafio1_DWF.model.Inscripcion;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Integer> {
}
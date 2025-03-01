package sv.edu.udb.Desafio1_DWF.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import sv.edu.udb.Desafio1_DWF.model.Evento;

public interface EventoRepository extends JpaRepository<Evento, Integer> {
}
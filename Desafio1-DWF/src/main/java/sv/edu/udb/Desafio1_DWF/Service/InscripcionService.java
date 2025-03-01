package sv.edu.udb.Desafio1_DWF.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sv.edu.udb.Desafio1_DWF.model.Inscripcion;
import sv.edu.udb.Desafio1_DWF.repository.InscripcionRepository;

import java.util.List;

@Service
public class InscripcionService {
    @Autowired
    private InscripcionRepository inscripcionRepository;

    public Inscripcion registrarInscripcion(Inscripcion inscripcion) {
        return inscripcionRepository.save(inscripcion);
    }

    public List<Inscripcion> listarInscripciones() {
        return inscripcionRepository.findAll();
    }
}
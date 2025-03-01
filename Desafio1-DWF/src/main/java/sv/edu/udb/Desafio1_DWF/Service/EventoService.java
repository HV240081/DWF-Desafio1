package sv.edu.udb.Desafio1_DWF.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sv.edu.udb.Desafio1_DWF.model.Evento;
import sv.edu.udb.Desafio1_DWF.repository.EventoRepository;

import java.util.List;

@Service
public class EventoService {
    @Autowired
    private EventoRepository eventoRepository;

    public Evento registrarEvento(Evento evento) {
        return eventoRepository.save(evento);
    }

    public List<Evento> listarEventos() {
        return eventoRepository.findAll();
    }

    public void eliminarEvento(Integer id) {
        eventoRepository.deleteById(id);
    }
}
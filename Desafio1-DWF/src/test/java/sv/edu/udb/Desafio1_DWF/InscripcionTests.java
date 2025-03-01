package sv.edu.udb.Desafio1_DWF;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sv.edu.udb.Desafio1_DWF.model.Inscripcion;
import sv.edu.udb.Desafio1_DWF.model.Evento;
import sv.edu.udb.Desafio1_DWF.Service.InscripcionService;
import sv.edu.udb.Desafio1_DWF.Service.EventoService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class InscripcionTests {
    @Autowired
    private InscripcionService inscripcionService;

    @Autowired
    private EventoService eventoService;

    @Test
    public void inscripcionExitosaDeportista() {
        Evento evento = eventoService.listarEventos().get(0);
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setNombreDeportista("Ana Torres");
        inscripcion.setDocumentoIdentificacion("C12345678");
        inscripcion.setTelefono("+50312345678");
        inscripcion.setEvento(evento);
        inscripcion.setEdadDeportista(20);
        inscripcion.setCondicionMedica(false);

        Inscripcion inscripcionGuardada = inscripcionService.registrarInscripcion(inscripcion);
        assertNotNull(inscripcionGuardada);
        assertEquals(inscripcion.getNombreDeportista(), inscripcionGuardada.getNombreDeportista());
    }

    @Test
    public void inscripcionEventoInexistente() {
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setNombreDeportista("Ana Torres");
        inscripcion.setDocumentoIdentificacion("C12345678");
        inscripcion.setTelefono("+50312345678");
        inscripcion.setEvento(null); // Evento inexistente
        inscripcion.setEdadDeportista(20);
        inscripcion.setCondicionMedica(false);

        assertThrows(Exception.class, () -> {
            inscripcionService.registrarInscripcion(inscripcion);
        });
    }
}
package sv.edu.udb.Desafio1_DWF;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sv.edu.udb.Desafio1_DWF.model.Evento;
import sv.edu.udb.Desafio1_DWF.Service.EventoService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EventoTests {
    @Autowired
    private EventoService eventoService;

    @Test
    public void registrarEventoExitosamente() {
        Evento evento = new Evento();
        evento.setNombreEvento("Torneo de Ajedrez");
        evento.setFechaInicio(LocalDate.of(2025, 6, 1));
        evento.setFechaFin(LocalDate.of(2025, 6, 2));
        evento.setRangoHoras(LocalTime.of(10, 0));
        evento.setTipoEvento("Deportivo");
        evento.setRangoEdad("15-30");
        evento.setRequerimientoSalud(false);
        evento.setCostoTotal(BigDecimal.valueOf(20.00));

        Evento eventoGuardado = eventoService.registrarEvento(evento);
        assertNotNull(eventoGuardado);
        assertEquals(evento.getNombreEvento(), eventoGuardado.getNombreEvento());
    }

    @Test
    public void registrarEventoConFechasInvalidas() {
        Evento evento = new Evento();
        evento.setNombreEvento("Torneo de Ajedrez");
        evento.setFechaInicio(LocalDate.of(2025, 6, 2));
        evento.setFechaFin(LocalDate.of(2025, 6, 1));
        evento.setRangoHoras(LocalTime.of(10, 0));
        evento.setTipoEvento("Deportivo");
        evento.setRangoEdad("15-30");
        evento.setRequerimientoSalud(false);
        evento.setCostoTotal(BigDecimal.valueOf(20.00));

        assertThrows(Exception.class, () -> {
            eventoService.registrarEvento(evento);
        });
    }
}
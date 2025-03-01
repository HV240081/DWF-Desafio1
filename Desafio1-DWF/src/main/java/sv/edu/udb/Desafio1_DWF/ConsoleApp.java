package sv.edu.udb.Desafio1_DWF;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import sv.edu.udb.Desafio1_DWF.model.Evento;
import sv.edu.udb.Desafio1_DWF.model.Inscripcion;
import sv.edu.udb.Desafio1_DWF.Service.EventoService;
import sv.edu.udb.Desafio1_DWF.Service.InscripcionService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.regex.Pattern;

@Component
public class ConsoleApp implements CommandLineRunner {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private InscripcionService inscripcionService;

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Registrar nuevo evento");
            System.out.println("2. Listar eventos");
            System.out.println("3. Inscribir deportista a evento");
            System.out.println("4. Listar inscripciones");
            System.out.println("5. Salir");
            int opcion = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcion) {
                case 1:
                    registrarEvento(scanner);
                    break;
                case 2:
                    listarEventos();
                    break;
                case 3:
                    inscribirDeportista(scanner);
                    break;
                case 4:
                    listarInscripciones();
                    break;
                case 5:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private void registrarEvento(Scanner scanner) {
        Evento evento = new Evento();

        System.out.println("Ingrese el nombre del evento:");
        evento.setNombreEvento(scanner.nextLine());

        evento.setFechaInicio(ingresarFecha(scanner, "inicio"));
        evento.setFechaFin(ingresarFecha(scanner, "fin"));

        if (evento.getFechaFin().isBefore(evento.getFechaInicio())) {
            System.out.println("Error: La fecha de fin no puede ser anterior a la fecha de inicio.");
            return;
        }

        evento.setRangoHoras(ingresarHora(scanner));

        System.out.println("Ingrese el tipo de evento:");
        evento.setTipoEvento(scanner.nextLine());

        System.out.println("Ingrese el rango de edad:");
        evento.setRangoEdad(scanner.nextLine());

        System.out.println("¿Requiere salud? (true/false):");
        evento.setRequerimientoSalud(Boolean.parseBoolean(scanner.nextLine()));

        System.out.println("Ingrese el costo total:");
        evento.setCostoTotal(ingresarCosto(scanner));

        eventoService.registrarEvento(evento);
        System.out.println("Evento registrado exitosamente.");
    }

    private void inscribirDeportista(Scanner scanner) {
        Inscripcion inscripcion = new Inscripcion();

        System.out.println("Ingrese el nombre del deportista:");
        inscripcion.setNombreDeportista(scanner.nextLine());

        inscripcion.setDocumentoIdentificacion(ingresarDUI(scanner));

        System.out.println("Ingrese el teléfono:");
        inscripcion.setTelefono(scanner.nextLine());

        System.out.println("Ingrese el ID del evento:");
        int eventoId = scanner.nextInt();
        scanner.nextLine(); // Consumir la nueva línea

        Evento evento = eventoService.listarEventos().stream()
                .filter(e -> e.getId() == eventoId)
                .findFirst()
                .orElse(null);

        if (evento == null) {
            System.out.println("Error: El evento no existe.");
            return;
        }

        inscripcion.setEvento(evento);

        System.out.println("Ingrese la edad del deportista:");
        inscripcion.setEdadDeportista(scanner.nextInt());
        scanner.nextLine();

        System.out.println("¿Tiene alguna condición médica? (true/false):");
        inscripcion.setCondicionMedica(Boolean.parseBoolean(scanner.nextLine()));

        if (!validarCondicionesInscripcion(inscripcion, evento)) {
            System.out.println("Error: El deportista no cumple con las condiciones del evento.");
            return;
        }

        inscripcionService.registrarInscripcion(inscripcion);
        System.out.println("Deportista inscrito exitosamente.");
    }

    private boolean validarCondicionesInscripcion(Inscripcion inscripcion, Evento evento) {
        String[] rangoEdad = evento.getRangoEdad().split("-");
        int edadMinima = Integer.parseInt(rangoEdad[0]);
        int edadMaxima = Integer.parseInt(rangoEdad[1]);

        if (inscripcion.getEdadDeportista() < edadMinima || inscripcion.getEdadDeportista() > edadMaxima) {
            return false;
        }

        if (evento.getRequerimientoSalud() && !inscripcion.getCondicionMedica()) {
            return false;
        }

        return true;
    }

    private LocalDate ingresarFecha(Scanner scanner, String tipo) {
        LocalDate fecha = null;
        while (fecha == null) {
            try {
                System.out.println("Ingrese la fecha de " + tipo + " (YYYY-MM-DD):");
                fecha = LocalDate.parse(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Fecha inválida. Intente nuevamente.");
            }
        }
        return fecha;
    }

    private LocalTime ingresarHora(Scanner scanner) {
        LocalTime hora = null;
        while (hora == null) {
            try {
                System.out.println("Ingrese el rango de horas (HH:MM):");
                hora = LocalTime.parse(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Hora inválida. Intente nuevamente.");
            }
        }
        return hora;
    }

    private BigDecimal ingresarCosto(Scanner scanner) {
        BigDecimal costo = null;
        while (costo == null) {
            try {
                System.out.println("Ingrese el costo total:");
                costo = new BigDecimal(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Costo inválido. Intente nuevamente.");
            }
        }
        return costo;
    }

    private String ingresarDUI(Scanner scanner) {
        String dui = null;
        while (dui == null) {
            System.out.println("Ingrese el documento de identificación (DUI) en formato 12345678-9:");
            dui = scanner.nextLine();
            if (!Pattern.matches("\\d{8}-\\d", dui)) {
                System.out.println("Formato de DUI inválido. Intente nuevamente.");
                dui = null;
            }
        }
        return dui;
    }

    private void listarEventos() {
        System.out.println("Lista de eventos:");
        for (Evento evento : eventoService.listarEventos()) {
            System.out.println(evento);
        }
    }

    private void listarInscripciones() {
        System.out.println("Lista de inscripciones:");
        for (Inscripcion inscripcion : inscripcionService.listarInscripciones()) {
            System.out.println(inscripcion);
        }
    }
}
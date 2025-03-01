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
            System.out.println("5. Modificar evento");
            System.out.println("6. Modificar inscripción");
            System.out.println("7. Borrar evento");
            System.out.println("8. Borrar inscripción");
            System.out.println("9. Salir");
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
                    modificarEvento(scanner);
                    break;
                case 6:
                    modificarInscripcion(scanner);
                    break;
                case 7:
                    borrarEvento(scanner);
                    break;
                case 8:
                    borrarInscripcion(scanner);
                    break;
                case 9:
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

    private void modificarEvento(Scanner scanner) {
        System.out.println("Ingrese el ID del evento a modificar:");
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

        System.out.println("Ingrese el nuevo nombre del evento (deje en blanco para no modificar):");
        String nombre = scanner.nextLine();
        if (!nombre.isEmpty()) {
            evento.setNombreEvento(nombre);
        }

        System.out.println("Ingrese la nueva fecha de inicio (YYYY-MM-DD) (deje en blanco para no modificar):");
        String fechaInicio = scanner.nextLine();
        if (!fechaInicio.isEmpty()) {
            evento.setFechaInicio(LocalDate.parse(fechaInicio));
        }

        System.out.println("Ingrese la nueva fecha de fin (YYYY-MM-DD) (deje en blanco para no modificar):");
        String fechaFin = scanner.nextLine();
        if (!fechaFin.isEmpty()) {
            evento.setFechaFin(LocalDate.parse(fechaFin));
        }

        if (evento.getFechaFin().isBefore(evento.getFechaInicio())) {
            System.out.println("Error: La fecha de fin no puede ser anterior a la fecha de inicio.");
            return;
        }

        System.out.println("Ingrese el nuevo rango de horas (HH:MM) (deje en blanco para no modificar):");
        String rangoHoras = scanner.nextLine();
        if (!rangoHoras.isEmpty()) {
            evento.setRangoHoras(LocalTime.parse(rangoHoras));
        }

        System.out.println("Ingrese el nuevo tipo de evento (deje en blanco para no modificar):");
        String tipoEvento = scanner.nextLine();
        if (!tipoEvento.isEmpty()) {
            evento.setTipoEvento(tipoEvento);
        }

        System.out.println("Ingrese el nuevo rango de edad (deje en blanco para no modificar):");
        String rangoEdad = scanner.nextLine();
        if (!rangoEdad.isEmpty()) {
            evento.setRangoEdad(rangoEdad);
        }

        System.out.println("¿Requiere salud? (true/false) (deje en blanco para no modificar):");
        String requerimientoSalud = scanner.nextLine();
        if (!requerimientoSalud.isEmpty()) {
            evento.setRequerimientoSalud(Boolean.parseBoolean(requerimientoSalud));
        }

        System.out.println("Ingrese el nuevo costo total (deje en blanco para no modificar):");
        String costoTotal = scanner.nextLine();
        if (!costoTotal.isEmpty()) {
            evento.setCostoTotal(new BigDecimal(costoTotal));
        }

        eventoService.registrarEvento(evento);
        System.out.println("Evento modificado exitosamente.");
    }

    private void modificarInscripcion(Scanner scanner) {
        System.out.println("Ingrese el ID de la inscripción a modificar:");
        int inscripcionId = scanner.nextInt();
        scanner.nextLine(); // Consumir la nueva línea

        Inscripcion inscripcion = inscripcionService.listarInscripciones().stream()
                .filter(i -> i.getId() == inscripcionId)
                .findFirst()
                .orElse(null);

        if (inscripcion == null) {
            System.out.println("Error: La inscripción no existe.");
            return;
        }

        System.out.println("Ingrese el nuevo nombre del deportista (deje en blanco para no modificar):");
        String nombre = scanner.nextLine();
        if (!nombre.isEmpty()) {
            inscripcion.setNombreDeportista(nombre);
        }

        System.out.println("Ingrese el nuevo documento de identificación (DUI) (deje en blanco para no modificar):");
        String documentoIdentificacion = scanner.nextLine();
        if (!documentoIdentificacion.isEmpty()) {
            inscripcion.setDocumentoIdentificacion(documentoIdentificacion);
        }

        System.out.println("Ingrese el nuevo teléfono (deje en blanco para no modificar):");
        String telefono = scanner.nextLine();
        if (!telefono.isEmpty()) {
            inscripcion.setTelefono(telefono);
        }

        System.out.println("Ingrese el nuevo ID del evento (deje en blanco para no modificar):");
        String eventoId = scanner.nextLine();
        if (!eventoId.isEmpty()) {
            Evento evento = eventoService.listarEventos().stream()
                    .filter(e -> e.getId() == Integer.parseInt(eventoId))
                    .findFirst()
                    .orElse(null);
            if (evento != null) {
                inscripcion.setEvento(evento);
            } else {
                System.out.println("Error: El evento no existe.");
                return;
            }
        }
        System.out.println("Ingrese la nueva edad del deportista (deje en blanco para no modificar):");
        String edadDeportista = scanner.nextLine();
        if (!edadDeportista.isEmpty()) {
            inscripcion.setEdadDeportista(Integer.parseInt(edadDeportista));
        }

        System.out.println("¿Tiene alguna condición médica? (true/false) (deje en blanco para no modificar):");
        String condicionMedica = scanner.nextLine();
        if (!condicionMedica.isEmpty()) {
            inscripcion.setCondicionMedica(Boolean.parseBoolean(condicionMedica));
        }

        inscripcionService.registrarInscripcion(inscripcion);
        System.out.println("Inscripción modificada exitosamente.");
    }

    private void borrarEvento(Scanner scanner) {
        System.out.println("Ingrese el ID del evento a borrar:");
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

        eventoService.eliminarEvento(eventoId);
        System.out.println("Evento borrado exitosamente.");
    }

    private void borrarInscripcion(Scanner scanner) {
        System.out.println("Ingrese el ID de la inscripción a borrar:");
        int inscripcionId = scanner.nextInt();
        scanner.nextLine(); // Consumir la nueva línea

        Inscripcion inscripcion = inscripcionService.listarInscripciones().stream()
                .filter(i -> i.getId() == inscripcionId)
                .findFirst()
                .orElse(null);

        if (inscripcion == null) {
            System.out.println("Error: La inscripción no existe.");
            return;
        }

        inscripcionService.eliminarInscripcion(inscripcionId);
        System.out.println("Inscripción borrada exitosamente.");
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
                System.out.println("Ingrese el documento de identificación (DUI) en formato 01234567-8:");
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
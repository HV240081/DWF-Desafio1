package sv.edu.udb.Desafio1_DWF.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "eventos")
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre_evento", nullable = false, unique = true)
    private String nombreEvento;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @Column(name = "rango_horas", nullable = false)
    private LocalTime rangoHoras;

    @Column(name = "tipo_evento", nullable = false)
    private String tipoEvento;

    @Column(name = "rango_edad", nullable = false)
    private String rangoEdad;

    @Column(name = "requerimiento_salud", nullable = false)
    private Boolean requerimientoSalud;

    @Column(name = "costo_total", nullable = false)
    private BigDecimal costoTotal;
}

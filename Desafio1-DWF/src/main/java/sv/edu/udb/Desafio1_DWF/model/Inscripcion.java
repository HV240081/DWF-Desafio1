package sv.edu.udb.Desafio1_DWF.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "inscripciones")
public class Inscripcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre_deportista", nullable = false)
    private String nombreDeportista;

    @Column(name = "documento_identificacion", nullable = false)
    private String documentoIdentificacion;

    @Column(name = "telefono", nullable = false)
    private String telefono;

    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    @Column(name = "edad_deportista", nullable = false)
    private Integer edadDeportista;

    @Column(name = "condicion_medica", nullable = false)
    private Boolean condicionMedica;
}

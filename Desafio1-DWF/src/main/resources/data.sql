INSERT INTO eventos (nombre_evento, fecha_inicio, fecha_fin, rango_horas, tipo_evento, rango_edad, requerimiento_salud, costo_total)
VALUES
    ('Torneo de Fútbol', '2025-03-10', '2025-03-15', '08:00:00', 'Deportivo', '10-15', FALSE, 50.00),
    ('Maratón Solidario', '2025-04-01', '2025-04-01', '06:00:00', 'Deportivo', '18-40', TRUE, 0),
    ('Concurso de Baile', '2025-05-20', '2025-05-21', '14:00:00', 'Cultural', '12-30', FALSE, 30.00);

INSERT INTO inscripciones (nombre_deportista, documento_identificacion, telefono, evento_id, edad_deportista, condicion_medica)
VALUES
    ('Juan Pérez', '00000000-0', '+50377778888', 1, 14, FALSE),
    ('María López', '01234567-8', '+50366665555', 2, 25, TRUE),
    ('Carlos Gómez', '09876543-2', '+50344443333', 3, 20, FALSE);

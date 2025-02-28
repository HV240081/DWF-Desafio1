CREATE TABLE eventos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre_evento VARCHAR(100) NOT NULL UNIQUE,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    rango_horas TIME NOT NULL,
    tipo_evento VARCHAR(50) NOT NULL,
    rango_edad VARCHAR(10) NOT NULL,
    requerimiento_salud BOOLEAN NOT NULL,
    costo_total DECIMAL(10,2) CHECK (costo_total >= 0) NOT NULL
);

CREATE TABLE inscripciones (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre_deportista VARCHAR(100) NOT NULL,
    documento_identificacion VARCHAR(20) NOT NULL,
    telefono VARCHAR(15) NOT NULL,
    evento_id INT NOT NULL,
    edad_deportista INT CHECK (edad_deportista > 0) NOT NULL,
    condicion_medica BOOLEAN NOT NULL,
    FOREIGN KEY (evento_id) REFERENCES eventos(id) ON DELETE CASCADE
);

-- Crear base de datos
CREATE DATABASE IF NOT EXISTS databaselapaz;
USE Data_Inventario_elementos;

-- Tabla de roles
CREATE TABLE rol (
    id_rol INT AUTO_INCREMENT PRIMARY KEY,
    nombre_rol VARCHAR(50) NOT NULL
);

-- Tabla de usuarios
CREATE TABLE usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    correo VARCHAR(100) UNIQUE NOT NULL,
    cedula VARCHAR(20) UNIQUE NOT NULL,
    contraseña VARCHAR(255) NOT NULL,
    rol_id INT NOT NULL,
    FOREIGN KEY (rol_id) REFERENCES rol(id_rol)
);

-- Tabla de colegios
CREATE TABLE colegio (
    id_colegio INT AUTO_INCREMENT PRIMARY KEY,
    nombre_colegio VARCHAR(100) NOT NULL,
    usuario_registra INT NOT NULL,
    FOREIGN KEY (usuario_registra) REFERENCES usuarios(id_usuario)
);

-- Tabla de sedes
CREATE TABLE sede (
    id_sede INT AUTO_INCREMENT PRIMARY KEY,
    nombre_sede VARCHAR(100) NOT NULL,
    colegio_id INT NOT NULL,
    usuario_id INT NOT NULL,
    FOREIGN KEY (colegio_id) REFERENCES colegio(id_colegio),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id_usuario)
);



--  Tabla de bloques
CREATE TABLE bloques (
    id_bloque INT AUTO_INCREMENT PRIMARY KEY,
    sede_id INT NOT NULL,
    usuario_id INT NOT NULL,
    FOREIGN KEY (sede_id) REFERENCES sede(id_sede),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id_usuario)
);

-- Tabla de pisos
CREATE TABLE pisos (
    id_piso INT AUTO_INCREMENT PRIMARY KEY,
    numero_piso INT NOT NULL,
    bloque_id INT NOT NULL,
    usuario_id INT NOT NULL,
    FOREIGN KEY (bloque_id) REFERENCES bloques(id_bloque),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id_usuario)
);

-- Tabla de aulas
CREATE TABLE aulas (
    id_aula INT AUTO_INCREMENT PRIMARY KEY,
    piso_id INT NOT NULL,
    usuario_id INT NOT NULL,
    FOREIGN KEY (piso_id) REFERENCES pisos(id_piso),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id_usuario)
);


-- Tabla de elementos
CREATE TABLE elementos (
    id_elemento INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    estado VARCHAR(50) NOT NULL,
    usuario_registra INT NOT NULL,
    aula_id INT NOT NULL,
    identificador_unico VARCHAR(100) UNIQUE NULL,
    tipo_identificador VARCHAR(300) NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_registra) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (aula_id) REFERENCES aulas(id_aula)
);

-- Tabla de elementos tecnológicos
CREATE TABLE elementos_tecnologicos (
    id_tecnologico INT AUTO_INCREMENT PRIMARY KEY,
    elemento_id INT NOT NULL,
    marca VARCHAR(50) NOT NULL,
    serie VARCHAR(100),
    FOREIGN KEY (elemento_id) REFERENCES elementos(id_elemento)
);

-- Tabla de elementos mobiliarios
CREATE TABLE elementos_mobiliarios (
    id_mobiliario INT AUTO_INCREMENT PRIMARY KEY,
    elemento_id INT NOT NULL,
    FOREIGN KEY (elemento_id) REFERENCES elementos(id_elemento)
);

-- Tabla de informes
CREATE TABLE informes (
    id_informe INT AUTO_INCREMENT PRIMARY KEY,
    tipo_informe ENUM('anual_aula', 'anual_eliminados', 'general_sede') NOT NULL,
    fecha_generacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario_generador INT NOT NULL,
    FOREIGN KEY (usuario_generador) REFERENCES usuarios(id_usuario)
);

-- Tabla de reporte
CREATE TABLE reporte (
    id_reporte INT AUTO_INCREMENT PRIMARY KEY,
    fecha_hora_reporte TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    descripcion TEXT NOT NULL,
    elemento_reportado INT NOT NULL,
    usuario_reporta INT NOT NULL,
    FOREIGN KEY (elemento_reportado) REFERENCES elementos(id_elemento),
    FOREIGN KEY (usuario_reporta) REFERENCES usuarios(id_usuario)
);

-- Tabla de historial de movimientos
CREATE TABLE historial_movimientos (
    id_historial_movimientos INT AUTO_INCREMENT PRIMARY KEY,
    tipo_elemento ENUM('mobiliario', 'tecnologico') NOT NULL,
    aula_origen INT NOT NULL,
    aula_destino INT NOT NULL,
    usuario_movio INT NOT NULL,
    fecha_movimiento TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (aula_origen) REFERENCES aulas(id_aula),
    FOREIGN KEY (aula_destino) REFERENCES aulas(id_aula),
    FOREIGN KEY (usuario_movio) REFERENCES usuarios(id_usuario)
);

-- Tabla de elementos eliminados
CREATE TABLE elementos_eliminados (
    id_elemento_eliminado INT AUTO_INCREMENT PRIMARY KEY,
    elemento_id INT NOT NULL,
    motivo_eliminacion TEXT NOT NULL,
    fecha_hora_eliminacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario_elimino INT NOT NULL,
    FOREIGN KEY (elemento_id) REFERENCES elementos(id_elemento),
    FOREIGN KEY (usuario_elimino) REFERENCES usuarios(id_usuario)
);
CREATE TABLE cambios_identificador (
    id_cambio INT AUTO_INCREMENT PRIMARY KEY,
    id_elemento INT NOT NULL,
    identificador_anterior VARCHAR(100),
    tipo_identificador_anterior VARCHAR(300),
    identificador_nuevo VARCHAR(100),
    tipo_identificador_nuevo VARCHAR(300),
    usuario_modifica INT NOT NULL,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_elemento) REFERENCES elementos(id_elemento),
    FOREIGN KEY (usuario_modifica) REFERENCES usuarios(id_usuario)
);

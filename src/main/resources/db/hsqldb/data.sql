-- Eliminar tablas si existen
DROP TABLE IF EXISTS artistas;
DROP TABLE IF EXISTS arrendadores;
DROP TABLE IF EXISTS usuarios;
CREATE TABLE usuarios (
    id INTEGER PRIMARY KEY,
    nombreUsuario VARCHAR(255),
    contrasena VARCHAR(255)
);
-- Creación de tablas
CREATE TABLE artistas (
    id INTEGER PRIMARY KEY,
    nombreUsuario VARCHAR(255),
    contrasena VARCHAR(255),
    nombreArtistico VARCHAR(255)
);
CREATE TABLE arrendadores (
    id INTEGER PRIMARY KEY,
    nombreUsuario VARCHAR(255),
    contrasena VARCHAR(255),
    nombreEmpresa VARCHAR(255)
);

-- Eliminar registros si existen
TRUNCATE TABLE artistas;
TRUNCATE TABLE arrendadores;

-- Inserción de datos
INSERT INTO usuarios(id, nombreUsuario, contrasena) VALUES (1, 'empresa1', '3mpr3s4');
INSERT INTO artistas(id, nombreUsuario, contrasena, nombreArtistico) VALUES (1, 'artista1', '4rt1st4', 'Lennon');
INSERT INTO arrendadores(id, nombreUsuario, contrasena, nombreEmpresa) VALUES (1, 'empresa1', '3mpr3s4', 'Alfonso');

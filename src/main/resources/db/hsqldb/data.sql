-- Creacion manual de artistas
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

INSERT INTO artistas(id, nombreUsuario, contrasena, nombreArtistico) VALUES (1, 'artista1', '4rt1st4', 'Lennon');

-- Creacion manual de arrendadores
INSERT INTO arrendadores(id, nombreUsuario, contrasena, nombreEmpresa) VALUES (1, 'empresa1', '3mpr3s4', 'Alfonso');
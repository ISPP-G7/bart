-- Eliminar tablas si existen
DROP TABLE IF EXISTS artistas;
DROP TABLE IF EXISTS arrendadores;
DROP TABLE IF EXISTS anuncios;

-- Creación de tablas
CREATE TABLE artistas (
    id INTEGER PRIMARY KEY,
    firstName VARCHAR(255),
    lastName VARCHAR(255),
    email VARCHAR(255),
    dob VARCHAR(255),
    gender VARCHAR(255),
    nombreArtistico VARCHAR(255),
    categoriaArtistica VARCHAR(255)
);
CREATE TABLE arrendadores (
    id INTEGER PRIMARY KEY,
    firstName VARCHAR(255),
    lastName VARCHAR(255),
    email VARCHAR(255),
    dob VARCHAR(255),
    gender VARCHAR(255),
    nombreLocal VARCHAR(255),
    direccion VARCHAR(255)
);

CREATE TABLE anuncios (
  id INT NOT NULL,
  titulo VARCHAR(50) NOT NULL,
  ubicacion VARCHAR(60) NOT NULL,
  precio FLOAT NOT NULL,
  especificaciones VARCHAR(300) NOT NULL,
  estilo VARCHAR(255) NOT NULL,
  artista_id INT ,
  arrendador_id INT,
  PRIMARY KEY (id),
  FOREIGN KEY (artista_id) REFERENCES artistas(id),
  FOREIGN KEY (arrendador_id) REFERENCES arrendadores(id)
);


-- Inserción de datos
INSERT INTO artistas(id, firstName, lastName, email, dob, gender, nombreArtistico, categoriaArtistica) VALUES
(1, 'Antonio', 'Perez', 'antonio@gmail.com', '11/05/1970', 'Male', 'Paco Sans', 'Rock');
INSERT INTO arrendadores(id, firstName, lastName, email, dob, gender, nombreLocal, direccion) VALUES
(1, 'Paco', 'Perez', 'paco@gmail.com', '11/05/1970', 'Male', 'Bar Paco', 'Renia Mercedes N3');
INSERT INTO anuncios(id,titulo, ubicacion, precio, especificaciones, estilo, artista_id) VALUES 
(1,'Título del anuncio', 'Ciudad, País', 1000.00, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.', 'ROCK',  1);
INSERT INTO anuncios(id,titulo, ubicacion, precio, especificaciones, estilo, arrendador_id) VALUES 
(2,'Título del anuncio', 'Ciudad, País', 1000.00, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.', 'RAP',  1);
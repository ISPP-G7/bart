TRUNCATE TABLE messages CASCADE;
TRUNCATE TABLE anuncios_artista CASCADE;
TRUNCATE TABLE anuncios_arrendador CASCADE;
TRUNCATE TABLE arrendadores CASCADE;
TRUNCATE TABLE artistas CASCADE;
TRUNCATE TABLE users CASCADE;

-- Creación de tablas
INSERT INTO users (id,first_name,last_name,email,dob,gender,password,es_arrendador,es_artista) VALUES
	(1,'José','Fernández', 'namnam@gmail.com','venta de bocadillos y dulce en reina mercedes', 'Masculino','joseNamNam',true,false),
	(2,'Manolo', 'Platero', 'platero@gmail.com','El mejor rock de los 80 hasta ahora', 'Masculino', 'platero', false, true);
INSERT INTO arrendadores (id,direccion,nombre_local,url_imagen) VALUES
	(1,'Avenida de la Reina Mercedes,31, Sevilla','Bocatería ÑAM ÑAM','https://media-cdn.tripadvisor.com/media/photo-s/13/0e/7f/68/sala-principal-escenario.jpg');
INSERT INTO artistas (id,nombre_artistico, categoria_artistica,url_imagen) VALUES
	(2,'Platonico', 'ROCK', 'https://media.gq.com.mx/photos/5f10af63ceda9ecf56c268df/4:3/w_3000,h_2250,c_limit/GettyImages-77348200-rock-alternativo-radiohead.jpg');
INSERT INTO	anuncios_artista (id,pseudonimo_artista,ubicacion,precio,descripcion_artista, requiere_microfono,requiere_instrumentos,requiere_iluminacion,requiere_altavoces,requiere_mesa_de_mezclas, requiere_portatil, requiere_otras_especificaciones,estilo,artista_id,esta_aceptado) VALUES
	(1,'platonico','Sevilla',300,'Toco rock fusión con tango',false,false,false,false,false,false,'', 'ROCK',2,false);
INSERT INTO	anuncios_arrendador (id,nombre_local,ubicacion,precio,descripcion_arrendador, estilo,ofrece_microfono,ofrece_instrumentos,ofrece_iluminacion,ofrece_altavoces, ofrece_mesa_de_mezclas,ofrece_portatil,ofrece_otras_especificaciones,arrendador_id,esta_aceptado) VALUES
	(1,'Bocadillos Ñam Ñam','Avenida Reina Mercedes,31',100,'A nuestro público les gusta el rock', 'ROCK',' ',' ',' ',' ',' ',' ',' ',1,false);
INSERT INTO messages (id,message_body,date,user_sender,user_receiver) VALUES
	(1,'Hola, estarías dispuesto a tocar en mi garito?','2023-03-22',1,2);
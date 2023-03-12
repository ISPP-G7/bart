-- Eliminar tablas si existen
DROP TABLE IF EXISTS public.messages CASCADE;
DROP TABLE IF EXISTS public.users CASCADE;
DROP TABLE IF EXISTS public.artistas CASCADE;
DROP TABLE IF EXISTS public.anuncios_artista CASCADE;
DROP TABLE IF EXISTS public.anuncios_arrendador CASCADE;
DROP TABLE IF EXISTS public.arrendadores CASCADE;
C
-- Creación de tablas
INSERT INTO public.users (id,dob,email,es_arrendador,es_artista,first_name,gender,last_name,"password") VALUES
	 (116,'2001-02-21','testArtista@gmail.com',false,true,'testArtista','Male','testArtista','testArtista'),
	 (118,'2023-03-02','testArrendador@gmail.com',true,false,'testArrendador','Male','testArrendador','testArrendador');
INSERT INTO public.arrendadores (direccion,nombre_local,url_imagen,id) VALUES
	 ('ÑAM ÑAM SEVILLA','testArrendador',NULL,118);
INSERT INTO public.artistas (categoria_artistica,nombre_artistico,url_imagen,id) VALUES
	 ('testArtista','testArtista','imagen.txt',116);
INSERT INTO public.anuncios_artista (id,arrendador_accept_id,descripcion_artista,esta_aceptado,estilo,precio,pseudonimo_artista,ubicacion,artista_id) VALUES
	 (117,NULL,'testArtista',false,'REGGAETON',12.0,'testArtista','testArtista',116);
INSERT INTO public.anuncios_arrendador (id,artista_accept_id,descripcion_arrendador,esta_aceptado,estilo,nombre_local,precio,ubicacion,arrendador_id) VALUES
	 (120,NULL,'testArrendador',false,'REGGAETON','testArrendador',12.0,'testArrendador',118);
INSERT INTO public.messages (id,message_body,user_receiver,user_sender) VALUES
	 (121,'12112',118,118);

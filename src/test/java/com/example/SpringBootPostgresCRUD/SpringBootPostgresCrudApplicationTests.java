package com.example.SpringBootPostgresCRUD;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.SpringBootPostgresCRUD.entity.Arrendador;
import com.example.SpringBootPostgresCRUD.entity.Artista;
import com.example.SpringBootPostgresCRUD.entity.Foto;
import com.example.SpringBootPostgresCRUD.repo.ArrendadorRepository;
import com.example.SpringBootPostgresCRUD.repo.ArtistaRepository;
import com.example.SpringBootPostgresCRUD.repo.FotoRepository;
import com.example.SpringBootPostgresCRUD.service.ArrendadorService;
import com.example.SpringBootPostgresCRUD.service.ArtistaService;
import com.example.SpringBootPostgresCRUD.service.FotoService;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class SpringBootPostgresCrudApplicationTests {

	@Mock
    private ArrendadorRepository arrendadorRepository;

	@Mock
    private ArtistaRepository artistaRepository;

	@Mock
	private FotoRepository fotoRepository;

    @InjectMocks
    private ArrendadorService arrendadorService;

	@InjectMocks
    private ArtistaService artistaService;

	@InjectMocks
    private FotoService fotoService;

    private static final Foto foto1 = new Foto(1L, null, "johnlennon@gmail.com");
	private static final Foto foto2 = new Foto(2L, null, "johnlennon@gmail.com");
	private static final Foto foto3 = new Foto(3L, null, "johnlennon@gmail.com");
    private static final List<Foto> fotosUsuario = new ArrayList<>();

	private Arrendador mockArrendador = new Arrendador();
	private Artista mockArtista = new Artista();

	@BeforeAll
	void setUp(){ //Mock data
		//Arrendador
		mockArrendador.setId(1L);
		mockArrendador.setFirstName("Anunakis");
		mockArrendador.setDireccion("Reina Mercedes, 19C");
		mockArrendador.setEmail("anunakis@gmail.com");

		//Artista
        mockArtista.setId(1L);
        mockArtista.setFirstName("John Lennon");
		mockArtista.setEmail("johnlennon@gmail.com");

		//Fotos
		fotosUsuario.add(foto1);
		fotosUsuario.add(foto2);
		fotosUsuario.add(foto3);
	}

	@Test
    void testGetArrendadorById() {
        // Mock repository method
        when(arrendadorRepository.findById(1L)).thenReturn(Optional.of(mockArrendador));

        // Call service method
        Arrendador Arrendador = arrendadorService.getArrendadorById(1L);

        // Verify result
        assertEquals(1L, Arrendador.getId());
        assertEquals("Anunakis", Arrendador.getFirstName());
		assertEquals("Reina Mercedes, 19C", Arrendador.getDireccion());
    }

	@Test
    void testGetArtistaById() {
        // Mock repository method
        when(artistaRepository.findById(1L)).thenReturn(Optional.of(mockArtista));

        // Call service method
        Artista Artista = artistaService.getArtistaById(1L);

        // Verify result
        assertEquals(1L, Artista.getId());
        assertEquals("John Lennon", Artista.getFirstName());
    }

    @Test
    void testGetArtistaByEmail() {
        // Mock repository method
        String mail = mockArtista.getEmail();
        when(artistaRepository.getArtistaByMailArtista(mail)).thenReturn(mockArtista);

        // Call service method
        Artista Artista = artistaService.getArtistaByMailArtista(mail);

        // Verify result
        assertEquals(1L, Artista.getId());
        assertEquals("John Lennon", Artista.getFirstName());
    }

	@Test
	void testGetArrendadorByMail() {
        // Mock repository method
        when(arrendadorRepository.getArrendadorByMailArrendador("anunakis@gmail.com")).thenReturn(mockArrendador);

        // Call service method
        Arrendador Arrendador = arrendadorService.getArrendadorByMailArrendador("anunakis@gmail.com");
		System.out.println(Arrendador.getEmail());

        // Verify result
        assertEquals(1L, Arrendador.getId());
        assertEquals("Anunakis", Arrendador.getFirstName());
	}

	@Test
	void testGetArtistaByMail() {
        // Mock repository method
        when(artistaRepository.getArtistaByMailArtista("johnlennon@gmail.com")).thenReturn(mockArtista);

        // Call service method
        Artista Artista = artistaService.getArtistaByMailArtista("johnlennon@gmail.com");
		
        // Verify result
        assertEquals(1L, Artista.getId());
        assertEquals("John Lennon", Artista.getFirstName());
	}

	@Test
    public void testFindByUser() {
		when(fotoRepository.findByUser("johnlennon@gmail.com")).thenReturn(fotosUsuario);
        List<Foto> fotos = fotoService.getFotosByUser("johnlennon@gmail.com");
        
		// Verify results
		assertEquals(3, fotos.size());
        assertEquals(foto1.getId(), fotos.get(0).getId());
        assertEquals(foto2.getId(), fotos.get(1).getId());
    }
}

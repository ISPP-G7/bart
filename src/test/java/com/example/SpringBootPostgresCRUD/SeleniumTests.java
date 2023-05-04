package com.example.SpringBootPostgresCRUD;

// Generated by Selenium IDE
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.After;
import org.junit.AfterClass;

import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.SpringBootPostgresCRUD.entity.Artista;
import com.example.SpringBootPostgresCRUD.repo.ArrendadorRepository;
import com.example.SpringBootPostgresCRUD.repo.ArtistaRepository;
import com.example.SpringBootPostgresCRUD.service.ArrendadorService;
import com.example.SpringBootPostgresCRUD.service.ArtistaService;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SeleniumTests {
  public static WebDriver driver;
  public static Map<String, Object> vars;
  public static JavascriptExecutor js;

  static Random random = new Random();
  static int randomNumber;
  static String emailArtista;
  static String emailArrendador;

  @Mock
  private ArrendadorRepository arrendadorRepository;

	@Mock
  private ArtistaRepository artistaRepository;

  @InjectMocks
  private ArrendadorService arrendadorService;

	@InjectMocks
  private ArtistaService artistaService;


  @BeforeClass
  public static void setUp() {
   

    

    //System.setProperty("webdriver.edge.driver", "..\\bart\\src\\test\\msedgedriver.exe");
    System.setProperty("webdriver.chrome.driver", "..\\bart\\src\\test\\chromedriver111.exe");
    driver = new ChromeDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();

    randomNumber = random.nextInt(100000000) + 1;
    emailArtista = "michaelJackson"+randomNumber+"@gmail.com";
    emailArrendador = "barPaco"+randomNumber+"@gmail.com";

    // Registro de un artista
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Registrarse como artista")).click();

    //  Rellena el formulario de artista
    driver.findElement(By.id("email")).sendKeys(emailArtista);
    driver.findElement(By.id("password")).sendKeys("123456");
    driver.findElement(By.id("urlImagen")).sendKeys("https://yt3.googleusercontent.com/DYNuiKlx93gnqckBObvCa_HSW8iR-XQNqND9OAVBgrTKEnxtVZaGQ_WYfH3DA6nmeCmhraRC=s900-c-k-c0x00ffffff-no-rj");
    driver.findElement(By.id("firstName")).sendKeys("Michael");
    driver.findElement(By.id("lastName")).sendKeys("Jackson");
    driver.findElement(By.id("dob")).sendKeys("2000-09-10");
    driver.findElement(By.id("nombre_artistico")).sendKeys("Michael Jackson");
    driver.findElement(By.id("categoria_artistica")).sendKeys("Pop");
    driver.findElement(By.cssSelector(".btn")).click();

    // Registro de un arrendador
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Registrarse como arrendador")).click();
    
    //  Rellena el formulario de arrendador
    driver.findElement(By.id("email")).sendKeys(emailArrendador);
    driver.findElement(By.id("password")).sendKeys("123456");
    driver.findElement(By.id("urlImagen")).sendKeys("https://media-cdn.tripadvisor.com/media/photo-s/15/7b/5b/0f/paco-pepe.jpg");
    driver.findElement(By.id("firstName")).sendKeys("Francisco");
    driver.findElement(By.id("lastName")).sendKeys("Pérez");
    driver.findElement(By.id("dob")).sendKeys("2000-04-20");
    driver.findElement(By.id("nombreLocal")).sendKeys("Bar Paco");
    driver.findElement(By.id("direccion")).sendKeys("Sevilla");
    driver.findElement(By.cssSelector(".btn")).click();
  }

  @AfterClass
  public static void tearDown() {
    driver.quit();
  }

  
  @Test
  public void loginTest() {

    // Login como artista
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Iniciar sesión")).click();
    driver.findElement(By.id("username")).sendKeys("michaelJackson"+randomNumber+"@gmail.com");
    driver.findElement(By.id("password")).sendKeys("123456");
    driver.findElement(By.id("password")).sendKeys(Keys.ENTER);

    // Comprobar el perfil de artista
    driver.findElement(By.id("navbarDropdown")).click();
    driver.findElement(By.linkText("Ver mi perfil")).click();
    String nombreArtistico = driver.findElement(By.xpath("//h1")).getText();
    assertEquals(nombreArtistico, "Michael Jackson");
    
    // Log out
    driver.findElement(By.id("navbarDropdown")).click();
    driver.findElement(By.linkText("Cerrar sesión")).click();
    driver.findElement(By.cssSelector(".btn")).click();

    // Login como arrendador
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Iniciar sesión")).click();
    driver.findElement(By.id("username")).sendKeys("barPaco"+randomNumber+"@gmail.com");
    driver.findElement(By.id("password")).sendKeys("123456");
    driver.findElement(By.id("password")).sendKeys(Keys.ENTER);

    // Comprobar el perfil de arrendador
    driver.findElement(By.id("navbarDropdown")).click();
    driver.findElement(By.linkText("Ver mi perfil")).click();
    String nombreLocal = driver.findElement(By.xpath("//h1")).getText();
    assertEquals(nombreLocal, "Bar Paco");

    // Log out
    driver.findElement(By.id("navbarDropdown")).click();
    driver.findElement(By.linkText("Cerrar sesión")).click();
    driver.findElement(By.cssSelector(".btn")).click();
  }
  
  @Test
  public void AddAnuncioArtistaTest() {

    //  Inicia sesión con el artista creado
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Iniciar sesión")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys(emailArtista);
    driver.findElement(By.id("password")).sendKeys("123456");
    driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
    
    //  Crea anuncio de artista
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Crea y vea anuncios de artistas")).click();
    driver.findElement(By.linkText("Añadir anuncio")).click();
    driver.findElement(By.id("pseudonimoArtista")).sendKeys("Michael Jackson");
    driver.findElement(By.id("ubicacion")).sendKeys("Sevilla");
    driver.findElement(By.id("precio")).sendKeys("4500");
    driver.findElement(By.id("descripcionArtista")).sendKeys("El rey del pop");
    driver.findElement(By.cssSelector(".form-check-inline:nth-child(2) > .form-check-label")).click();
    driver.findElement(By.id("requiereMicrofono")).click();
    driver.findElement(By.id("requiereInstrumentos")).click();
    driver.findElement(By.id("requiereAltavoces")).click();
    driver.findElement(By.id("ofreceActuacionPorEntradas")).click();
    driver.findElement(By.id("requiereOtrasEspecificaciones")).sendKeys("Ninguna");
    driver.findElement(By.cssSelector(".btn")).click();
    driver.findElement(By.cssSelector(".col-md-4:last-child .btn:nth-child(4)")).click();

    // Comprueba si se ha creado correctamente el anuncio de artista
    String nombre = driver.findElement(By.xpath("//h2[@class='mb-3']")).getText();
    assertEquals(nombre, "Michael Jackson");

    // Log out
    driver.findElement(By.id("navbarDropdown")).click();
    driver.findElement(By.linkText("Cerrar sesión")).click();
    driver.findElement(By.cssSelector(".btn")).click();
  }
  
  
  @Test
  public void AddAnuncioArrendadorTest() {

    //  Inicia sesión con el arrendador creado
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Iniciar sesión")).click();
    driver.findElement(By.id("username")).sendKeys(emailArrendador);
    driver.findElement(By.id("password")).sendKeys("123456");
    driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
    
    //  Crea anuncio de arrendador
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Crea y vea anuncios de locales")).click();
    driver.findElement(By.linkText("Añadir anuncio")).click();
    driver.findElement(By.id("nombreLocal")).sendKeys("Bar Paco");
    driver.findElement(By.id("ubicacion")).sendKeys("Sevilla");
    driver.findElement(By.id("precio")).sendKeys("1000");
    driver.findElement(By.id("descripcionArrendador")).sendKeys("Descripción");
    driver.findElement(By.cssSelector(".form-check-inline:nth-child(6) > .form-check-label")).click();
    driver.findElement(By.id("ofreceMicrofono")).sendKeys("Sí");
    driver.findElement(By.id("ofreceInstrumentos")).sendKeys("No");
    driver.findElement(By.id("ofreceIluminacion")).sendKeys("Sí");
    driver.findElement(By.id("ofreceAltavoces")).sendKeys("Sí");
    driver.findElement(By.id("ofreceMesaDeMezclas")).sendKeys("No");
    driver.findElement(By.id("ofrecePortatil")).sendKeys("No");
    driver.findElement(By.id("ofreceOtrasEspecificaciones")).sendKeys("Ninguna más");
    driver.findElement(By.id("ofreceActuacionPorEntradas")).click();
    driver.findElement(By.cssSelector(".btn")).click();
    driver.findElement(By.cssSelector(".col-md-4:last-child .btn:nth-child(4)")).click();

    // Comprueba si se ha creado correctamente el anuncio de arrendador
    String nombre = driver.findElement(By.xpath("//h2[@class='mb-3']")).getText();
    assertEquals(nombre, "Bar Paco");
    
    // Log out
    driver.findElement(By.id("navbarDropdown")).click();
    driver.findElement(By.linkText("Cerrar sesión")).click();
    driver.findElement(By.cssSelector(".btn")).click();
  }
  
   

  @Test
  public void AcceptAnuncioArtistaTest() {

    //  Inicia sesión con el arrendador creado
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Iniciar sesión")).click();
    driver.findElement(By.id("username")).sendKeys(emailArrendador);
    driver.findElement(By.id("password")).sendKeys("123456");
    driver.findElement(By.id("password")).sendKeys(Keys.ENTER);

    // Aceptar anuncio 
    driver.findElement(By.linkText("Anuncios de artistas")).click();
    driver.findElement(By.xpath("//div[2]/div/div[last()]/div/div/a[3]")).click();
    driver.findElement(By.linkText("Vea anuncios aceptados")).click();
    
    // Comprueba si se ha aceptado correctamente el anuncio de artista
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    String email = driver.findElement(By.xpath("//body/div[2]/div[last()]/div/h4")).getText();
    String precio = driver.findElement(By.xpath("//body/div[2]/div[last()]/h4")).getText();
    assertEquals(email, emailArtista);
    assertEquals(precio, "Precio: 4500.0€");

    // Log out
    driver.findElement(By.id("navbarDropdown")).click();
    driver.findElement(By.linkText("Cerrar sesión")).click();
    driver.findElement(By.cssSelector(".btn")).click();
  }

}
import com.sun.deploy.ref.Helpers;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.Select;
import org.testng.internal.Utils;

import java.io.File;
import java.io.IOException;

import static java.lang.System.setProperty;

public class CheapTicketsTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private CheapTicketsHomePage cheapPage;
    private CheapTicketsResultados resultadosPage;

    String destino = "Miami Beach";
    String hotel = "Faena Hotel Miami Beach";
    String fechaIda = "05/25/2019";
    String fechaVuelta = "05/26/2019";
    private Utils FileUtils;

    @BeforeClass
    public void testSetUp(){
        setProperty("webdriver.chrome.driver", "C:\\Users\\Agus\\chromedriver.exe");
        driver = new ChromeDriver();
        cheapPage = new CheapTicketsHomePage(driver);
        resultadosPage = new CheapTicketsResultados(driver);
        wait = new WebDriverWait(driver,60);
    }

    @Test
    public void ingresar() throws InterruptedException, IOException {
        driver.get("https://www.cheaptickets.com/");
        WebElement hoteles = cheapPage.getHoteles();
        hoteles.click();
        WebElement goingTo = cheapPage.getGoingTo();
        goingTo.clear();
        goingTo.sendKeys(destino);
        Select adutlos = new Select(cheapPage.getAdultos());

        Select ninos = new Select(cheapPage.getNinos());
        ninos.selectByVisibleText("1");
        Select edad = new Select(cheapPage.getEdad());
        edad.selectByVisibleText("7");
        WebElement calendarioIda = (WebElement) cheapPage.getCalendarioIda();
        calendarioIda.sendKeys(fechaIda);
        adutlos.selectByVisibleText("4");
        WebElement calendarioVuelta = (WebElement) cheapPage.getCalendarioVuelta();
        calendarioVuelta.sendKeys(fechaVuelta);
        WebElement buscar = cheapPage.getBuscar();
        buscar.click();
        Thread.sleep(22000);

        WebElement resultados = resultadosPage.getResultados();
        resultados.sendKeys(hotel);
        WebElement buscarResultados = resultadosPage.getBuscarResultados();
        buscarResultados.click();

        tomarCapturaPantalla(driver,"cantidad.png");
        Assert.assertTrue(CheapTicketsResultados.chekearCantidad());
        tomarCapturaPantalla(driver,"localizacion.png");
        Assert.assertTrue(CheapTicketsResultados.chekearUbicacion());
        tomarCapturaPantalla(driver,"faena.png");
        //Assert.assertTrue(CheapTicketsResultados.chekearFaena());
    }

    private void tomarCapturaPantalla (WebDriver driver, String nombre) throws IOException {
        File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(src,new File("C:\\Users\\Usuario\\Desktop\\Practicas Bootcamp"+nombre));
    }

    @AfterTest
    public void tearDown(){
        driver.quit();
        System.out.println("after class");}



}

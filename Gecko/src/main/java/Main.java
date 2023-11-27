import com.opencsv.CSVWriter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Clase principal que realiza diferentes operaciones relacionadas con elementos químicos.
 * Inicia el WebDriver, realiza operaciones pra extraer información de una web con la tabla periódica y genera archivos CSV y XML.
 * @author: mina-45b
 * @version: 1.0
 */
public class Main {
  /**
   * Método principal que inicia la ejecución del programa.
   * @param args Los argumentos de la línea de comandos (no se utilizan en este programa).
   */
  public static void main(String[] args) {
    System.out.println(System.getenv("PATH"));
    System.out.println(System.getenv("HOME"));

    System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver");
    FirefoxOptions options = new FirefoxOptions();

    // Configuración del WebDriver y navegación a la web de la tabla periódica
    options.setBinary("/home/brenda/Descargas/firefox-118.0.2/firefox/firefox");
    WebDriver driver = new FirefoxDriver(options);
    driver.get("https://ptable.com/#Propiedades");

    // Tiempos de espera
    driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS); //espera aparición de los elementos
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    WebDriverWait wait = new WebDriverWait(driver,5);

    List<ElementoQuimico> elementoQuimicos = elementoQuimicos(wait);
    String[] seriesElementos = series(wait);
    String[] estadosPosibles = estado(wait);

    //---Generación de archivos CSV y XML

    // Elementos químicos
    generarCSVElementoQuimico( elementoQuimicos);

    // Elementos por series
    generarCSVElementosSerie(elementoQuimicos, seriesElementos);

    // Elementos por estado
    generarCSVElementosEstado(elementoQuimicos, estadosPosibles);

    // Tabla periodica
    generarXMLTablaPeriodica(seriesElementos, estadosPosibles, elementoQuimicos);

    // Cierre del WebDriver
    driver.quit();

  }

  /**
   * Recopila información sobre elementos químicos en la tabla periódica.
   * @param wait El objeto WebDriverWait utilizado para esperar la presencia de los elementos de la página.
   * @return Una lista de objetos ElementoQuimico que contiene información sobre los elementos químicos.
   * @see WebDriverWait
   * @see ElementoQuimico
   */
  public static List<ElementoQuimico> elementoQuimicos(WebDriverWait wait) {
    List<ElementoQuimico> elementosQuimicos = new ArrayList<>();
    String nombre = "", simboloQuimico = "", numeroAtomico = "", numeroMasico = "";

    // Array para almacenar otras propiedades de los elementos
    String[] otrasPropiedades = new String[18];
    int contador = 0;

    // Localizar la tabla periódica de la página
    WebElement tabla = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("Ptable")));
    List<WebElement> elementos = tabla.findElements(By.tagName("li"));


    // Iterar sobre elementos de la tabla: número atómicos de los elementos, símbolo, nombre, peso atómico
    for (WebElement elemento : elementos) {
      try{
        // Obtener información básica de los elementos
        numeroAtomico = elemento.findElement(By.tagName("b")).getText();
        simboloQuimico = elemento.findElement(By.tagName("abbr")).getText();
        nombre = elemento.findElement(By.tagName("em")).getText();
        numeroMasico = elemento.findElement(By.tagName("data")).getText();

        // Hacer clic en el elemento para más propiedades
        elemento.click();
        try {
          // Localizar las propiedades del elemento
          WebElement propiedades = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("Property")));
          List<WebElement> propiedadesElemento = propiedades.findElements(By.tagName("li"));

          // Almacenar propiedades en un Array
          for (WebElement propiedad : propiedadesElemento)  {
            otrasPropiedades[contador] = propiedad.findElement(By.tagName("output")).getText();
            contador++;
          }

          // Creación de ElementoQuimico y añadir a la lista
          ElementoQuimico elementoQuimicoC = new ElementoQuimico(numeroAtomico, simboloQuimico, nombre, numeroMasico);
          elementoQuimicoC.setSeries(otrasPropiedades[0]);
          elementoQuimicoC.setEstado(otrasPropiedades[2]);
          elementoQuimicoC.setNivelEnergia(otrasPropiedades[4]);
          elementoQuimicoC.setElectroNegatividad(otrasPropiedades[5]);
          elementoQuimicoC.setPuntoFusion(otrasPropiedades[6]);
          elementoQuimicoC.setPuntoEbullicion(otrasPropiedades[7]);
          elementoQuimicoC.setAfinidadElectronica(otrasPropiedades[8]);
          elementoQuimicoC.setEnergiaIonizacion(otrasPropiedades[9]);
          elementoQuimicoC.setRadio(otrasPropiedades[10]);
          elementoQuimicoC.setDureza(otrasPropiedades[11]);
          elementoQuimicoC.setModulo(otrasPropiedades[12]);
          elementoQuimicoC.setDensidad(otrasPropiedades[13]);
          elementoQuimicoC.setConductividad(otrasPropiedades[14]);
          elementoQuimicoC.setCalor(otrasPropiedades[15]);
          elementoQuimicoC.setAbundancia(otrasPropiedades[16]);
          elementoQuimicoC.setDescubrimiento(otrasPropiedades[17]);

          elementosQuimicos.add(elementoQuimicoC);

          // Reiniciar el arreglo y contador
          Arrays.fill(otrasPropiedades, null);
          contador = 0;

        } catch (Exception e) {
          System.out.println("---------------------- Propiedad no encontrada");
        }

      } catch (Exception e) {
        System.out.println("------------------------------------------------- No encontrado: "+elemento.getText());
      }
    }
    return elementosQuimicos;
  }

  /**
   * Genera un archivo CSV que contiene información detallada sobre elementos químicos.
   * @param elementos Lista de objetos ElementoQuimico que se utilizará para generar el archivo CSV.
   * @see ElementoQuimico
   */
  public static void generarCSVElementoQuimico (List<ElementoQuimico> elementos) {
    // Archivo CSV
    File elementosQuimicos = new File("src/elementosQuimicos.csv");

    // Objeto para escribir en el archivo CSV
    CSVWriter csvWriter;

    // Cabecera del archivo CSV
    String[] cabecera = {"Numero Átomico", "Símbolo Químico", "Nombre", "Peso Átomico", "Serie", "Estado", "Nivel de Energía", "Electronegatividad", "Punto de Fusión",
            "Punto de Ebullición", "Afinidad Eléctrica", "Energía de Ionización", "Radio", "Dureza", "Módulo",
            "Densidad", "Conductividad", "Calor", "Abundancia", "Descubrimiento"};

    // Lista para almacenar los datos de elementos químicos
    List<String[]> elementosCompletos = new ArrayList<>();

    // Iterar sobre los elementos y agregar sus datos a la lista
    for (ElementoQuimico e : elementos) {
      elementosCompletos.add(new String[] {e.getNumeroAtomico(),e.getSimboloQuimico(),e.getNombre(),e.getPesoAtomico(),
     e.getSeries(),e.getEstado(),e.getNivelEnergia(),e.getElectroNegatividad(),e.getPuntoFusion(),
      e.getPuntoEbullicion(),e.getAfinidadElectronica(),e.getEnergiaIonizacion(),e.getRadio(),e.getDureza(),e.getModulo(),
      e.getDensidad(),e.getConductividad(),e.getCalor(),e.getAbundancia(),e.getDescubrimiento()});
    }

    try {
      // Inicializar el objeto CSVWriter y escribir el archvio CSV
      csvWriter = new CSVWriter(new FileWriter(elementosQuimicos));
      csvWriter.writeNext(cabecera);
      csvWriter.writeAll(elementosCompletos);
      csvWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Recupera los nombres de las series de los elementos desde la página web.
   * @param wait Objeto WebDriverWait utilizado para esperar la presencia de elementos en la página.
   * @return Array de cadena de texto que contiene los nombre de las series de los elementos químicos.
   * @see WebDriverWait
   * @see WebElement
   */
  public static String[] series (WebDriverWait wait) {
    // Array para almacenar los nombre de las series
    String[] nombreSerie = new String[9];
    int contador = 0;

    // Localizar la sección de la página
    WebElement series = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("Series")));

    //Obtener nombres de las series
    List<WebElement> grupos = series.findElements(By.tagName("dd"));

    //Grupos
    for (WebElement  grupo : grupos)  {
      try {
        nombreSerie[contador] = grupo.getText();
        contador++;
      } catch (Exception e) {
        System.out.println("--------------------- No encontrado: "+grupo.getText());
      }
    }

    // Obtener los nombres de las categorías de series
    List<WebElement> categorias = series.findElements(By.tagName("dt"));

    int cont = 0;
    //Iterar sobre las categorías de series y almacenar el nombre de la última
    for (WebElement categoria : categorias) {
      try {
        if (cont == 1 ){
          nombreSerie[8] = categoria.getText();
        }
        cont++;
      } catch (Exception e) {
        System.out.println("--------------------- No encontrado: "+categoria.getText());
      }
    }
    return nombreSerie;
  }

  /**
   * Genera un archivo CSV que organiza los elementos químicos según sus series.
   * @param elementos Lista de elementos químicos a ser organizados en el archivo CSV.
   * @param seriesElementos Nombre de las series de los elementos químicos que se utilizarán como encabezados de columna.
   * @see Serie
   * @see ElementoQuimico
   * @see CSVWriter
   * @see FileWriter
   */
  public static void generarCSVElementosSerie (List<ElementoQuimico> elementos, String[] seriesElementos) {
    // Archivo de salida para el CSV
    File elementosPorSerie = new File("src/elementosPorSerie.csv");

    // Objeto para escribir en el archivo CSV
    CSVWriter csvWriter;

    // Lista para almacenar objetos Serie con sus elementos químicos
    List<Serie> series = new ArrayList<>();

    // Crear objetos Serie con nombre y listas vacías de elementos
    for (String serieNombre : seriesElementos) {
        series.add(new Serie(serieNombre, new ArrayList<>()));
    }

    //Asignar cada elemento químico a su respectiva serie
    for (ElementoQuimico element : elementos) {
      for (Serie serie : series) {
        if (element.getSeries().equals(serie.getNombre())) {
          serie.getSerieElementos().add(element);
        }
      }
    }

    // Preparar listas de nombres de elementos para cada serie
    List<List<String>> columnas = new ArrayList<>();
    for (Serie serie : series) {
      columnas.add(serie.nombreElementos());
    }

    try {
      // Inicializar el objeto CSVWriteer con el archivo de salida
      csvWriter = new CSVWriter(new FileWriter(elementosPorSerie));
      csvWriter.writeNext(seriesElementos);

      // Determinar el número máximo de elementos en una columna
      int maxElementos = columnas.stream().mapToInt(List::size).max().orElse(0);

      // Rellenar las columnas con elementos o cadenas vacías si no hay suficientes elementos
      for (int i = 0; i < maxElementos; i++) {
        String[] linea = new String[columnas.size()];
        for (int j = 0; j < columnas.size(); j++) {
          if (i < columnas.get(j).size()) {
            linea[j] = columnas.get(j).get(i);
          } else {
            linea[j] = ""; // Cadena vacía si no hay suficientes elementos
          }
        }
        // Escribir la línea en el archivo CSV
        csvWriter.writeNext(linea);
      }
      // Cerrar el objeto CSVWriter para finalizar la escritura del archivo
      csvWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Obtiene los nombres de los estados de los elementos químicos desde la página web
   * @param wait Objeto WebDriverWait utilizado para esperar a que los elementos de la página se carguen.
   * @return Array de String que contiene los nombres de los estados de los elementos.
   * @see WebDriverWait
   * @see WebElement
   */
  public static String[] estado(WebDriverWait wait) {
    // Array para almacenar los nombres de los estados
    String[] nombreEstados = new String[4];
    int contador = 0;

    // Localizar la sección de estados en la página
    WebElement states = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("StateOfMatter")));
    List<WebElement> estados = states.findElements(By.tagName("div"));

    //Iterar para obtener el nombre de los estados
    for (WebElement estado : estados) {
      try {
        nombreEstados[contador] = estado.findElement(By.tagName("dd")).findElement(By.tagName("a")).getText();
        contador++;
      } catch (Exception e) {
        System.out.println("---------------------- No encontrado: "+estado.getText());
      }
    }
    return nombreEstados;
  }


  /**
   * Genera un archivo CSV que contiene los elementos químicos clasificados por su estado.
   * @param elementos Lista de objetos ElementoQuimico que se clasficacarán por estado.
   * @param estadosPosibles Array de Strings que representa los posibles estados de un elemento químico.
   * @see ElementoQuimico
   * @see Estado
   * @see CSVWriter
   * @see File
   * @see FileWriter
   */
  public static void generarCSVElementosEstado(List<ElementoQuimico> elementos, String[] estadosPosibles) {
    // Archivo salida CSV
    File elementosPorSerie = new File("src/elementosPorEstado.csv");

    // Objeto para escribir en el archivo CSV
    CSVWriter csvWriter;

    // Cabecara para el archivo CSV
    String[] cabecera = {"Estado", "Elementos"};

    // Lista de objetos Estado para cada estado posible
    List<Estado> estados = new ArrayList<>();

    // Crea objetos Estado y agregarlos a la lista
    for (String estado : estadosPosibles) {
      estados.add(new Estado(estado, new ArrayList<>()));
    }

    // Clasificar los elementos por estado
    for (ElementoQuimico element : elementos) {
      for (Estado estado : estados) {
        if (element.getEstado().equals(estado.getNombre())) {
          estado.getEstadoElementos().add(element);
        }
      }
    }

    // Lista que contendrá las líneas para el CSV
    List<String[]> elementosEstado = new ArrayList<>();

    for (Estado estado : estados) {
      elementosEstado.add(new String[] {estado.getNombre(), estado.getEstadoElementos().toString()});
    }

    try {
      // Crear y guardar el archivo CSV
      csvWriter = new CSVWriter(new FileWriter(elementosPorSerie));
      csvWriter.writeNext(cabecera);
      csvWriter.writeAll(elementosEstado);
      csvWriter.close();
    } catch (IOException e) {
    e.printStackTrace();
    }
  }

  /**
   * Genera un archivo XML que representa una tabla periódica con información detallada de cada elemento químico.
   * @param seriesElementos Array de Strings que representa las series de la tabla periódica.
   * @param estadosPosibles Array de Strings que representa los posibles estados de un elemento químico.
   * @param elementoQuimicos Lista de objetos ElementoQuimico que se incluirán en la tabla.
   * @see ElementoQuimico
   * @see DocumentBuilderFactory
   * @see DocumentBuilder
   * @see Document
   * @see Element
   * @see OutputKeys
   * @see javax.xml.transform.TransformerFactory
   * @see javax.xml.transform.Transformer
   * @see javax.xml.transform.dom.DOMSource
   * @see javax.xml.transform.stream.StreamResult
   * @see File
   */
  public static void generarXMLTablaPeriodica (String[] seriesElementos, String[] estadosPosibles, List<ElementoQuimico> elementoQuimicos) {
    try {
      // Crear un nuevo documento XML
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newDefaultInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document document = dBuilder.newDocument();

      //Raíz
      Element rootElement = document.createElement("tabla");
      document.appendChild(rootElement);

      // Agregar elementos para las series
      Element series = document.createElement("series");
      rootElement.appendChild(series);
      // Iteración sobre las series
      for (int i = 0; i < seriesElementos.length; i ++) {
      Element serie = document.createElement("serie");
      serie.appendChild(document.createTextNode(seriesElementos[i]));
      series.appendChild(serie);
      }

      // Agregar elementos para los estados
      Element estados = document.createElement("estados");
      rootElement.appendChild(estados);
      //iteración sobre los estados
      for (int i = 0; i < estadosPosibles.length; i ++) {
        Element estado = document.createElement("estado");
        estado.appendChild(document.createTextNode(estadosPosibles[i]));
        estados.appendChild(estado);
      }

      // Agregar elementos químicos
      Element elementos = document.createElement("elementos");
      rootElement.appendChild(elementos);

      //Iterración de elementos químicos
      for (ElementoQuimico elementoQ : elementoQuimicos) {
        Element elemento = document.createElement("elemento");
        elementos.appendChild(elemento);

        Element numeroElemento = document.createElement("numero");
        numeroElemento.appendChild(document.createTextNode(elementoQ.getNumeroAtomico()));
        elemento.appendChild(numeroElemento);

        Element nombreElemento = document.createElement("nombre");
        nombreElemento.appendChild(document.createTextNode(elementoQ.getNombre()));
        elemento.appendChild(nombreElemento);

        Element simboloElemento = document.createElement("simbolo");
        simboloElemento.appendChild(document.createTextNode(elementoQ.getSimboloQuimico()));
        elemento.appendChild(simboloElemento);

        Element pesoElemento = document.createElement("peso");
        pesoElemento.appendChild(document.createTextNode(elementoQ.getPesoAtomico()));
        elemento.appendChild(pesoElemento);


        Element serieElemento = document.createElement("serie");
        serieElemento.appendChild(document.createTextNode(elementoQ.getSeries()));
        elemento.appendChild(serieElemento);

        Element estadoElemento = document.createElement("estado");
        estadoElemento.appendChild(document.createTextNode(elementoQ.getEstado()));
        elemento.appendChild(estadoElemento);

        Element energiaElemento = document.createElement("energia");
        energiaElemento.appendChild(document.createTextNode(elementoQ.getNivelEnergia()));
        elemento.appendChild(energiaElemento);

        Element electronegatividadElemento = document.createElement("electronegatividad");
        electronegatividadElemento.appendChild(document.createTextNode(elementoQ.getElectroNegatividad()));
        elemento.appendChild(electronegatividadElemento);

        Element puntofusionElemento = document.createElement("fusion");
        puntofusionElemento.appendChild(document.createTextNode(elementoQ.getPuntoFusion()));
        elemento.appendChild(puntofusionElemento);

        Element puntoebullicionElemento = document.createElement("ebullicion");
        puntoebullicionElemento.appendChild(document.createTextNode(elementoQ.getPuntoEbullicion()));
        elemento.appendChild(puntoebullicionElemento);

        Element electroafinidadElemento = document.createElement("electroafinidad");
        electroafinidadElemento.appendChild(document.createTextNode(elementoQ.getElectroNegatividad()));
        elemento.appendChild(electroafinidadElemento);

        Element ionizacionElemento = document.createElement("ionizacion");
        ionizacionElemento.appendChild(document.createTextNode(elementoQ.getEnergiaIonizacion()));
        elemento.appendChild(ionizacionElemento);

        Element radioElemento = document.createElement("radio");
        radioElemento.appendChild(document.createTextNode(elementoQ.getRadio()));
        elemento.appendChild(radioElemento);

        Element durezaElemento = document.createElement("dureza");
        durezaElemento.appendChild(document.createTextNode(elementoQ.getDureza()));
        elemento.appendChild(durezaElemento);

        Element moduloElemento = document.createElement("modulo");
        moduloElemento.appendChild(document.createTextNode(elementoQ.getModulo()));
        elemento.appendChild(moduloElemento);

        Element densidadElemento = document.createElement("densidad");
        densidadElemento.appendChild(document.createTextNode(elementoQ.getDensidad()));
        elemento.appendChild(densidadElemento);

        Element calorElmento = document.createElement("calor");
        calorElmento.appendChild(document.createTextNode(elementoQ.getCalor()));
        elemento.appendChild(calorElmento);

        Element abundanciaElemento = document.createElement("abundancia");
        abundanciaElemento.appendChild(document.createTextNode(elementoQ.getAbundancia()));
        elemento.appendChild(abundanciaElemento);

        Element descubrimientoElemento = document.createElement("descubrimiento");
        descubrimientoElemento.appendChild(document.createTextNode(elementoQ.getDescubrimiento()));
        elemento.appendChild(descubrimientoElemento);
      }

      //Guardar XML
      File xmlFile = new File("src/elementosPorEstado.xml");
      javax.xml.transform.TransformerFactory transformerFactory = javax.xml.transform.TransformerFactory.newInstance();
      javax.xml.transform.Transformer transformer = transformerFactory.newTransformer();

      transformer.setOutputProperty(OutputKeys.INDENT,"yes");
      javax.xml.transform.dom.DOMSource source = new javax.xml.transform.dom.DOMSource(document);
      javax.xml.transform.stream.StreamResult result = new javax.xml.transform.stream.StreamResult(xmlFile);
      transformer.transform(source, result);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}

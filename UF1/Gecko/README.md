# Extractor de Información de la Tabla Periódica (Selenium - Java)

Este programa utiliza Selenium para extraer información detallada de los elementos de la tabla periódica en [ptable.com](https://ptable.com/#Propiedades), incluyendo propiedades, estados posibles y series a las que pertenecen. Luego, organiza y genera archivos CSV y XML con detalles sobre los elementos químicos.

# Requisitos

- Java 8 o superior 
- GeckoDriver para Firefox
- Maven

# Configuración
Asegúrate de tener GeckoDriver instalado y actualizado. Puedes configurar la ubicación de GeckoDriver en el código modificando la línea:

```java
System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver");
```

# Instalación
1. Clona el repostiorio o descarga el código fuente.
2. Ejecuta el programa Java `Main.java`.

# Uso
Ejecuta el programa principal para iniciar la extracción de datos. El programa abrirá un navegador, navegará a la página de la tabla periódica y extraerá la información especificada. Puedes encontrar los resultados en el archivo "scr".

# Archivos Generados

- `elementosQuimicos.csv`: Detalles detallados sobre cada elemento químico.
- `elementosPorSerie.csv`: Elementos químicos organizados por series.
- `elementosPorEstado.csv`: Elementos químicos clasificados por estado.
- `tablaPeriodica.xml`: Tabla periódica en formato XML con información detallada.

# Dependencias

- Selenium WebDriver
- OpenCSV

# Documentación
- [Main](src/main/javadoc/Main.html)
- [ElementoQuimico](src/main/javadoc/ElementoQuimico.html)
- [Serie](src/main/javadoc/Serie.html)
- [Estado](src/main/javadoc/Estado.html)

# Autor

- **mina-45b**



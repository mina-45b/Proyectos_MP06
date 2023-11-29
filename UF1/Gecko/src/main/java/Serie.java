import java.util.ArrayList;
import java.util.List;

/**
 * Clase Serie representa una serie de elementos químicos con un nombre asociado.
 * @author mina-45b
 * @version 1.0
 */
public class Serie {
   // Nombre de la serie
   String nombre;

   // Lista de elementoQuimicos que pertenece a la serie.
   List<ElementoQuimico> serieElementos;

   /**
    * Establece el nombre de la serie.
    * @return Nombre de la serie.
    */
   public String getNombre() {
      return nombre;
   }

   /**
    * Obtiene la lista de elementos químicos en la serie.
    * @return Lista de elementos químicos en la serie.
    */
   public List<ElementoQuimico> getSerieElementos() {
      return serieElementos;
   }

   /**
    * Constructor de la clase Serie.
    * @param nombre Nombre de la serie.
    * @param serieElementos Lista de elementos químicos en la serie.
    */
   public Serie(String nombre, List<ElementoQuimico> serieElementos) {
      this.nombre = nombre;
      this.serieElementos = serieElementos;
   }

   /**
    * Obtiene una lista de nombres de elementos químicos en la serie.
    * @return Lista de nombres de elementos químicos.
    */
   public List<String> nombreElementos () {
      List<String> nombres = new ArrayList<>();
      for (ElementoQuimico elemento : serieElementos) {
         nombres.add(elemento.getNombre());
      }
      return nombres;
   }
}

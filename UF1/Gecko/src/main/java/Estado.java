import java.util.ArrayList;
import java.util.List;

/**
 * Clase Estado representa un estado que puede tener los elementos químicos.
 * @author mina-45b
 * @version 1.0
 */
public class Estado {
    // Nombre de los estodos
    String nombre;

    // Lista de elementos de ese estado
    List<ElementoQuimico> estadoElementos;

    /**
     * Obtiene el nombre del estado.
     * @return Nombre del estado.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene la lista de elementos químicos con ese estado.
     * @return Lista de elementos químicos.
     */
    public List<ElementoQuimico> getEstadoElementos() {
        return estadoElementos;
    }

    /**
     * Constructor de la clase Estado.
     * @param name Nombre del estado.
     * @param estadoElementos Lista de elementos químicos con ese estado.
     */
    public Estado(String name, List<ElementoQuimico> estadoElementos) {
        this.nombre = name;
        this.estadoElementos = estadoElementos;
    }

}

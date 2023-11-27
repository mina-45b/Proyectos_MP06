/**
 * Clase ElementoQuimico que representa cada elementos químico de la tabla periódica y sus propiedades.
 * @author mina-45b
 * @version 1.0
 */
public class ElementoQuimico {
    // Propiedades de los elementos
    private String nombre;
    private String simboloQuimico;
    private String numeroAtomico;
    private String pesoAtomico;
    private String series;
    private String estado;
    private String nivelEnergia;
    private String electroNegatividad;
    private String puntoFusion;
    private String puntoEbullicion;
    private String afinidadElectronica;
    private String energiaIonizacion;
    private String radio;
    private String dureza;
    private String modulo;
    private String densidad;
    private String conductividad;
    private String calor;
    private String abundancia;
    private String descubrimiento;

    /**
     * Constructor de la clase ElementoQuimico
     * @param numeroAtomico Número del elemento químico.
     * @param simboloQuimico Símbolo del elemento químico.
     * @param nombre Nombre del elemento químico.
     * @param pesoAtomico Peso del elemento químico.
     */
    public ElementoQuimico(String numeroAtomico, String simboloQuimico, String nombre, String pesoAtomico) {
        this.nombre = nombre;
        this.simboloQuimico = simboloQuimico;
        this.numeroAtomico = numeroAtomico;
        this.pesoAtomico = pesoAtomico;
    }

    /**
     * Obtiene el nombre del elemento.
     * @return Nombre del elemento.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el símbolo químico del elemento.
     * @return símbolo del elemento.
     */
    public String getSimboloQuimico() {
        return simboloQuimico;
    }

    /**
     * Obtiene el númeor atómico del elemento.
     * @return Número del elemento.
     */
    public String getNumeroAtomico() {
        return numeroAtomico;
    }

    /**
     * Obtiene el peso atómico del elemento químico.
     * @return Peso del elemento químico.
     */
    public String getPesoAtomico() {
        return pesoAtomico;
    }

    /**
     * Obtiene la serie del elemento químico.
     * @return Serie del elemento.
     */
    public String getSeries() {
        return series;
    }

    /**
     * Establece la Serie del elemento químico.
     * @param series Serie del elemento químico.
     */
    public void setSeries(String series) {
        this.series = series;
    }

    /**
     * Obtiene el estado del elemento químico.
     * @return Estado del elemento.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado del elemento químico.
     * @param estado Estado del elemento químico.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Obtiene el nivel de energía del elemento químico.
     * @return Nivel de energía del elemento.
     */
    public String getNivelEnergia() {
        return nivelEnergia;
    }

    /**
     * Establece el nivel de energía del elemento químico.
     * @param nivelEnergia Nivel de energía del elemento.
     */

    public void setNivelEnergia(String nivelEnergia) {
        this.nivelEnergia = nivelEnergia;
    }

    /**
     * Obtiene la electronegatividad del elemento químico.
     * @return Electronegatividad del elemento químico
     */
    public String getElectroNegatividad() {
        return electroNegatividad;
    }

    /**
     * Establece la electronegatividad del elemento químico.
     * @param electroNegatividad La electronegatividad del elemento químico.
     */
    public void setElectroNegatividad(String electroNegatividad) {
        this.electroNegatividad = electroNegatividad;
    }

    /**
     * Obtiene el punto de fusión del elemento químico.
     * @return El punto de fusión del elemento químico.
     */
    public String getPuntoFusion() {
        return puntoFusion;
    }


    /**
     * Establece el punto de fusión del elemento químico.
     * @param puntoFusion El punto de fusión del elemento químico.
     */
    public void setPuntoFusion(String puntoFusion) {
        this.puntoFusion = puntoFusion;
    }

    /**
     * Obtiene el punto de ebullición del elemento químico.
     * @return El punto de ebullición del elemento químico.
     */
    public String getPuntoEbullicion() {
        return puntoEbullicion;
    }

    /**
     * Establece el punto de ebullición del elemento químico.
     * @param puntoEbullicion El punto de ebullición del elemento químico.
     */
    public void setPuntoEbullicion(String puntoEbullicion) {
        this.puntoEbullicion = puntoEbullicion;
    }

    /**
     * Obtiene la afinidad electrónica del elemento químico.
     * @return La afinidad electrónica del elemento químico.
     */
    public String getAfinidadElectronica() {
        return afinidadElectronica;
    }

    /**
     * Establece la afinidad electrónica del elemento químico.
     * @param afinidadElectronica La afinidad electrónica del elemento químico.
     */
    public void setAfinidadElectronica(String afinidadElectronica) {
        this.afinidadElectronica = afinidadElectronica;
    }

    /**
     * Obtiene la energía de ionización del elemento químico.
     * @return La energía de ionización del elemento químico.
     */
    public String getEnergiaIonizacion() {
        return energiaIonizacion;
    }

    /**
     * Establece la energía de ionización del elemento químico.
     * @param energiaIonizacion La energía de ionización del elemento químico.
     */
    public void setEnergiaIonizacion(String energiaIonizacion) {
        this.energiaIonizacion = energiaIonizacion;
    }


    /**
     * Obtiene el radio del elemento químico.
     * @return El radio del elemento químico.
     */
    public String getRadio() {
        return radio;
    }

    /**
     * Establece el radio del elemento químico.
     * @param radio El radio del elemento químico.
     */
    public void setRadio(String radio) {
        this.radio = radio;
    }

    /**
     * Obtiene la dureza del elemento químico.
     * @return La dureza del elemento químico.
     */
    public String getDureza() {
        return dureza;
    }

    /**
     * Establece la dureza del elemento químico.
     * @param dureza La dureza del elemento químico.
     */
    public void setDureza(String dureza) {
        this.dureza = dureza;
    }

    /**
     * Obtiene el módulo del elemento químico.
     * @return El módulo del elemento químico.
     */
    public String getModulo() {
        return modulo;
    }

    /**
     * Establece el módulo del elemento químico.
     * @param modulo El módulo del elemento químico.
     */
    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    /**
     * Obtiene la densidad del elemento químico.
     * @return La densidad del elemento químico.
     */
    public String getDensidad() {
        return densidad;
    }

    /**
     * Establece la densidad del elemento químico.
     * @param densidad La densidad del elemento químico.
     */
    public void setDensidad(String densidad) {
        this.densidad = densidad;
    }

    /**
     * Obtiene el calor del elemento químico.
     * @return El calor del elemento químico.
     */
    public String getCalor() {
        return calor;
    }

    /**
     * Establece el calor del elemento químico.
     * @param calor El calor del elemento químico.
     */
    public void setCalor(String calor) {
        this.calor = calor;
    }

    /**
     * Obtiene la abundancia del elemento químico.
     * @return La abundancia del elemento químico.
     */
    public String getAbundancia() {
        return abundancia;
    }


    /**
     * Establece la abundancia del elemento químico.
     * @param abundancia La abundancia del elemento químico.
     */
    public void setAbundancia(String abundancia) {
        this.abundancia = abundancia;
    }

    /**
     * Obtiene el descubrimiento del elemento químico.
     * @return El descubrimiento del elemento químico.
     */
    public String getDescubrimiento() {
        return descubrimiento;
    }


    /**
     * Establece el descubrimiento del elemento químico.
     * @param descubrimiento El descubrimiento del elemento químico.
     */
    public void setDescubrimiento(String descubrimiento) {
        this.descubrimiento = descubrimiento;
    }

    /**
     * Obtiene la conductividad del elemento químico.
     * @return La conductividad del elemento químico.
     */
    public String getConductividad() {
        return conductividad;
    }

    /**
     * Establece la conductividad del elemento químico.
     * @param conductividad La conductividad del elemento químico.
     */
    public void setConductividad(String conductividad) {
        this.conductividad = conductividad;
    }

    /**
     * Regresa un String que representa el nombre del elemento químico.
     * @return String representa elemento químico.
     */
    @Override
    public String toString() {
        return nombre + " ";
    }
}

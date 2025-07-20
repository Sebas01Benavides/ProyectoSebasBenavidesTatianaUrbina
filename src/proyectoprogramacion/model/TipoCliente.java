package proyectoprogramacion.model;

/**
 *
 * @author Tatiana Urbina y Sebastian Benavides 
 */
public enum TipoCliente { //Definimos la clase para que sea Enum
    ADULTO_MAYOR(0, "A"),
    EMBARAZADA(1,"B"),
    DISCAPACIDAD(2, "C"),
    MULTIASUNTO(3, "D"),
    PLATAFORMA_SERVICIOS(4, "E"),
    MUJER(5, "F"),
    HOMBRE(6, "G");
    //
    private final int prioridad;
    private final String sufijoTicket;
    // Constructor del enum, recibe prioridad y sufijo para inicializar campos
    TipoCliente(int prioridad, String sufijoTicket){
        this.prioridad=prioridad;//  guarda la prioridad asignada a cada tipo cliente
        this.sufijoTicket=sufijoTicket;// guarda el sufijo para el ticket de ese tipo cliente
    }
    
    //Getters
    public int getPrioridad(){
        return prioridad;
    }
    //
    public String getSufijoTicket(){
        return sufijoTicket;
    }
    // Metodo  que devuelve un tipo de cliente aleatorio de los definidos anteriormente
    public static TipoCliente getRandomTipoCliente(){
        TipoCliente[] tipos={ADULTO_MAYOR, EMBARAZADA, DISCAPACIDAD, MULTIASUNTO, PLATAFORMA_SERVICIOS, MUJER, HOMBRE};
        return tipos[(int) (Math.random()* tipos.length)];
    }
}

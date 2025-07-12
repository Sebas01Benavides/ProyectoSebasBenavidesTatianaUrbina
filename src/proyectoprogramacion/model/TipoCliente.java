/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
    //
    TipoCliente(int prioridad, String sufijoTicket){
        this.prioridad=prioridad;
        this.sufijoTicket=sufijoTicket;
    }
    
    //Getters
    public int getPrioridad(){
        return prioridad;
    }
    //
    public String getSufijoTicket(){
        return sufijoTicket;
    }
    //
    public static TipoCliente getRandomTipoCliente(){
        TipoCliente[] tipos={ADULTO_MAYOR, EMBARAZADA, DISCAPACIDAD, MULTIASUNTO, PLATAFORMA_SERVICIOS, MUJER, HOMBRE};
        return tipos[(int) (Math.random()* tipos.length)];
    }
}

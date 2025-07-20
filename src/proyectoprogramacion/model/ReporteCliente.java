package proyectoprogramacion.model;

/**
 *
 * @author Tatiana Urbina y Sebastian Benavides 
 */
public class ReporteCliente {
    private final String idTicket;
    private final String tipoCliente;
    private final int minutosEsperados;
    private final int toleranciaMinutos;
    private final int minutosEsperaAlRetirarse;
    
    //Metodo constructor reporte
    public ReporteCliente(String idTicket, String tipoCliente, int minutosEsperados, int toleranciaMinutos, int minutosEsperaAlRetirarse){
        this.idTicket= idTicket;
        this.tipoCliente=tipoCliente;
        this.toleranciaMinutos= toleranciaMinutos;
        this.minutosEsperados=minutosEsperados;
        this.minutosEsperaAlRetirarse= minutosEsperaAlRetirarse;
    }
    
    //Getters
    public String getIdTicket(){
        return idTicket;
    }
    //
    public String getTipoCliente(){
        return tipoCliente;
    }
    //
    public int getToleranciaMinutos(){
        return toleranciaMinutos;
    }
    //
    public int getMinutosEsperados(){
        return minutosEsperados;
    }
    //
    public int getMinutosEsperaAlRetirarse(){
        return minutosEsperaAlRetirarse;
    }
    @Override
    public String toString() {
        return "Ticket: " + idTicket +
               ", Tipo: " + tipoCliente +
               ", Esperó: " + minutosEsperaAlRetirarse + " min" +
               ", Tolerancia: " + toleranciaMinutos + " min";
    }
}
    

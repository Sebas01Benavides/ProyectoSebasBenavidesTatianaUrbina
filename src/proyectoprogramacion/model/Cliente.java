/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoprogramacion.model;
/**
 *Esta clase representa a un cliente del banco con nombre, prioridad, tiempo y su nivel de tolerancia.
 * @author Tatiana Urbina y Sebastian Benavides
 */
public class Cliente {
    //Atributos privados, para la protección de datos 
    private String nombre;
    private String prioridad;
    private int tiempoAtencion;
    private int tolerancia;
    
    //Constructor, para crear un nuevo cliente con los datos básicos necesarios para el sistema.
    //Se usa para registrar un cleinte en la simulación del banco.
    public Cliente(String nombre, String prioridad, int tiempoAtencion, int tolerancia) {
          this.nombre = nombre;
          this.prioridad = prioridad;
          this.tiempoAtencion = tiempoAtencion;
          this.tolerancia = tolerancia;
    } 

    //Getters
    public String getNombre() {
        return nombre;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public int getTiempoAtencion() {
        return tiempoAtencion;
    }

    public int getTolerancia() {
        return tolerancia;
    }

    //Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public void setTiempoAtencion(int tiempoAtencion) {
        this.tiempoAtencion = tiempoAtencion;
    }

    public void setTolerancia(int tolerancia) {
        this.tolerancia = tolerancia;
    }

    //Esta función define cómo se verá el cliente cuando lo mostraremos en pantalla.
    //Por ejemplo, al desplegar la fila o generar un reporte en una ventana emergente.
    //El formato está pensado para ser claro, corto y visulamente entendible.
    @Override
    public String toString() { //El objeto como texto legible
        return nombre + " | Prioridad: " + prioridad + " | Atencion: " + tiempoAtencion + " min" + " | Tolerancia: " + tolerancia + " min";
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoprogramacion.controller;

import proyectoprogramacion.model.Cliente;
import javax.swing.*;
import java.util.*;

/**
 *Clase que se encarga de gestionar la lógica de la simulación del banco TATISEBAS.
 * Se encarga de gestionar la fila de clientes,  asignación a cajas de atención,  gestión de tiempo,  tolerancia, y reporte por cajero.
 * 
 * Requisitos que cumple el banco:
 * Fila con un máximo de 25 clientes.
 * Tolerancia de espera entre 5 y 150 minutos
 * 5 cajas de atención generales + 1 caja exclusiva para la prioridad E
 * Distribución por letra de prioridad
 * Reportes de clientes atendidos y retirados
 * 
 * @author Tatiana Urbina y Sebastian Benavides.
 */
public class Banco {
    private static final int LIMITE_MAXIMO = 25; //Cantidad máxima permitida en la fila
    private static final Queue<Cliente> fila = new LinkedList<>(); //La fila dónde esperan los clientes
    private static final List<Cliente> atendidos = new ArrayList<>();//Clientes atendidos
    private static final List<Cliente> retirados = new ArrayList<>(); //Clientes que se fueron
    
    //Cajas de atención, las listas separadas para registrar cada una
    private static final List<Cliente> caja1 = new ArrayList<>();
    private static final List<Cliente> caja2 = new ArrayList<>();
    private static final List<Cliente> caja3 = new ArrayList<>();
    private static final List<Cliente> caja4 = new ArrayList<>();
    private static final List<Cliente> caja5 = new ArrayList<>();
    
    //Caja de Plataforma de Servicios, solo para la letra E
    private static final List<Cliente> PlataformaServicios = new ArrayList<>();
    
    //Nombres para generar clientes automáticamente
    private static final String[] nombresDisponibles = {
      "Tatiana", "Sebastián", "Camila", "Jorge", "Valeria",
      "María", "Carlos", "Ana", "Yeison", "Paola",
      "Paulo", "Laura", "Jose", "Lucía", "Esteban",
      "Emesis", "Antonio", "Rebeca", "Manuel", "Isabel",
      "Eduardo", "Sofía", "Julian", "Andres", "Martín", "Gabriela"
    };
     private static final Random rnd = new Random();//Generador de valores aleatorios
     
   /**
    * Crea los 25 clientes automáticamente (como en un banco real que las personas llegan por si solas) al iniciar el sistema.
    * Cada cliente tiene nombre ficticio, prioridad aleatoria, tiempo de atención y tolerancia.
    */
    public static void cargarClientesIniciales() {
        for (int i = 0; i <= LIMITE_MAXIMO; i++) {
            String nombre = nombresDisponibles[i % nombresDisponibles.length] + " " + (i + 1);
            String prioridad = asignarPrioridad();
            int tiempoAtencion = rnd.nextInt(111) + 10; //entre 10 y 120 minutos
            int tolerancia = rnd.nextInt(146) + 5; //entre 5 y 150 minutos
            
            Cliente nuevo = new Cliente (nombre, prioridad, tiempoAtencion, tolerancia);
            fila.offer(nuevo); //Se agrega al final de la fila
        }
    }
    
     /**
     * Agrega un nuevo cliente si aún hay espacio en la fila.
     * Evita exceder el límite de 25 clientes.
     */
   public static void agregarCliente(JFrame parent) {
       if (fila.size() >=  LIMITE_MAXIMO) {
           JOptionPane.showMessageDialog(parent,  "La fila está llena. No se puede atender al cliente número 26. ");
           return;
       }
       
        String nombre = nombresDisponibles[rnd.nextInt(nombresDisponibles.length)] + " " + (fila.size() + 1);
        String prioridad = asignarPrioridad();
        int tiempoAtencion = rnd.nextInt(111) + 10;
        int tolerancia = rnd.nextInt(146) + 5;

        Cliente nuevo = new Cliente(nombre, prioridad, tiempoAtencion, tolerancia);
        fila.offer(nuevo);
        JOptionPane.showMessageDialog(parent, "Cliente ingresado: " + nuevo.getNombre());
    }
   
    /**Atiende al siguiente cliente y decide si se va por esperar demasiado.
     * @param tiempoDeEspera Minutos simulados que esperó antes de recibir atención.
     */
   public static Cliente atenderClienteConCajas(int tiempoDeEspera, JFrame parent) {
       Cliente cliente  = fila.poll(); //Siguiente cliente en fila
         if (cliente == null) return null;
    
            //Si la espra supera la tolerancia de esperar del cliente; se retira sin ser atendido
            if (tiempoDeEspera > cliente.getTolerancia()) {
            retirados.add(cliente);
            JOptionPane.showMessageDialog(parent, "  " + cliente.getNombre() + " se retiró sin ser atendido.");
            return null;
        }

         //Si es prioridad E, se atiende en Plataforma
        if (cliente.getPrioridad().equals("E")) {
            PlataformaServicios.add(cliente);
            JOptionPane.showMessageDialog(parent, "Cliente enviado a Plataforma de Servicios(E): " + cliente.getNombre());
        } else {
            int cajero = rnd.nextInt(5) + 1;
            switch (cajero) {
                case 1 -> caja1.add(cliente);
                case 2 -> caja2.add(cliente);
                case 3 -> caja3.add(cliente);
                case 4 -> caja4.add(cliente);
                case 5 -> caja5.add(cliente);
            }
            JOptionPane.showMessageDialog(parent,
                "Cliente atendido: " + cliente.getNombre() +
                "\n Cajero: #" + cajero +
                "\n Prioridad: " + cliente.getPrioridad());
        }

        atendidos.add(cliente);
        return cliente;
    }
   
     // Devuelve una letra de prioridad aleatoria entre A–G.
    private static String asignarPrioridad() {
        String[] letras = {"A", "B", "C", "D", "E", "F", "G"};
        return letras[rnd.nextInt(letras.length)];
    }
    
    //Reportes y estadísticas por caja
    public static int getAtendidosPorCaja(int num) {
        return switch (num) {
            case 1 -> caja1.size();
            case 2 -> caja2.size();
            case 3 -> caja3.size();
            case 4 -> caja4.size();
            case 5 -> caja5.size();
            default -> 0;
        };
    }

    //Getters
    public static int getLIMITE_MAXIMO() {
        return LIMITE_MAXIMO;
    }

    public static Queue<Cliente> getFila() {
        return fila;
    }

    public static List<Cliente> getAtendidos() {
        return atendidos;
    }

    public static List<Cliente> getRetirados() {
        return retirados;
    }

    public static List<Cliente> getCaja1() {
        return caja1;
    }

    public static List<Cliente> getCaja2() {
        return caja2;
    }

    public static List<Cliente> getCaja3() {
        return caja3;
    }

    public static List<Cliente> getCaja4() {
        return caja4;
    }

    public static List<Cliente> getCaja5() {
        return caja5;
    }

    public static List<Cliente> getPlataformaServicios() {
        return PlataformaServicios;
    }

    public static String[] getNombresDisponibles() {
        return nombresDisponibles;
    }

    public static Random getRnd() {
        return rnd;
    }
    public static boolean hayClientesPendientes() { return !fila.isEmpty(); }
    public static int getTotalEnFila() { return fila.size(); }
}


    

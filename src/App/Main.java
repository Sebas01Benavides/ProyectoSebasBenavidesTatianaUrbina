package App;
import javax.swing.SwingUtilities;
import proyectoprogramacion.gui.VentanaPrincipal; 
import proyectoprogramacion.gui.SplashScreen; 
/**
 *
 * @author Tatiana Urbina y Sebastian Benavides
 */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            SplashScreen splash = new SplashScreen(4000); // Muestra por 4 segundos

            splash.mostrar(() -> {
                System.out.println("Splash screen cerrado. Iniciando VentanaPrincipal...");
                VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
                ventanaPrincipal.setVisible(true);
            });
        });
    }
}
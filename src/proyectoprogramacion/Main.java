package proyectoprogramacion;

import com.jtattoo.plaf.hifi.HiFiLookAndFeel;
import proyectoprogramacion.view.VentanaPrincipal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import proyectoprogramacion.view.SplashScreen;

/**
 *
 * @author Tatiana Urbina y Sebastian Benavides 
 */
public class Main {
    public static void main(String args[]){
          try {
            UIManager.setLookAndFeel(new HiFiLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // Crear y mostrar splash screen
        SplashScreen splash = new SplashScreen();
        splash.setVisible(true);

        // Esperar 2 segundos para que se vea el splash
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Cerrar splash
        splash.dispose();

        // Mostrar ventana principal
        java.awt.EventQueue.invokeLater(() -> {
            new VentanaPrincipal().setVisible(true);
        });
    }
}

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
        }

        // Crear y mostrar splash screen
        SplashScreen splash = new SplashScreen();
        splash.setVisible(true);
    }
}

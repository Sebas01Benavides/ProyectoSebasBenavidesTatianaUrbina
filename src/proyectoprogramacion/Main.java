package proyectoprogramacion.app;
import proyectoprogramacion.view.SplashScreen;
import com.jtattoo.plaf.hifi.HiFiLookAndFeel;
import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Tatiana Urbina y Sebastian Benavides 
 */
public class Main {
    public static void main(String args[]){
        try {
            UIManager.setLookAndFeel(new HiFiLookAndFeel());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        EventQueue.invokeLater(new Runnable(){
            public void run(){
                new SplashScreen().setVisible(true);
            }
        });
    }
}

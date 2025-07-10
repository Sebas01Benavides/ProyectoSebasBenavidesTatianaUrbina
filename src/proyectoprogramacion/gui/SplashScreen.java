package proyectoprogramacion.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
/**
 *
 * @author Tatiana Urbina y Sebastian Benavides
 */
public class SplashScreen extends JWindow {

    private int duracion; 

    public SplashScreen(int duracionMs) {
        this.duracion = duracionMs;
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);

        URL imageUrl = getClass().getResource("/Imagenes/SplashScreenBanco.png"); 
        ImageIcon imageIcon = null;
        JLabel imageLabel;
        imageIcon = new ImageIcon(imageUrl);
        imageLabel = new JLabel(imageIcon);
        panel.add(imageLabel, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack(); 
        setLocationRelativeTo(null); 
    }

    public void mostrar(Runnable accionAlTerminar) {
        SwingUtilities.invokeLater(() -> setVisible(true));
        new Thread(() -> {
            try {
                Thread.sleep(duracion);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
            SwingUtilities.invokeLater(() -> {
                dispose(); 
                if (accionAlTerminar != null) {
                    accionAlTerminar.run(); 
                }
            });
        }).start();
    }
}

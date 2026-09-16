package system;

import system.logic.Service;
import system.presentation.board.Controller;
import system.presentation.board.Model;
import system.presentation.board.View;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Application {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            View view = new View();
            Model model = new Model();
            new Controller(view, model);

            JFrame window = new JFrame("Sistema de Planilla");
            window.setContentPane(view.getPanel());
            window.setSize(1000, 650);
            window.setLocationRelativeTo(null);
            window.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

            window.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    try {
                        Service.instance().stop();
                    } finally {
                        window.dispose();
                        System.exit(0);
                    }
                }
            });

            // Respaldo adicional para cierres normales de la JVM.
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    Service.instance().stop();
                } catch (Exception ignored) {
                }
            }));

            window.setVisible(true);
        });
    }
}

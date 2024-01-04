import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ControlApollo11 {
    private JPanel rootPanel;

    private JLabel lblSegundos;
    private JLabel lblSegundosSal;
    private JButton btnInicio;
    private JButton btnCancelar;
    private JProgressBar barraProgreso;
    private JFormattedTextField txtSegundos;

    private JLabel tituloPral;
    private JLabel lblimage;

    private Task task;
    private String txtMensaje = "FIN Proceso";
    private boolean hiloIniciado = false;


    public ControlApollo11() {

        btnInicio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!hiloIniciado) {
                    try {
                        int segundos = Integer.parseInt(txtSegundos.getText());
                        validarSegundos(segundos);
                        barraProgreso.setMaximum(segundos);
                        task = new ControlApollo11.Task(segundos);
                        task.start();
                        txtMensaje = "FIN Proceso";
                        hiloIniciado = true;
                    } catch (NumberFormatException ex) {
                        mostrarError("Debes introducir un número entero");
                        inicializarObjetos();
                    } catch (IllegalArgumentException ex) {
                        mostrarError(ex.getMessage());
                        inicializarObjetos();
                    }
                } else {
                    mostrarError("Ya hay un hilo en ejecución. Debes esperar a que termine.");
                }
            }
        });
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (hiloIniciado) {
                    txtMensaje = "Cancelado por Usuario";
                    inicializarObjetos();
                    task.detener();
                    hiloIniciado = false;
                } else {
                    mostrarError("El Hilo no ha sido iniciado");
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ControlApollo11");
        frame.setContentPane(new ControlApollo11().rootPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        Image image = null;
        try {
            URL url = new URL("https://spacecenter.org/wp-content/uploads/2022/09/Apollo-Mission-Control-15-inch.png");
            image = ImageIO.read(url);
        }
        catch (IOException e) {
        }
    }

    private void validarSegundos(int segundos) {
        if (segundos <= 0) {
            throw new IllegalArgumentException("El número de segundos debe ser mayor que cero.");
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje);
    }

    private void inicializarObjetos() {
        barraProgreso.setValue(0);
        txtSegundos.setText("");
        lblSegundosSal.setText("");
    }

    private class Task extends Thread {
        private int numSegundos;

        public Task(int numSegundos) {
            this.numSegundos = numSegundos;
        }

        @Override
        public void run() {
            for (var ref = new Object() {
                int i = 1;
            }; ref.i <= numSegundos; ref.i++) {
                if (task != null && task.isInterrupted()) {
                    break;
                }
                SwingUtilities.invokeLater(() -> {
                    barraProgreso.setValue(ref.i);
                    lblSegundosSal.setText(Integer.toString(numSegundos - ref.i));
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(null, txtMensaje);
                inicializarObjetos();
                hiloIniciado = false;
            });
        }

        public void detener() {
            interrupt();
        }
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ControlApollo11 {

    // Declaración de componentes de la interfaz gráfica
    private JPanel rootPanel;

    private JLabel lblSegundos;
    private JLabel lblSegundosSal;
    private JButton btnInicio;
    private JButton btnCancelar;
    private JProgressBar barraProgreso;
    private JFormattedTextField txtSegundos;

    private JLabel tituloPral;
    private JLabel lblimage;

    // Declaración de variables relacionadas con la lógica del hilo
    private Task task;
    private String txtMensaje = "FIN Proceso";
    private boolean hiloIniciado = false;


    public ControlApollo11() {

// Configuración de acciones para los botones de Iniciar y Detener Cuenta Atras.
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

        // Creación de la ventana principal
        JFrame frame = new JFrame("ControlApollo11");

        // Creación del panel principal que contendrá todos los componentes
        frame.setContentPane(new ControlApollo11().rootPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    private void validarSegundos(int segundos) {
        if (segundos <= 0) {
            throw new IllegalArgumentException("El número de segundos debe ser mayor que cero.");
        }
    }

    // Método para mostrar mensajes de error
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje);
    }

    // Método para reiniciar los componentes al estado inicial
    private void inicializarObjetos() {
        barraProgreso.setValue(0);
        txtSegundos.setText("");
        lblSegundosSal.setText("");
    }

    // Clase interna que representa el hilo de cuenta atrás
    private class Task extends Thread {
        private int numSegundos;

        // Método para validar que los segundos sean mayores que cero
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
                // Mostrar mensaje al finalizar el hilo
                JOptionPane.showMessageDialog(null, txtMensaje);
                inicializarObjetos();
                hiloIniciado = false;
            });
        }

        // Método para detener el hilo
        public void detener() {
            interrupt();
        }
    }
}

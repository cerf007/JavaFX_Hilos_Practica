package ni.edu.uam.practicahilos.javafx_hilos_practica;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class ProcesadorController {

    @FXML private Label lblEstadoClientes;
    @FXML private Label lblPorcentajeClientes;
    @FXML private ProgressBar barraClientes;
    @FXML private Button btnIniciarClientes;
    @FXML private Button btnDetenerClientes;

    @FXML private Label lblEstadoProductos;
    @FXML private Label lblPorcentajeProductos;
    @FXML private ProgressBar barraProductos;
    @FXML private Button btnIniciarProductos;
    @FXML private Button btnDetenerProductos;

    @FXML private Button btnReiniciar;

    private static final int  TOTAL_CLIENTES  = 100;
    private static final int  TOTAL_PRODUCTOS = 50;
    private static final long MS_CLIENTES     = 100;
    private static final long MS_PRODUCTOS    = 150;

    private Thread hiloClientes;
    private Thread hiloProductos;

    private boolean clientesActivo  = false;
    private boolean productosActivo = false;

    private volatile int progresoClientes  = 0;
    private volatile int progresoProductos = 0;

    @FXML
    private void initialize() {
        btnReiniciar.setDisable(true);
    }

    @FXML
    private void iniciarClientes() {

        if (clientesActivo) return;
        if (progresoClientes >= TOTAL_CLIENTES) return;

        clientesActivo = true;

        final int inicio = progresoClientes + 1;

        lblEstadoClientes.setText(
                progresoClientes == 0
                        ? "Procesando clientes..."
                        : "Continuando clientes desde " + progresoClientes + "%");

        btnIniciarClientes.setDisable(true);
        btnDetenerClientes.setDisable(false);
        actualizarBotonReiniciar();

        Runnable tarea = () -> {
            try {
                for (int i = inicio; i <= TOTAL_CLIENTES; i++) {

                    if (Thread.currentThread().isInterrupted()) {
                        throw new InterruptedException();
                    }

                    Thread.sleep(MS_CLIENTES);

                    progresoClientes = i;

                    final int avance = i;
                    Platform.runLater(() -> {
                        barraClientes.setProgress(avance / (double) TOTAL_CLIENTES);
                        lblPorcentajeClientes.setText(avance + "%");
                    });
                }

                Platform.runLater(() -> {
                    lblEstadoClientes.setText("Clientes procesados");
                    clientesActivo = false;
                    finalizarClientes();
                });

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                Platform.runLater(() -> {
                    lblEstadoClientes.setText("Proceso de clientes detenido");
                    clientesActivo = false;
                    finalizarClientes();
                });
            }
        };

        hiloClientes = new Thread(tarea, "Hilo-Clientes");
        hiloClientes.setDaemon(true);
        hiloClientes.start();
    }

    @FXML
    private void detenerClientes() {
        if (hiloClientes != null && hiloClientes.isAlive()) {
            btnDetenerClientes.setDisable(true);
            lblEstadoClientes.setText("Solicitando detención...");
            hiloClientes.interrupt();
        }
    }

    private void finalizarClientes() {
        btnDetenerClientes.setDisable(true);

        if (progresoClientes >= TOTAL_CLIENTES) {
            btnIniciarClientes.setText("Iniciar clientes");
            btnIniciarClientes.setDisable(true);
        } else if (progresoClientes > 0) {
            btnIniciarClientes.setText("Continuar clientes");
            btnIniciarClientes.setDisable(false);
        } else {
            btnIniciarClientes.setText("Iniciar clientes");
            btnIniciarClientes.setDisable(false);
        }

        actualizarBotonReiniciar();
    }


    @FXML
    private void iniciarProductos() {

        if (productosActivo) return;
        if (progresoProductos >= TOTAL_PRODUCTOS) return;

        productosActivo = true;

        final int inicio = progresoProductos + 1;
        final int porcentajeActual =
                (int) Math.round(progresoProductos * 100.0 / TOTAL_PRODUCTOS);

        lblEstadoProductos.setText(
                progresoProductos == 0
                        ? "Procesando productos..."
                        : "Continuando productos desde " + porcentajeActual + "%");

        btnIniciarProductos.setDisable(true);
        btnDetenerProductos.setDisable(false);
        actualizarBotonReiniciar();

        Runnable tarea = () -> {
            try {
                for (int i = inicio; i <= TOTAL_PRODUCTOS; i++) {

                    if (Thread.currentThread().isInterrupted()) {
                        throw new InterruptedException();
                    }

                    Thread.sleep(MS_PRODUCTOS);

                    progresoProductos = i;

                    final int avance = i;
                    final int porcentaje =
                            (int) Math.round(avance * 100.0 / TOTAL_PRODUCTOS);

                    Platform.runLater(() -> {
                        barraProductos.setProgress(avance / (double) TOTAL_PRODUCTOS);
                        lblPorcentajeProductos.setText(porcentaje + "%");
                    });
                }

                Platform.runLater(() -> {
                    lblEstadoProductos.setText("Productos procesados");
                    productosActivo = false;
                    finalizarProductos();
                });

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                Platform.runLater(() -> {
                    lblEstadoProductos.setText("Proceso de productos detenido");
                    productosActivo = false;
                    finalizarProductos();
                });
            }
        };

        hiloProductos = new Thread(tarea, "Hilo-Productos");
        hiloProductos.setDaemon(true);
        hiloProductos.start();
    }

    @FXML
    private void detenerProductos() {
        if (hiloProductos != null && hiloProductos.isAlive()) {
            btnDetenerProductos.setDisable(true);
            lblEstadoProductos.setText("Solicitando detención...");
            hiloProductos.interrupt();
        }
    }

    private void finalizarProductos() {
        btnDetenerProductos.setDisable(true);

        if (progresoProductos >= TOTAL_PRODUCTOS) {
            btnIniciarProductos.setText("Iniciar productos");
            btnIniciarProductos.setDisable(true);
        } else if (progresoProductos > 0) {
            btnIniciarProductos.setText("Continuar productos");
            btnIniciarProductos.setDisable(false);
        } else {
            btnIniciarProductos.setText("Iniciar productos");
            btnIniciarProductos.setDisable(false);
        }

        actualizarBotonReiniciar();
    }


    @FXML
    private void reiniciarProceso() {

        if (clientesActivo || productosActivo) return;

        // Clientes
        progresoClientes = 0;
        barraClientes.setProgress(0);
        lblPorcentajeClientes.setText("0%");
        lblEstadoClientes.setText("Esperando inicio");
        btnIniciarClientes.setText("Iniciar clientes");
        btnIniciarClientes.setDisable(false);
        btnDetenerClientes.setDisable(true);

        progresoProductos = 0;
        barraProductos.setProgress(0);
        lblPorcentajeProductos.setText("0%");
        lblEstadoProductos.setText("Esperando inicio");
        btnIniciarProductos.setText("Iniciar productos");
        btnIniciarProductos.setDisable(false);
        btnDetenerProductos.setDisable(true);

        actualizarBotonReiniciar();
    }

    private void actualizarBotonReiniciar() {
        boolean hayProgreso   = progresoClientes > 0 || progresoProductos > 0;
        boolean algunoActivo  = clientesActivo || productosActivo;
        btnReiniciar.setDisable(!hayProgreso || algunoActivo);
    }

    public void detenerAlCerrar() {
        if (hiloClientes != null && hiloClientes.isAlive()) {
            hiloClientes.interrupt();
        }
        if (hiloProductos != null && hiloProductos.isAlive()) {
            hiloProductos.interrupt();
        }
    }
}
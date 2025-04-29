package atomicmind.views;

import atomicmind.database.ConexionBD;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Clase para gestionar los hábitos digitales sin selector de fecha.
 */
public class HabitosDigitalesView {
    private Stage stage;
    private String correoUsuario;
    private int idUsuario;
    private BarChart<String, Number> barChart;
    private Label lblAlerta;
    private TextField txtTiempo;
    private final Map<String, Integer> tiempoUsoApps = new HashMap<>();
    private final Map<String, String> coloresBarras = Map.of(
            "Instagram", "#2196F3",  // Azul
            "Facebook", "#FF9800",  // Naranja
            "Twitter", "#9C27B0",   // Morado
            "TikTok", "#FFEB3B",    // Amarillo
            "YouTube", "#4CAF50"    // Verde
    );

    public HabitosDigitalesView(Stage stage, String correoUsuario) {
        this.stage = stage;
        this.correoUsuario = correoUsuario;
        this.idUsuario = obtenerIdUsuario();
        this.barChart = crearGrafico();  // ✅ Crear el gráfico antes de cargar datos
        cargarDatosDesdeBD();  // ✅ Cargamos datos desde la BD
        actualizarGrafico();   // ✅ Ahora se actualiza el gráfico automáticamente
        mostrarPantallaPrincipal();
    }



    private int obtenerIdUsuario() {
        int id = -1;
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT id_usuario FROM usuarios WHERE correo = ?")) {
            ps.setString(1, correoUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id_usuario");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    private void cargarDatosDesdeBD() {
        tiempoUsoApps.clear();
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT app_nombre, tiempo_uso FROM habitosdigitales WHERE id_usuario = ?")) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tiempoUsoApps.put(rs.getString("app_nombre"), rs.getInt("tiempo_uso"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Crea un botón con estilo futurista y efectos al pasar el ratón.
     */
    private Button crearBotonFuturista(String texto) {
        Button boton = new Button(texto);
        boton.setStyle("-fx-background-color: rgba(255, 255, 255, 0.7); -fx-text-fill: black; "
                + "-fx-font-size: 14px; -fx-font-weight: bold; -fx-border-color: black; -fx-border-width: 1px; "
                + "-fx-border-radius: 5px; -fx-padding: 8px;");

        // Efecto al pasar el ratón (hover)
        boton.setOnMouseEntered(e -> boton.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.9); -fx-text-fill: black; "
                        + "-fx-font-size: 14px; -fx-font-weight: bold; -fx-border-color: black; -fx-border-width: 1px; "
                        + "-fx-border-radius: 5px; -fx-padding: 8px;"));

        boton.setOnMouseExited(e -> boton.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.7); -fx-text-fill: black; "
                        + "-fx-font-size: 14px; -fx-font-weight: bold; -fx-border-color: black; -fx-border-width: 1px; "
                        + "-fx-border-radius: 5px; -fx-padding: 8px;"));

        return boton;
    }
    
    



    void mostrarPantallaPrincipal() {
        stage.setTitle("Hábitos Digitales");
        
        // 📌 Imagen de fondo
        ImageView fondoImagen = new ImageView(new Image(getClass().getResourceAsStream("/atomicmind/views/fondohabitosdig.jpg")));
        fondoImagen.setPreserveRatio(false);
        fondoImagen.fitWidthProperty().bind(stage.widthProperty());
        fondoImagen.fitHeightProperty().bind(stage.heightProperty());

        Label lblTitulo = new Label("Registro de Uso de Tiempos en Aplicaciones");
        ComboBox<String> comboApps = new ComboBox<>();
        comboApps.getItems().addAll("Instagram", "Facebook", "Twitter", "TikTok", "YouTube");
        comboApps.setValue("Instagram");

        txtTiempo = new TextField("minutos");
        txtTiempo.setPromptText("Minutos");

        Button btnAumentar = crearBotonFuturista("🔼 +5");
        btnAumentar.setOnAction(e -> modificarTiempo(5));

        Button btnReducir = crearBotonFuturista("🔽 -5");
        btnReducir.setOnAction(e -> modificarTiempo(-5));

        Button btnGuardar = crearBotonFuturista("Guardar Tiempo");
        btnGuardar.setOnAction(e -> guardarTiempo(comboApps.getValue()));

        Button btnRestablecer = crearBotonFuturista("Restablecer Datos");
        btnRestablecer.setOnAction(e -> restablecerDatos());

        Button btnVolver = new Button("🔙 Volver al Menú Principal");
        btnVolver.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; "
                + "-fx-font-size: 14px; -fx-font-weight: bold; "
                + "-fx-border-color: #1976D2; -fx-border-width: 2px; "
                + "-fx-border-radius: 8px; -fx-padding: 10px;");

        btnVolver.setOnMouseEntered(e -> btnVolver.setStyle(
                "-fx-background-color: #64B5F6; -fx-text-fill: white; "
                        + "-fx-font-size: 14px; -fx-font-weight: bold; "
                        + "-fx-border-color: #1976D2; -fx-border-width: 2px; "
                        + "-fx-border-radius: 8px; -fx-padding: 10px;"));

        btnVolver.setOnMouseExited(e -> btnVolver.setStyle(
                "-fx-background-color: #2196F3; -fx-text-fill: white; "
                        + "-fx-font-size: 14px; -fx-font-weight: bold; "
                        + "-fx-border-color: #1976D2; -fx-border-width: 2px; "
                        + "-fx-border-radius: 8px; -fx-padding: 10px;"));

        btnVolver.setOnAction(e -> new PrincipalView(stage, correoUsuario).mostrarPantalla());



        lblAlerta = new Label();
        lblAlerta.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 14px;");

        HBox boxBotones = new HBox(10, btnReducir, txtTiempo, btnAumentar);
        boxBotones.setAlignment(Pos.CENTER);

        VBox layout = new VBox(15);
        layout.getChildren().addAll(lblTitulo, comboApps, boxBotones, btnGuardar, lblAlerta, barChart, btnRestablecer, btnVolver);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10));

        // 📌 Usamos StackPane para poner la imagen detrás del contenido
        StackPane root = new StackPane();
        root.getChildren().addAll(fondoImagen, layout);

        // Crear la escena con el nuevo root
        Scene scene = new Scene(root, 600, 600);
        stage.setScene(scene);
    }


    private void modificarTiempo(int cambio) {
        try {
            int tiempoActual = Integer.parseInt(txtTiempo.getText());
            int nuevoTiempo = Math.max(0, tiempoActual + cambio);
            txtTiempo.setText(String.valueOf(nuevoTiempo));
        } catch (NumberFormatException e) {
            txtTiempo.setText("0");
        }
    }

    private void guardarTiempo(String app) {
        try {
            int tiempo = Integer.parseInt(txtTiempo.getText());
            tiempoUsoApps.put(app, tiempo);

            try (Connection con = ConexionBD.getConnection();
                 PreparedStatement ps = con.prepareStatement("INSERT OR REPLACE INTO habitosdigitales (id_usuario, app_nombre, tiempo_uso) VALUES (?, ?, ?)")) {
                ps.setInt(1, idUsuario);
                ps.setString(2, app);
                ps.setInt(3, tiempo);
                ps.executeUpdate();
            }
            mostrarAlerta(app, tiempo);
            actualizarGrafico();
        } catch (NumberFormatException e) {
            lblAlerta.setText("Introduce un número válido.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String app, int tiempo) {
        if (tiempo > 30) {
            lblAlerta.setText("⚠️ ¡Excesivo uso de " + app + "! Más de 30 min.");
        } else if (tiempo > 25) {
            lblAlerta.setText("⚠️ Reduzca su tiempo en " + app + ". Más de 25 min.");
        } else if (tiempo > 15) {
            lblAlerta.setText("⚠️ Consejo: Intente usar " + app + " menos de 15 min.");
        } else {
            lblAlerta.setText("");
        }
    }

    private void actualizarGrafico() {
        if (barChart == null) return;

        barChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        for (Map.Entry<String, Integer> entry : tiempoUsoApps.entrySet()) {
            XYChart.Data<String, Number> data = new XYChart.Data<>(entry.getKey(), entry.getValue());
            series.getData().add(data);
        }

        barChart.getData().add(series);
        barChart.applyCss();
        barChart.layout();

        for (XYChart.Data<String, Number> data : series.getData()) {
            String color = coloresBarras.getOrDefault(data.getXValue(), "#9E9E9E");
            if (data.getNode() != null) {
                data.getNode().setStyle("-fx-bar-fill: " + color + ";");
            }
        }
    }

    private BarChart<String, Number> crearGrafico() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
        chart.setTitle("Tiempo de Uso por Aplicación");
        chart.setCategoryGap(5);
        chart.setBarGap(1);

        // 📌 Estilos para los ejes del gráfico (Texto en blanco)
        xAxis.setStyle("-fx-tick-label-fill: white; -fx-font-size: 14px;");
        yAxis.setStyle("-fx-tick-label-fill: white; -fx-font-size: 14px;");
        
        // 📌 Ajustar el fondo celeste solo al tamaño del texto
        chart.applyCss();
        chart.layout();
        chart.lookup(".chart-title").setStyle("-fx-text-fill: black; -fx-font-size: 16px; -fx-font-weight: bold; "
                + "-fx-background-color: #87CEEB; -fx-padding: 2px 5px; -fx-border-radius: 3px; -fx-alignment: center; -fx-display: inline;");
        
        return chart;
    }



    private void restablecerDatos() {
        tiempoUsoApps.clear();
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement("DELETE FROM habitosdigitales WHERE id_usuario = ?")) {
            ps.setInt(1, idUsuario);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        actualizarGrafico();
        lblAlerta.setText("Datos restablecidos.");
    }
}

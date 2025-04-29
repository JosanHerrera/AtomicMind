package atomicmind.views;

import atomicmind.database.ConexionBD;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
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

public class RetosView {
    private Stage stage;
    private String correoUsuario;
    private int idUsuario;
    private final Map<String, List<String>> retosDificultad = new HashMap<>();
    private final Set<String> retosCompletados = new HashSet<>();
    private PieChart pieChart;
    private Label lblMensajeBienvenida;

    public RetosView(Stage stage, String correoUsuario) {
        this.stage = stage;
        this.correoUsuario = correoUsuario;
        this.idUsuario = obtenerIdUsuario();
        inicializarRetos();
        cargarRetosCompletados();
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

    private void inicializarRetos() {
        retosDificultad.put("Fácil", Arrays.asList(
                "Leer 25 páginas de un libro", "Hacer 15 sentadillas", "Beber 2 litros de agua", "Caminar 30 minutos", "Hacer 10 flexiones"
        ));

        retosDificultad.put("Medio", Arrays.asList(
                "Hacer 50 sentadillas", "Correr 2 km", "Hacer una comida vegetariana", "Hacer 30 flexiones", "Levantarse a las 6 am"
        ));

        retosDificultad.put("Difícil", Arrays.asList(
                "Correr 5 km", "Hacer 100 sentadillas", "Hacer 50 flexiones", "No usar el móvil en todo el día", "Leer un libro completo"
        ));
    }

    private void cargarRetosCompletados() {
        retosCompletados.clear();
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT descripcion FROM retos WHERE id_usuario = ?")) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                retosCompletados.add(rs.getString("descripcion"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void mostrarPantallaPrincipal() {
        stage.setTitle("Retos");

        // 📌 Imagen de fondo adaptable
        ImageView fondoImagen = new ImageView(new Image(getClass().getResourceAsStream("/atomicmind/views/fondoreto.jpg")));
        fondoImagen.setPreserveRatio(false);
        fondoImagen.fitWidthProperty().bind(stage.widthProperty());
        fondoImagen.fitHeightProperty().bind(stage.heightProperty());
        fondoImagen.setOpacity(1);

        Label lblTitulo = new Label("Selecciona una dificultad");
        lblTitulo.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-font-weight: bold;");

        // 🔹 Botones futuristas para seleccionar dificultad
        Button btnFacil = crearBotonFuturista("Fácil", true);
        Button btnMedio = crearBotonFuturista("Medio", true);
        Button btnDificil = crearBotonFuturista("Difícil", true);

        btnFacil.setOnAction(e -> {
            mostrarRetos("Fácil");
            stage.show();
        });
        btnMedio.setOnAction(e -> {
            mostrarRetos("Medio");
            stage.show();
        });
        btnDificil.setOnAction(e -> {
            mostrarRetos("Difícil");
            stage.show();
        });


        // 🔹 Botones de dificultad en una fila
        HBox contenedorDificultades = new HBox(15, btnFacil, btnMedio, btnDificil);
        contenedorDificultades.setAlignment(Pos.CENTER);

        pieChart = crearGrafico();

        lblMensajeBienvenida = new Label("Aún no has completado ningún reto.\n¡Empieza ahora!");
        lblMensajeBienvenida.setStyle("-fx-font-size: 16px; -fx-text-fill: gray; -fx-font-weight: bold;");

        // 🔹 Botones futuristas para "Restablecer Retos" y "Volver"
        Button btnRestablecer = crearBotonFuturista("Restablecer Retos", false);
        btnRestablecer.setOnAction(e -> {
            restablecerRetos();
            cargarRetosCompletados(); // Asegura que los retos se actualicen
            actualizarGrafico();
            mostrarPantallaPrincipal(); // Vuelve a mostrar la pantalla con los cambios reflejados
        });


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



        VBox layout = new VBox(20);
        layout.getChildren().addAll(lblTitulo, contenedorDificultades, lblMensajeBienvenida, pieChart, btnRestablecer, btnVolver);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(15));

        StackPane root = new StackPane();
        root.getChildren().addAll(fondoImagen, layout);

        Scene scene = new Scene(root, 600, 600);
        stage.setScene(scene);

        actualizarGrafico();
    }
    
    private void restablecerRetos() {
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement("DELETE FROM retos WHERE id_usuario = ?")) {
            ps.setInt(1, idUsuario);
            ps.executeUpdate();
            retosCompletados.clear(); // Limpia la lista de retos completados en memoria
        } catch (SQLException e) {
            e.printStackTrace();
        }

        actualizarGrafico();
        lblMensajeBienvenida.setText("¡Todos los retos han sido restablecidos!");
    }


	private Button crearBotonFuturista(String texto, boolean esDificultad) {
        String borderColor = esDificultad ? "#00bfff" : "white"; // 🔵 Azul para dificultades, ⚪ Blanco para los otros
        Button boton = new Button(texto);
        boton.setStyle("-fx-background-color: rgba(255, 255, 255, 0.2); -fx-text-fill: white; "
                + "-fx-font-size: 14px; -fx-font-weight: bold; -fx-border-color: " + borderColor + "; -fx-border-width: 1px; "
                + "-fx-border-radius: 5px; -fx-padding: 8px;");

        boton.setOnMouseEntered(e -> boton.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.5); -fx-text-fill: white; "
                        + "-fx-font-size: 14px; -fx-font-weight: bold; -fx-border-color: " + borderColor + "; -fx-border-width: 1px; "
                        + "-fx-border-radius: 5px; -fx-padding: 8px;"));

        boton.setOnMouseExited(e -> boton.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.2); -fx-text-fill: white; "
                        + "-fx-font-size: 14px; -fx-font-weight: bold; -fx-border-color: " + borderColor + "; -fx-border-width: 1px; "
                        + "-fx-border-radius: 5px; -fx-padding: 8px;"));

        return boton;
    }

    private void mostrarRetos(String dificultad) {
        stage.setTitle("Retos - " + dificultad);

        // 📌 Imagen de fondo adaptable
        ImageView fondoImagen = new ImageView(new Image(getClass().getResourceAsStream("/atomicmind/views/fondoreto.jpg")));
        fondoImagen.setPreserveRatio(false);
        fondoImagen.fitWidthProperty().bind(stage.widthProperty());
        fondoImagen.fitHeightProperty().bind(stage.heightProperty());
        fondoImagen.setOpacity(1);

     // 🔹 Contenedor para los retos alineados a la izquierda
        VBox contenedorRetos = new VBox(10);
        contenedorRetos.setAlignment(Pos.TOP_LEFT); // 📌 Alineación a la izquierda
        contenedorRetos.setPadding(new Insets(15));
        contenedorRetos.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3); -fx-border-color: #00bfff; "
                + "-fx-border-width: 1px; -fx-padding: 10px; -fx-background-radius: 10px;");


        // 🔹 Agregamos cada reto como un CheckBox
        for (String reto : retosDificultad.get(dificultad)) {
            CheckBox checkBox = new CheckBox(reto);
            checkBox.setSelected(retosCompletados.contains(reto));

            // 🔹 Evento para marcar/desmarcar retos
            checkBox.setOnAction(e -> {
                if (checkBox.isSelected()) {
                    marcarRetoComoCompletado(reto);
                } else {
                    desmarcarReto(reto);
                }
                actualizarGrafico();
            });

            // 🔹 Estilo futurista de cada CheckBox
            checkBox.setStyle("-fx-text-fill: white; -fx-font-size: 14px; "
                    + "-fx-border-color: #00bfff; -fx-border-width: 1px; "
                    + "-fx-border-radius: 5px; -fx-padding: 5px; "
                    + "-fx-background-color: rgba(0, 0, 0, 0.5);");

            // 🔹 Efectos al pasar el ratón
            checkBox.setOnMouseEntered(e -> checkBox.setStyle(
                    "-fx-background-color: rgba(0, 0, 0, 0.7); -fx-text-fill: white; "
                    + "-fx-font-size: 14px; -fx-border-color: #1e90ff; "
                    + "-fx-border-width: 1px; -fx-border-radius: 5px; -fx-padding: 5px;"));

            checkBox.setOnMouseExited(e -> checkBox.setStyle(
                    "-fx-background-color: rgba(0, 0, 0, 0.5); -fx-text-fill: white; "
                    + "-fx-font-size: 14px; -fx-border-color: #00bfff; "
                    + "-fx-border-width: 1px; -fx-border-radius: 5px; -fx-padding: 5px;"));

            contenedorRetos.getChildren().add(checkBox);
        }

        Button btnVolver = new Button("Volver");
        btnVolver.setOnAction(e -> mostrarPantallaPrincipal());

        // 🔹 Diseño futurista del botón "Volver"
        btnVolver.setStyle("-fx-background-color: rgba(255, 255, 255, 0.2); -fx-text-fill: white; "
                + "-fx-font-size: 14px; -fx-border-color: white; -fx-border-width: 1px; "
                + "-fx-border-radius: 5px; -fx-padding: 8px;");

        btnVolver.setOnMouseEntered(e -> btnVolver.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.5); -fx-text-fill: white; "
                        + "-fx-font-size: 14px; -fx-border-color: white; -fx-border-width: 1px; "
                        + "-fx-border-radius: 5px; -fx-padding: 8px;"));

        btnVolver.setOnMouseExited(e -> btnVolver.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.2); -fx-text-fill: white; "
                        + "-fx-font-size: 14px; -fx-border-color: white; -fx-border-width: 1px; "
                        + "-fx-border-radius: 5px; -fx-padding: 8px;"));

        VBox layout = new VBox(15);
        layout.getChildren().addAll(contenedorRetos, btnVolver);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10));

        // 🔹 Oscurecemos el fondo del contenedor
        layout.setStyle("-fx-background-color: rgba(0, 0, 0, 0.6); -fx-border-color: #00bfff; " +
                        "-fx-border-width: 1px; -fx-border-radius: 10px; -fx-padding: 15px;");


        StackPane root = new StackPane();
        root.getChildren().addAll(fondoImagen, layout);

        Scene scene = new Scene(root, 600, 600);
        stage.setScene(scene);
        stage.show();

    }


    private void marcarRetoComoCompletado(String reto) {
        retosCompletados.add(reto);
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement("INSERT INTO retos (id_usuario, descripcion) VALUES (?, ?)")) {
            ps.setInt(1, idUsuario);
            ps.setString(2, reto);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void desmarcarReto(String reto) {
        retosCompletados.remove(reto);
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement("DELETE FROM retos WHERE id_usuario = ? AND descripcion = ?")) {
            ps.setInt(1, idUsuario);
            ps.setString(2, reto);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void actualizarGrafico() {
        pieChart.getData().clear();
        List<PieChart.Data> datos = crearDatosGrafico();
        pieChart.getData().addAll(datos);
        lblMensajeBienvenida.setVisible(datos.isEmpty());
    }

    private PieChart crearGrafico() {
        PieChart chart = new PieChart();
        chart.setTitle("Progreso de Retos Completados");

        // 🔹 Aplicamos el estilo en negrita y negro
        chart.lookup(".chart-title").setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: black;");

        return chart;
    }


    private List<PieChart.Data> crearDatosGrafico() {
        List<PieChart.Data> datos = new ArrayList<>();

        for (String dificultad : retosDificultad.keySet()) {
            int completados = (int) retosDificultad.get(dificultad).stream().filter(retosCompletados::contains).count();
            int total = retosDificultad.get(dificultad).size();
            double porcentaje = total == 0 ? 0 : (double) completados / total * 100;
            datos.add(new PieChart.Data(dificultad + " (" + (int) porcentaje + "%)", porcentaje));
        }

        return datos;
    }
}

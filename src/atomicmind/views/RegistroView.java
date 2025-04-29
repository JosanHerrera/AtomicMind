package atomicmind.views;

import atomicmind.database.ConexionBD;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class RegistroView extends Application {

    private Stage stage;

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        primaryStage.setTitle("Registro de Usuario");

        // 📌 Imagen de fondo
        ImageView fondoImagen = new ImageView(new Image(getClass().getResourceAsStream("/atomicmind/views/fondoregistro.jpg")));
        fondoImagen.setPreserveRatio(false);
        fondoImagen.fitWidthProperty().bind(stage.widthProperty());
        fondoImagen.fitHeightProperty().bind(stage.heightProperty());

        // 📌 Campos de texto un poco más grandes
        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre completo");
        txtNombre.setMaxWidth(280); // 📌 Un poco más grande

        TextField txtCorreo = new TextField();
        txtCorreo.setPromptText("Correo electrónico");
        txtCorreo.setMaxWidth(280);

        PasswordField txtPassword = new PasswordField();
        txtPassword.setPromptText("Contraseña");
        txtPassword.setMaxWidth(280);

        // 📌 Botones futuristas un poco más pequeños
        Button btnRegistrarse = crearBotonVerde("Registrarse");
        btnRegistrarse.setMaxWidth(220); // 📌 Más pequeño que los campos

        Button btnVolver = crearBotonAzul("⬅ Volver al Login");
        btnVolver.setMaxWidth(220); // 📌 Mantener diferencia de tamaño

        Label lblMensaje = new Label();
        lblMensaje.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");

        // 📌 Diseño con fondo semitransparente y distribución mejorada
        VBox layout = new VBox(14);
        layout.getChildren().addAll(txtNombre, txtCorreo, txtPassword, btnRegistrarse, btnVolver, lblMensaje);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: rgba(0, 0, 0, 0.6); -fx-padding: 20px; -fx-border-radius: 10px;");
        layout.setMaxWidth(320); // 📌 Ajustamos el ancho del contenedor

        StackPane root = new StackPane(fondoImagen, layout);
        Scene scene = new Scene(root, 600, 600); // Pantalla más grande
        primaryStage.setScene(scene);
        primaryStage.show();

        // 📌 Evento para registrar usuario con validación
        btnRegistrarse.setOnAction(e -> {
            String nombre = txtNombre.getText().trim();
            String correo = txtCorreo.getText().trim();
            String password = txtPassword.getText().trim();

            if (nombre.isEmpty() || correo.isEmpty() || password.isEmpty()) {
                lblMensaje.setText("⚠️ Debes completar todos los campos.");
                lblMensaje.setStyle("-fx-text-fill: orange; -fx-font-weight: bold;");
                return;
            }

            if (correoYaRegistrado(correo)) {
                lblMensaje.setText("⚠️ El correo \"" + correo + "\" ya existe. Pruebe con uno nuevo.");
                lblMensaje.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                return;
            }

            if (registrarUsuario(nombre, correo, password)) {
                lblMensaje.setText("Usuario registrado correctamente.");
                lblMensaje.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
            } else {
                lblMensaje.setText("⚠️ Error al registrar. Inténtalo de nuevo.");
                lblMensaje.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            }
        });

        // 📌 Evento para volver a la pantalla de Login
        btnVolver.setOnAction(e -> new LoginView().start(primaryStage));
    }

    private boolean correoYaRegistrado(String correo) {
        String consulta = "SELECT COUNT(*) FROM usuarios WHERE correo = ?";

        try (Connection conexion = ConexionBD.getConnection();
             PreparedStatement stmt = conexion.prepareStatement(consulta)) {

            stmt.setString(1, correo);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error al verificar el correo: " + e.getMessage());
            return false;
        }
    }

    private boolean registrarUsuario(String nombre, String correo, String password) {
        String consulta = "INSERT INTO usuarios (nombre, correo, contraseña) VALUES (?, ?, ?)";

        try (Connection conexion = ConexionBD.getConnection();
             PreparedStatement stmt = conexion.prepareStatement(consulta)) {

            stmt.setString(1, nombre);
            stmt.setString(2, correo);
            stmt.setString(3, password);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error al registrar usuario: " + e.getMessage());
            return false;
        }
    }

    private Button crearBotonVerde(String texto) {
        Button boton = new Button(texto);
        boton.setStyle("-fx-background-color: rgba(0, 255, 0, 0.7); -fx-text-fill: white; "
                + "-fx-font-size: 14px; -fx-font-weight: bold; -fx-border-color: green; "
                + "-fx-border-width: 1px; -fx-border-radius: 5px; -fx-padding: 8px;");
        boton.setOnMouseEntered(e -> boton.setStyle(
                "-fx-background-color: rgba(0, 255, 0, 0.9); -fx-text-fill: white; "
                        + "-fx-font-size: 14px; -fx-border-color: darkgreen; -fx-border-width: 1px; "
                        + "-fx-border-radius: 5px; -fx-padding: 8px;"));
        boton.setOnMouseExited(e -> boton.setStyle(
                "-fx-background-color: rgba(0, 255, 0, 0.7); -fx-text-fill: white; "
                        + "-fx-font-size: 14px; -fx-border-color: green; -fx-border-width: 1px; "
                        + "-fx-border-radius: 5px; -fx-padding: 8px;"));
        return boton;
    }

    private Button crearBotonAzul(String texto) {
        Button boton = new Button(texto);
        boton.setStyle("-fx-background-color: rgba(0, 100, 255, 0.7); -fx-text-fill: white; "
                + "-fx-font-size: 14px; -fx-font-weight: bold; -fx-border-color: blue; "
                + "-fx-border-width: 1px; -fx-border-radius: 5px; -fx-padding: 8px;");
        boton.setOnMouseEntered(e -> boton.setStyle(
                "-fx-background-color: rgba(0, 100, 255, 0.9); -fx-text-fill: white; "
                        + "-fx-font-size: 14px; -fx-border-color: darkblue; -fx-border-width: 1px; "
                        + "-fx-border-radius: 5px; -fx-padding: 8px;"));
        boton.setOnMouseExited(e -> boton.setStyle(
                "-fx-background-color: rgba(0, 100, 255, 0.7); -fx-text-fill: white; "
                        + "-fx-font-size: 14px; -fx-border-color: blue; -fx-border-width: 1px; "
                        + "-fx-border-radius: 5px; -fx-padding: 8px;"));
        return boton;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

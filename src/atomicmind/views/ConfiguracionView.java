package atomicmind.views;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import atomicmind.database.ConexionBD;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConfiguracionView {

    private Stage stage;
    private String correoUsuario;

    public ConfiguracionView(Stage stage, String correoUsuario) {
        this.stage = stage;
        this.correoUsuario = correoUsuario;

        if (correoUsuario == null || correoUsuario.isEmpty()) {
            System.out.println("❌ ERROR: El correo del usuario es NULL o está vacío.");
        } else {
            System.out.println("✅ Correo del usuario recibido correctamente: " + correoUsuario);
        }
    }


    public void mostrarPantalla() {
        stage.setTitle("Configuración de Cuenta");

        // 📌 Imagen de fondo
        ImageView fondoImagen = new ImageView(new Image(getClass().getResourceAsStream("/atomicmind/views/imagenconfiguracion.jpg")));
        fondoImagen.setPreserveRatio(false);
        fondoImagen.fitWidthProperty().bind(stage.widthProperty());
        fondoImagen.fitHeightProperty().bind(stage.heightProperty());

        // 📌 Campo de contraseña más ancho
        PasswordField txtNuevaPassword = new PasswordField();
        txtNuevaPassword.setStyle("-fx-background-radius: 15px; -fx-border-radius: 15px; -fx-padding: 10px;");
        txtNuevaPassword.setPromptText("Nueva contraseña");
        txtNuevaPassword.setMaxWidth(250);

        // 📌 Botones futuristas
        Button btnCambiarPassword = crearBotonFuturista("Cambiar contraseña");
        Button btnEliminarCuenta = crearBotonRojo("Eliminar cuenta"); // Ahora en rojo

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

        Label lblMensaje = new Label();
        lblMensaje.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");

        VBox layout = new VBox(20);
        layout.getChildren().addAll(txtNuevaPassword, btnCambiarPassword, btnEliminarCuenta, btnVolver, lblMensaje);
        layout.setAlignment(Pos.CENTER);

        // 📌 Agregamos el fondo y el contenido al StackPane
        StackPane root = new StackPane(fondoImagen, layout);
        Scene scene = new Scene(root, 600, 600);
        stage.setScene(scene);

        btnCambiarPassword.setOnAction(e -> {
            String nuevaPassword = txtNuevaPassword.getText();
            if (cambiarPassword(correoUsuario, nuevaPassword)) {
                lblMensaje.setText("Contraseña actualizada correctamente.");
                lblMensaje.setStyle("-fx-text-fill: green; -fx-font-weight: bold;"); // ✅ Ahora en negrita
            } else {
                lblMensaje.setText("Error al actualizar la contraseña.");
                lblMensaje.setStyle("-fx-text-fill: red; -fx-font-weight: bold;"); // ✅ Ahora en negrita
            }
        });

        btnEliminarCuenta.setOnAction(e -> {
            if (eliminarCuenta(correoUsuario)) {
                lblMensaje.setText("Tu cuenta ha sido eliminada correctamente.");
                lblMensaje.setStyle("-fx-text-fill: green; -fx-font-weight: bold;"); // ✅ Ahora en negrita

                new Thread(() -> {
                    try {
                        Thread.sleep(2000);
                        Platform.runLater(() -> new LoginView().start(stage));
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }).start();

            } else {
                lblMensaje.setText("Error al eliminar la cuenta.");
                lblMensaje.setStyle("-fx-text-fill: red; -fx-font-weight: bold;"); // ✅ Ahora en negrita
            }
        });

        btnVolver.setOnAction(e -> new PrincipalView(stage, correoUsuario).mostrarPantalla());
    }

    // 📌 Método para crear botones futuristas con efectos al pasar el ratón
    private Button crearBotonFuturista(String texto) {
        Button boton = new Button(texto);
        boton.setStyle("-fx-background-color: rgba(255, 255, 255, 0.2); -fx-text-fill: white; "
                + "-fx-font-size: 14px; -fx-font-weight: bold; -fx-border-color: white; "
                + "-fx-border-width: 1px; -fx-border-radius: 5px; -fx-padding: 8px;");

        boton.setOnMouseEntered(e -> boton.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.5); -fx-text-fill: white; "
                        + "-fx-font-size: 14px; -fx-border-color: white; -fx-border-width: 1px; "
                        + "-fx-border-radius: 5px; -fx-padding: 8px;"));

        boton.setOnMouseExited(e -> boton.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.2); -fx-text-fill: white; "
                        + "-fx-font-size: 14px; -fx-border-color: white; -fx-border-width: 1px; "
                        + "-fx-border-radius: 5px; -fx-padding: 8px;"));

        return boton;
    }

    // 📌 Método para crear el botón "Eliminar cuenta" en rojo
    private Button crearBotonRojo(String texto) {
        Button boton = new Button(texto);
        boton.setStyle("-fx-background-color: rgba(255, 0, 0, 0.7); -fx-text-fill: white; "
                + "-fx-font-size: 14px; -fx-font-weight: bold; -fx-border-color: red; "
                + "-fx-border-width: 1px; -fx-border-radius: 5px; -fx-padding: 8px;");

        boton.setOnMouseEntered(e -> boton.setStyle(
                "-fx-background-color: rgba(255, 0, 0, 0.9); -fx-text-fill: white; "
                        + "-fx-font-size: 14px; -fx-border-color: darkred; -fx-border-width: 1px; "
                        + "-fx-border-radius: 5px; -fx-padding: 8px;"));

        boton.setOnMouseExited(e -> boton.setStyle(
                "-fx-background-color: rgba(255, 0, 0, 0.7); -fx-text-fill: white; "
                        + "-fx-font-size: 14px; -fx-border-color: red; -fx-border-width: 1px; "
                        + "-fx-border-radius: 5px; -fx-padding: 8px;"));

        return boton;
    }

    private boolean cambiarPassword(String correo, String nuevaPassword) {
        if (nuevaPassword == null || nuevaPassword.trim().isEmpty()) {
            System.out.println("⚠️ Error: La nueva contraseña no puede estar vacía.");
            return false;
        }

        // 🔹 Verificar si el usuario realmente existe en la base de datos
        String verificarUsuario = "SELECT correo FROM usuarios WHERE correo = ?";
        try (Connection conexion = ConexionBD.getConnection();
             PreparedStatement verificarStmt = conexion.prepareStatement(verificarUsuario)) {

            verificarStmt.setString(1, correo);
            ResultSet resultado = verificarStmt.executeQuery();

            if (!resultado.next()) {
                System.out.println("❌ Error: No se encontró el usuario con el correo: " + correo);
                return false;
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al verificar el usuario: " + e.getMessage());
            return false;
        }

        // 🔹 Si el usuario existe, proceder con la actualización de la contraseña
        String consulta = "UPDATE usuarios SET contraseña = ? WHERE correo = ?";

        try (Connection conexion = ConexionBD.getConnection();
             PreparedStatement stmt = conexion.prepareStatement(consulta)) {

            stmt.setString(1, nuevaPassword);
            stmt.setString(2, correo);

            int filasActualizadas = stmt.executeUpdate();

            if (filasActualizadas > 0) {
                System.out.println("✅ Contraseña actualizada correctamente para el usuario: " + correo);
                return true;
            } else {
                System.out.println("⚠️ No se pudo actualizar la contraseña (posible error de BD o usuario no encontrado).");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar la contraseña en SQLite: " + e.getMessage());
            return false;
        }
    }



    private boolean eliminarCuenta(String correo) {
        String consulta = "DELETE FROM usuarios WHERE correo = ?";

        try (Connection conexion = ConexionBD.getConnection();
             PreparedStatement stmt = conexion.prepareStatement(consulta)) {

            stmt.setString(1, correo);

            int filasEliminadas = stmt.executeUpdate();
            return filasEliminadas > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar la cuenta en SQLite: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}

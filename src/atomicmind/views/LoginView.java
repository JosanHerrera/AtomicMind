package atomicmind.views;

import atomicmind.database.ConexionBD;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.InputStream;
import java.sql.*;

public class LoginView extends Application {

    private Stage stage;

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        mostrarSplashScreen();
    }

    /**
     * 🖼️ Muestra el logo antes de cargar el login
     */
    private void mostrarSplashScreen() {
        ImageView logoView = cargarImagen("/atomicmind/views/PantallaPrincipalAtomicMind.jpg", 650);

        if (logoView == null) {
            System.out.println("❌ ERROR: No se encontró la imagen del splash screen.");
            mostrarLogin();
            return;
        } else {
            System.out.println("✅ Imagen del splash screen cargada correctamente.");
        }

        stage.setTitle("Bienvenidos a AtomicMind");

        StackPane splashLayout = new StackPane(logoView);
        Scene splashScene = new Scene(splashLayout, 600, 600);
        stage.setScene(splashScene);
        stage.show();

        new Thread(() -> {
            try {
                Thread.sleep(3000);
                javafx.application.Platform.runLater(this::mostrarLogin);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 📌 Muestra la pantalla de inicio de sesión con fondo normal (sin brillo extra)
     */
    private void mostrarLogin() {
        stage.setTitle("Inicio de Sesión");

        // 📌 Imagen de fondo sin modificaciones
        ImageView fondoImagen = new ImageView(new Image(getClass().getResourceAsStream("/atomicmind/views/fondo.jpg")));
        fondoImagen.setPreserveRatio(false);
        fondoImagen.fitWidthProperty().bind(stage.widthProperty());
        fondoImagen.fitHeightProperty().bind(stage.heightProperty());


        // 📌 Logo pequeño
        ImageView logoPequeno = cargarImagen("/atomicmind/views/logoprincipal.png", 250);

     // 📌 Campos de texto con bordes redondeados, bordes azules permanentes y efecto hover
        TextField txtCorreo = new TextField();
        txtCorreo.setPromptText("Correo electrónico");
        txtCorreo.setMaxWidth(300);
        txtCorreo.setStyle("-fx-background-radius: 15px; -fx-border-radius: 15px; "
                + "-fx-padding: 5px; -fx-alignment: center; -fx-border-color: #2196F3; " // Azul permanente
                + "-fx-background-color: white; -fx-border-width: 2px;");

        // Efecto al pasar el ratón por encima (fondo azul claro)
        txtCorreo.setOnMouseEntered(e -> txtCorreo.setStyle("-fx-background-radius: 15px; -fx-border-radius: 15px; "
                + "-fx-padding: 5px; -fx-alignment: center; -fx-border-color: #2196F3; " // Azul permanente
                + "-fx-background-color: #E3F2FD; -fx-border-width: 2px;"));
        txtCorreo.setOnMouseExited(e -> txtCorreo.setStyle("-fx-background-radius: 15px; -fx-border-radius: 15px; "
                + "-fx-padding: 5px; -fx-alignment: center; -fx-border-color: #2196F3; " // Azul permanente
                + "-fx-background-color: white; -fx-border-width: 2px;"));

        // 📌 Campo de contraseña con los mismos efectos
        PasswordField txtPassword = new PasswordField();
        txtPassword.setPromptText("Contraseña");
        txtPassword.setMaxWidth(300);
        txtPassword.setStyle("-fx-background-radius: 15px; -fx-border-radius: 15px; "
                + "-fx-padding: 5px; -fx-alignment: center; -fx-border-color: #2196F3; " // Azul permanente
                + "-fx-background-color: white; -fx-border-width: 2px;");

        // Efecto al pasar el ratón por encima (fondo azul claro)
        txtPassword.setOnMouseEntered(e -> txtPassword.setStyle("-fx-background-radius: 15px; -fx-border-radius: 15px; "
                + "-fx-padding: 5px; -fx-alignment: center; -fx-border-color: #2196F3; " // Azul permanente
                + "-fx-background-color: #E3F2FD; -fx-border-width: 2px;"));
        txtPassword.setOnMouseExited(e -> txtPassword.setStyle("-fx-background-radius: 15px; -fx-border-radius: 15px; "
                + "-fx-padding: 5px; -fx-alignment: center; -fx-border-color: #2196F3; " // Azul permanente
                + "-fx-background-color: white; -fx-border-width: 2px;"));

        // 📌 Botones normales con tonos suaves
        Button btnLogin = crearBoton("Iniciar sesión", "#4CAF50");  // Verde suave
        Button btnRegistro = crearBoton("Registrar usuario", "#2196F3"); // Azul suave

        // 📌 Mensaje de error o éxito
        Label lblMensaje = new Label();
        lblMensaje.setStyle("-fx-text-fill: red;");

        // 📌 Contenedor con fondo semitransparente para mejor visibilidad
        VBox layout = new VBox(15);
        layout.getChildren().addAll(logoPequeno, txtCorreo, txtPassword, btnLogin, btnRegistro, lblMensaje);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: rgba(0, 0, 0, 0.1); -fx-padding: 20px; -fx-border-radius: 10px;");

     // 📌 Fondo + Contenido
        StackPane root = new StackPane(fondoImagen, layout);

        Scene scene = new Scene(root, 600, 600);
        stage.setScene(scene);

        // 📌 Evitar que el primer campo se seleccione automáticamente
        Platform.runLater(() -> layout.requestFocus());


        // 📌 Evento al presionar el botón de login
        btnLogin.setOnAction(e -> {
            String correo = txtCorreo.getText();
            String password = txtPassword.getText();

            if (validarCredenciales(correo, password)) {
                lblMensaje.setText("✔ Inicio de sesión exitoso.");
                lblMensaje.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                new PrincipalView(stage, correo).mostrarPantalla();
            } else {
                lblMensaje.setText("❌ Correo o contraseña incorrectos.");
                lblMensaje.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            }
        });

        btnRegistro.setOnAction(e -> new RegistroView().start(stage));
    }

    /**
     * 🔧 Método reutilizable para cargar imágenes
     */
    private ImageView cargarImagen(String ruta, double ancho) {
        InputStream stream = getClass().getResourceAsStream(ruta);
        if (stream == null) return null;
        ImageView imageView = new ImageView(new Image(stream));
        imageView.setFitWidth(ancho);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    /**
     * 📌 Crea botones con un diseño limpio y profesional
     */
    private Button crearBoton(String texto, String color) {
        Button boton = new Button(texto);
        boton.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; "
                + "-fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 8px; "
                + "-fx-border-color: white; -fx-border-width: 1px;");

        boton.setOnMouseEntered(e -> boton.setStyle("-fx-background-color: #ffffff; -fx-text-fill: " + color + "; "
                + "-fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 8px; "
                + "-fx-border-color: " + color + "; -fx-border-width: 1px;"));

        boton.setOnMouseExited(e -> boton.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; "
                + "-fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 8px; "
                + "-fx-border-color: white; -fx-border-width: 1px;"));

        return boton;
    }

    /**
     * 📌 Valida credenciales en la base de datos
     */
    private boolean validarCredenciales(String correo, String password) {
        String consulta = "SELECT * FROM usuarios WHERE correo = ? AND contraseña = ?";

        try (Connection conexion = ConexionBD.getConnection();
             PreparedStatement stmt = conexion.prepareStatement(consulta)) {

            stmt.setString(1, correo);
            stmt.setString(2, password);
            ResultSet resultado = stmt.executeQuery();
            return resultado.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

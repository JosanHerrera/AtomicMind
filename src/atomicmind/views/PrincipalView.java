package atomicmind.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PrincipalView {

    private Stage stage;
    private String correoUsuario;

    public PrincipalView(Stage stage, String correoUsuario) {
        this.stage = stage;
        this.correoUsuario = correoUsuario;
    }

    public void mostrarPantalla() {
        stage.setTitle("AtomicMind - Menú Principal");

        // 📌 Imagen de fondo adaptable
        ImageView fondoImagen = new ImageView(new Image(getClass().getResourceAsStream("/atomicmind/views/fondoprincipal.jpg")));
        fondoImagen.setPreserveRatio(false);
        fondoImagen.fitWidthProperty().bind(stage.widthProperty());
        fondoImagen.fitHeightProperty().bind(stage.heightProperty());

        // 📌 Logo en la parte superior
        ImageView logo = new ImageView(new Image(getClass().getResourceAsStream("/atomicmind/views/logo.png")));
        logo.setFitWidth(170);
        logo.setPreserveRatio(true);

        // 📌 Crear botones con diseño moderno
        Button btnEmociones = new Button("😃 ¿Cómo te sientes hoy?");
        Button btnAlimentacion = new Button("🥗 Alimentación");
        Button btnRetos = new Button("🏆 Retos");
        Button btnHabitos = new Button("📱 Hábitos Digitales");
        Button btnConfiguracion = new Button("⚙️ Configuración");
        Button btnCerrarSesion = new Button("❌ Cerrar Sesión");

        // Aplicar estilos CSS a los botones
        btnEmociones.getStyleClass().add("boton-principal");
        btnAlimentacion.getStyleClass().add("boton-principal");
        btnRetos.getStyleClass().add("boton-principal");
        btnHabitos.getStyleClass().add("boton-principal");
        btnConfiguracion.getStyleClass().add("boton-configuracion");
        btnCerrarSesion.getStyleClass().add("boton-cerrar");

        // 📌 GridPane para organizar los botones en dos columnas
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(20));

        grid.add(btnEmociones, 0, 0);
        grid.add(btnAlimentacion, 1, 0);
        grid.add(btnRetos, 0, 1);
        grid.add(btnHabitos, 1, 1);
        
        // 📌 HBox para los botones de configuración y cerrar sesión
        HBox hbox = new HBox(20, btnConfiguracion, btnCerrarSesion);
        hbox.setAlignment(Pos.CENTER);

        // 📌 Contenedor con fondo semitransparente para mejor visibilidad de los botones
        VBox layout = new VBox(25);
        layout.getChildren().addAll(logo, grid, hbox);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: rgba(0, 0, 0, 0.1); -fx-padding: 25px; -fx-border-radius: 10px;");

        // 📌 Fondo + Contenido
        StackPane root = new StackPane(fondoImagen, layout);

        // 📌 Escena con CSS aplicado
        Scene scene = new Scene(root, 600, 600);
        scene.getStylesheets().add(getClass().getResource("/atomicmind/views/styles/dark-theme.css").toExternalForm());

        stage.setScene(scene);

        // 📌 Acciones de los botones
        btnEmociones.setOnAction(e -> new EmocionesView(stage, correoUsuario).mostrarPantalla());
        btnAlimentacion.setOnAction(e -> new AlimentacionView(stage, correoUsuario).mostrarPantalla());
        btnRetos.setOnAction(e -> new RetosView(stage, correoUsuario));

        btnHabitos.setOnAction(e -> new HabitosDigitalesView(stage, correoUsuario).mostrarPantallaPrincipal());
        btnConfiguracion.setOnAction(e -> new ConfiguracionView(stage, correoUsuario).mostrarPantalla());
        btnCerrarSesion.setOnAction(e -> new LoginView().start(stage));
    }
}

package atomicmind.views;

import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class EmocionesView {

    private Stage stage;
    private String correoUsuario;
    private Label lblMensajeMotivador;
    private Label lblReto;
    private Label lblComida;
    private Random random = new Random();

    private final List<String> coloresFondos = Arrays.asList(
            "#FF6F61", // Rojo coral
            "#FFB400", // Amarillo cálido
            "#4A90E2", // Azul vibrante
            "#F5A623"  // Naranja brillante
    );

    private final List<String> mensajesFeliz = Arrays.asList(
            "¡Sigue sonriendo, el mundo necesita más de esa energía!",
            "Disfruta tu felicidad, compártela con quienes te rodean.",
            "Aprovecha este día al máximo, la felicidad se multiplica cuando la compartes."
    );

    private final List<String> mensajesTriste = Arrays.asList(
            "Recuerda, después de la tormenta siempre sale el sol.",
            "Eres más fuerte de lo que crees, esto también pasará.",
            "No tengas miedo de sentir, cada emoción nos enseña algo."
    );

    private final List<String> retos = Arrays.asList(
            "Haz 20 sentadillas.", "Camina 10 minutos al aire libre.", "Bebe un vaso de agua.",
            "Escribe 3 cosas por las que estás agradecido hoy.", "Medita durante 5 minutos."
    );
    
    private final List<String> mensajesMotivado = Arrays.asList(
            "El esfuerzo de hoy será tu éxito de mañana.",
            "Sigue adelante, cada paso te acerca más a tu meta.",
            "No hay límites para lo que puedes lograr, solo los que te pones tú mismo."
    );

    private final List<String> mensajesCansado = Arrays.asList(
            "Descansa, recarga energías y sigue adelante con más fuerza.",
            "El descanso también es parte del progreso, tómate un respiro.",
            "Escucha a tu cuerpo, un pequeño descanso puede hacer la diferencia."
    );

    private final List<String> mensajesEstresado = Arrays.asList(
            "Respira hondo, todo tiene solución paso a paso.",
            "No te preocupes por lo que no puedes controlar, enfócate en lo que sí.",
            "Tómate un momento para ti, relájate y despeja tu mente."
    );


    private final List<String> comidas = Arrays.asList(
            "🍳 Tortilla de claras con espinacas y queso fresco.",
            "🥑 Tostadas integrales con aguacate y huevo pochado.",
            "🍗 Pechuga de pollo a la plancha con ensalada de quinoa y verduras."
    );

    public EmocionesView(Stage stage, String correoUsuario) {
        this.stage = stage;
        this.setCorreoUsuario(correoUsuario);
    }

    private void setCorreoUsuario(String correoUsuario2) {
		// TODO Auto-generated method stub
		
	}

	public void mostrarPantalla() {
        stage.setTitle("AtomicMind - Estado Emocional");

        ImageView fondoImagen = new ImageView(new Image(getClass().getResourceAsStream("/atomicmind/views/fondoemociones.jpg")));
        fondoImagen.setPreserveRatio(false);
        fondoImagen.fitWidthProperty().bind(stage.widthProperty());
        fondoImagen.fitHeightProperty().bind(stage.heightProperty());

        // 📌 ComboBox con texto en negrita y negro
        ComboBox<String> cbEmocion = new ComboBox<>();
        cbEmocion.getItems().addAll("Feliz", "Triste", "Motivado", "Cansado", "Estresado");
        cbEmocion.setPromptText("Elige una emoción");
        cbEmocion.setStyle("-fx-background-color: white; -fx-text-fill: black; "
                + "-fx-font-size: 14px; -fx-font-weight: bold; "
                + "-fx-border-color: black; -fx-border-width: 1px; "
                + "-fx-border-radius: 5px; -fx-padding: 5px;");
        
        
     // 📌 Texto explicativo sobre la pantalla (Antes del texto de las 4 leyes)
        Text textoExplicativo = new Text(
                "En esta pantalla podrás elegir cómo te sientes en este momento.\n"
                + "Según la emoción que selecciones, tendrás dos opciones para mejorar tu estado de ánimo:\n\n"
                + "🎯 Retos: Te ayudan a sentirte realizado y a elevar tu energía.\n"
                + "🥗 Comida recomendada: Aporta nutrientes clave para mejorar tu bienestar.\n\n"
                + "✨ Ambas opciones están diseñadas para mejorar tu crecimiento personal. ¡Elige una emoción!"
        );
        textoExplicativo.setTextAlignment(TextAlignment.CENTER);
        textoExplicativo.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-fill: black;");
        textoExplicativo.setWrappingWidth(550); // 🔹 Para que sea compacto y no se expanda demasiado

        
        

        // 📌 Texto de las leyes con fondo blanco, negro y en negrita
        Text textoLeyes = new Text(
                "Para CREAR buenos hábitos aplica las #4LeyesDelCambioDeConducta\n" +
                "1. SEÑAL: Hacerlo obvio\n" +
                "2. ANHELO: Hacerlo atractivo\n" +
                "3. RESPUESTA: Hacerlo sencillo\n" +
                "4. RECOMPENSA: Hacerlo satisfactorio"
        );
        textoLeyes.setTextAlignment(TextAlignment.CENTER);
        textoLeyes.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-fill: black;");
        
        // 📌 Estilos base para los mensajes
        lblMensajeMotivador = new Label();
        lblMensajeMotivador.setWrapText(true);
        lblMensajeMotivador.setMaxWidth(300);
        lblMensajeMotivador.setVisible(false);

        lblReto = new Label();
        lblReto.setWrapText(true);
        lblReto.setMaxWidth(300);
        lblReto.setVisible(false);

        lblComida = new Label();
        lblComida.setWrapText(true);
        lblComida.setMaxWidth(300);
        lblComida.setVisible(false);

        // 📌 Botones futuristas con letra en negrita y color negro
        Button btnNuevoReto = crearBotonFuturista("🎯 Probar un reto");
        btnNuevoReto.setVisible(false);

        Button btnElegirComida = crearBotonFuturista("🍽️ Elegir comida");
        btnElegirComida.setVisible(false);

     // 📌 Botón "Volver al Menú" con un azul más suave y efecto al pasar el ratón
        Button btnVolver = new Button("🔙 Volver al Menú");
        btnVolver.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; "
                + "-fx-font-size: 14px; -fx-font-weight: bold; "
                + "-fx-border-color: #1976D2; -fx-border-width: 2px; "
                + "-fx-border-radius: 8px; -fx-padding: 10px;");

        // 📌 Efecto al pasar el ratón (cambia a un tono más claro)
        btnVolver.setOnMouseEntered(e -> btnVolver.setStyle(
                "-fx-background-color: #64B5F6; -fx-text-fill: white; "
                + "-fx-font-size: 14px; -fx-font-weight: bold; "
                + "-fx-border-color: #1976D2; -fx-border-width: 2px; "
                + "-fx-border-radius: 8px; -fx-padding: 10px;"));

        // 📌 Al quitar el cursor, vuelve al color original
        btnVolver.setOnMouseExited(e -> btnVolver.setStyle(
                "-fx-background-color: #2196F3; -fx-text-fill: white; "
                + "-fx-font-size: 14px; -fx-font-weight: bold; "
                + "-fx-border-color: #1976D2; -fx-border-width: 2px; "
                + "-fx-border-radius: 8px; -fx-padding: 10px;"));

        // 📌 Funcionalidad para volver al menú principal
        btnVolver.setOnAction(e -> new PrincipalView(stage, correoUsuario).mostrarPantalla());


     // 🔹 Crear un VBox para el texto explicativo con un tono verdoso
        VBox contenedorTextoExplicativo = new VBox();
        contenedorTextoExplicativo.setAlignment(Pos.CENTER);
        contenedorTextoExplicativo.setStyle("-fx-background-color: rgba(144, 238, 144, 0.8); " // Verde claro
                + "-fx-border-radius: 10px; -fx-padding: 10px; -fx-border-color: #2E7D32; " // Borde verde oscuro
                + "-fx-border-width: 1px; -fx-background-radius: 10px;");
        contenedorTextoExplicativo.getChildren().add(textoExplicativo);

        // 🔹 Crear otro VBox para el texto de las 4 leyes con un tono naranja claro
        VBox contenedorTextoLeyes = new VBox();
        contenedorTextoLeyes.setAlignment(Pos.CENTER);
        contenedorTextoLeyes.setStyle("-fx-background-color: rgba(255, 179, 71, 0.8); " // Naranja claro
                + "-fx-border-radius: 10px; -fx-padding: 10px; -fx-border-color: #E65100; " // Borde naranja oscuro
                + "-fx-border-width: 1px; -fx-background-radius: 10px;");
        contenedorTextoLeyes.getChildren().add(textoLeyes);

        // 🔹 Definir el VBox con el orden correcto al iniciar la pantalla
        VBox layout = new VBox(10); // 🔹 Reducimos la separación para que quede compacto
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(contenedorTextoExplicativo, cbEmocion, contenedorTextoLeyes, btnVolver);


        cbEmocion.setOnAction(e -> {
            if (cbEmocion.getValue() != null) {
                contenedorTextoLeyes.setVisible(false); // Oculta las 4 leyes al elegir una emoción
                mostrarMensajeMotivador(cbEmocion.getValue());
                animarEntrada(lblMensajeMotivador);

                // 🔹 Mostrar botones y ocultar reto y comida inicialmente
                lblMensajeMotivador.setVisible(true);
                btnNuevoReto.setVisible(true);
                btnElegirComida.setVisible(true);
                lblReto.setVisible(false);
                lblComida.setVisible(false);

                // 🔹 Mantener orden correcto sin reto ni comida al inicio
                layout.getChildren().setAll(
                        contenedorTextoExplicativo, cbEmocion, lblMensajeMotivador,
                        btnNuevoReto, btnElegirComida, btnVolver
                );
            }
        });

        btnNuevoReto.setOnAction(e -> {
            generarReto();
            animarEntrada(lblReto);
            lblReto.setVisible(true);
            lblComida.setVisible(false); // 🔹 Ocultamos la comida si estaba visible

            // 🔹 Insertar reto debajo del botón correspondiente
            layout.getChildren().setAll(
                    contenedorTextoExplicativo, cbEmocion, lblMensajeMotivador,
                    btnNuevoReto, lblReto, // 🔹 Reto debajo de su botón
                    btnElegirComida, btnVolver
            );
        });

        btnElegirComida.setOnAction(e -> {
            generarComida();
            animarEntrada(lblComida);
            lblComida.setVisible(true);
            lblReto.setVisible(false); // 🔹 Ocultamos el reto si estaba visible

            // 🔹 Insertar comida debajo del botón correspondiente
            layout.getChildren().setAll(
                    contenedorTextoExplicativo, cbEmocion, lblMensajeMotivador,
                    btnNuevoReto, btnElegirComida, lblComida, // 🔹 Comida debajo de su botón
                    btnVolver
            );
        });

        // 📌 Agregar layout a la escena
        StackPane root = new StackPane(fondoImagen, layout);
        Scene scene = new Scene(root, 600, 600);
        stage.setScene(scene);
	}


	private Button crearBotonFuturista(String texto) {
	    Button boton = new Button(texto);
	    boton.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-text-fill: white; "
	            + "-fx-font-size: 14px; -fx-font-weight: bold; "
	            + "-fx-border-color: white; -fx-border-width: 2px; "
	            + "-fx-border-radius: 10px; -fx-padding: 10px; "
	            + "-fx-effect: dropshadow(three-pass-box, rgba(255, 255, 255, 0.3), 10, 0, 0, 4);");

	    boton.setOnMouseEntered(e -> boton.setStyle(
	            "-fx-background-color: rgba(255, 255, 255, 0.9); -fx-text-fill: black; "
	            + "-fx-font-size: 14px; -fx-font-weight: bold; "
	            + "-fx-border-color: black; -fx-border-width: 2px; "
	            + "-fx-border-radius: 10px; -fx-padding: 10px; "
	            + "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.5), 10, 0, 0, 4);"));

	    boton.setOnMouseExited(e -> boton.setStyle(
	            "-fx-background-color: rgba(0, 0, 0, 0.7); -fx-text-fill: white; "
	            + "-fx-font-size: 14px; -fx-font-weight: bold; "
	            + "-fx-border-color: white; -fx-border-width: 2px; "
	            + "-fx-border-radius: 10px; -fx-padding: 10px; "
	            + "-fx-effect: dropshadow(three-pass-box, rgba(255, 255, 255, 0.3), 10, 0, 0, 4);"));

	    return boton;
	}


	private void mostrarMensajeMotivador(String emocion) {
	    List<String> mensajes = switch (emocion) {
	        case "Feliz" -> mensajesFeliz;
	        case "Triste" -> mensajesTriste;
	        case "Motivado" -> mensajesMotivado;
	        case "Cansado" -> mensajesCansado;
	        case "Estresado" -> mensajesEstresado;
	        default -> Arrays.asList("¡Eres increíble! Sigue adelante.");
	    };
	    lblMensajeMotivador.setText(mensajes.get(random.nextInt(mensajes.size())));
	}


    private void generarReto() {
        lblReto.setText("🎯 Reto del día: " + retos.get(random.nextInt(retos.size())));
    }

    private void generarComida() {
        lblComida.setText("🍽️ Comida recomendada: " + comidas.get(random.nextInt(comidas.size())));
    }

    private void animarEntrada(Label label) {
        label.setStyle("-fx-background-color: " + coloresFondos.get(random.nextInt(coloresFondos.size())) + "; "
                + "-fx-text-fill: black; -fx-font-size: 14px; -fx-font-weight: bold; "
                + "-fx-border-color: black; -fx-border-width: 1px; "
                + "-fx-border-radius: 5px; -fx-padding: 5px;");

        FadeTransition ft = new FadeTransition(Duration.millis(500), label);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }

	public List<String> getMensajesMotivado() {
		return mensajesMotivado;
	}

	public List<String> getMensajesCansado() {
		return mensajesCansado;
	}

	public List<String> getMensajesEstresado() {
		return mensajesEstresado;
	}
}

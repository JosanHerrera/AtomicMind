package atomicmind.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;


import java.util.*;

public class AlimentacionView {

    private Stage stage;
    private String correoUsuario;

    private final Map<String, String> descripcionesDietas = new HashMap<>();
    private final Map<String, List<String>> recetasDietas = new HashMap<>();
    private final Map<String, String> ingredientesRecetas = new HashMap<>();
    private final Map<String, String> pasosRecetas = new HashMap<>();

    public AlimentacionView(Stage stage, String correoUsuario) {
        this.stage = stage;
        this.correoUsuario = correoUsuario;
        inicializarDatos();
    }

    private void inicializarDatos() {
        // 🟢 Descripciones de las 8 dietas
        descripcionesDietas.put("Dieta Mediterránea", "Basada en frutas, verduras, pescado, aceite de oliva y cereales integrales.");
        descripcionesDietas.put("Dieta Keto", "Alta en grasas y baja en carbohidratos, utilizada para perder peso y mejorar la energía.");
        descripcionesDietas.put("Dieta Vegetariana", "Elimina la carne y se enfoca en vegetales, legumbres y proteínas vegetales.");
        descripcionesDietas.put("Dieta Alta en Proteínas", "Ideal para aumentar la masa muscular y mejorar la recuperación muscular.");
        descripcionesDietas.put("Dieta Baja en Calorías", "Se centra en alimentos bajos en calorías para ayudar a la pérdida de peso.");
        descripcionesDietas.put("Dieta Detox", "Incluye jugos, frutas y verduras para limpiar el cuerpo de toxinas.");
        descripcionesDietas.put("Dieta Paleo", "Basada en alimentos no procesados como carne, pescado, frutas y verduras.");
        descripcionesDietas.put("Dieta Rica en Fibra", "Favorece la digestión y la salud intestinal con cereales, frutas y legumbres.");

        // 🟢 Recetas para cada dieta (5 recetas por dieta)
        recetasDietas.put("Dieta Mediterránea", Arrays.asList("Ensalada griega", "Salmón con espárragos", "Gazpacho", "Pisto", "Hummus casero"));
        recetasDietas.put("Dieta Keto", Arrays.asList("Huevos revueltos con jamón", "Bistec con ajo", "Ensalada de aguacate", "Pollo con queso y espinacas", "Salmón con mantequilla"));
        recetasDietas.put("Dieta Vegetariana", Arrays.asList("Ensalada de quinoa", "Berenjenas a la parmesana", "Wraps de garbanzos", "Hamburguesa de lentejas", "Tortilla de espinacas"));
        recetasDietas.put("Dieta Alta en Proteínas", Arrays.asList("Pechuga de pollo", "Huevos revueltos con pavo", "Atún con arroz", "Batido de proteínas", "Salmón con almendras"));
        recetasDietas.put("Dieta Baja en Calorías", Arrays.asList("Ensalada de lechuga", "Filete de merluza", "Manzana con yogur", "Verduras al vapor", "Pechuga a la plancha"));
        recetasDietas.put("Dieta Detox", Arrays.asList("Infusión de jengibre", "Zumo natural de naranja", "Smoothie verde detox", "Sopa de verduras", "Agua con limón y menta"));
        recetasDietas.put("Dieta Paleo", Arrays.asList("Carne a la parrilla", "Pollo asado con verduras", "Pescado al horno", "Batata asada con carne", "Ensalada de nueces y pollo"));
        recetasDietas.put("Dieta Rica en Fibra", Arrays.asList("Avena con frutos rojos", "Ensalada de garbanzos", "Manzana con almendras", "Sopa de lentejas", "Batido de frutas y chía"));

        // Dieta Mediterránea
        agregarReceta("Ensalada griega", "Tomates, Pepino, Cebolla roja, Aceitunas negras, Queso feta, Aceite de oliva, Orégano",
                "1. Corta los tomates y el pepino.\n2. Mezcla con aceitunas y queso feta.\n3. Aliña con aceite de oliva y orégano.");
        agregarReceta("Salmón con espárragos", "1 filete de salmón, Espárragos, Limón, Ajo picado, Aceite de oliva, Sal y pimienta",
                "1. Precalienta el horno a 200°C.\n2. Coloca el salmón y los espárragos en una bandeja.\n3. Agrega ajo, aceite de oliva y limón.\n4. Hornea por 15 minutos.");
        agregarReceta("Gazpacho", "Tomates maduros, Pimiento verde, Pepino, Ajo, Aceite de oliva, Vinagre, Sal",
                "1. Lava y corta todos los ingredientes.\n2. Licúa hasta obtener una mezcla homogénea.\n3. Añade aceite de oliva, vinagre y sal al gusto.\n4. Refrigera y sirve frío.");
        agregarReceta("Pisto", "Tomate, Calabacín, Pimiento rojo, Pimiento verde, Cebolla, Aceite de oliva, Sal",
                "1. Sofríe la cebolla y los pimientos en aceite.\n2. Añade el calabacín y cocina a fuego medio.\n3. Agrega el tomate y deja cocer hasta reducir.");
        agregarReceta("Hummus casero", "Garbanzos, Tahini, Limón, Ajo, Aceite de oliva, Sal, Comino",
                "1. Tritura los garbanzos con tahini, ajo y limón.\n2. Agrega aceite de oliva y sal al gusto.\n3. Sirve con pan de pita.");

        // Dieta Keto
        agregarReceta("Huevos revueltos con jamón", "Huevos, Jamón, Mantequilla, Sal, Pimienta",
                "1. Bate los huevos con sal y pimienta.\n2. Cocina en una sartén con mantequilla.\n3. Agrega el jamón troceado y cocina hasta que cuajen.");
        agregarReceta("Bistec con ajo", "Bistec de res, Ajo picado, Aceite de oliva, Sal, Pimienta",
                "1. Sazona el bistec con ajo, sal y pimienta.\n2. Cocina en una sartén con aceite hasta el punto deseado.");
        agregarReceta("Ensalada de aguacate", "Aguacate, Lechuga, Tomate, Aceite de oliva, Limón, Sal",
                "1. Corta el aguacate y el tomate.\n2. Mezcla con la lechuga.\n3. Aliña con aceite, limón y sal.");
        agregarReceta("Pollo con queso y espinacas", "Pechuga de pollo, Espinacas, Queso mozzarella, Ajo, Aceite",
                "1. Cocina las espinacas con ajo.\n2. Rellena la pechuga de pollo con las espinacas y queso.\n3. Hornea a 180°C por 20 minutos.");
        agregarReceta("Salmón con mantequilla", "Salmón, Mantequilla, Limón, Sal, Pimienta",
                "1. Derrite la mantequilla en una sartén.\n2. Cocina el salmón con sal y pimienta.\n3. Exprime limón antes de servir.");

        // Dieta Vegetariana
        agregarReceta("Ensalada de quinoa", "Quinoa, Tomate cherry, Pepino, Cebolla morada, Aceite de oliva, Limón",
                "1. Cocina la quinoa según instrucciones.\n2. Mezcla con los vegetales picados.\n3. Aliña con aceite y limón.");
        agregarReceta("Berenjenas a la parmesana", "Berenjenas, Salsa de tomate, Queso parmesano, Mozzarella, Orégano",
                "1. Corta las berenjenas en rodajas y ásalas.\n2. Alterna capas de berenjena, salsa y queso en un molde.\n3. Hornea a 180°C por 20 minutos.");
        agregarReceta("Wraps de garbanzos", "Tortillas, Garbanzos cocidos, Aguacate, Lechuga, Tomate",
                "1. Tritura los garbanzos y mezcla con aguacate.\n2. Rellena las tortillas con la mezcla y agrega lechuga y tomate.");
        agregarReceta("Hamburguesa de lentejas", "Lentejas cocidas, Pan rallado, Cebolla, Especias",
                "1. Tritura las lentejas y mezcla con los demás ingredientes.\n2. Forma hamburguesas y cocina en una sartén.");
        agregarReceta("Tortilla de espinacas", "Huevos, Espinacas, Ajo, Aceite de oliva, Sal",
                "1. Sofríe el ajo y las espinacas.\n2. Añade los huevos batidos y cocina a fuego medio.");

        // Dieta Alta en Proteínas
        agregarReceta("Pechuga de pollo", "Pechuga de pollo, Sal, Pimienta, Aceite de oliva",
                "1. Sazona la pechuga con sal y pimienta.\n2. Cocina a la plancha hasta dorar.");
        agregarReceta("Huevos revueltos con pavo", "Huevos, Pavo en tiras, Sal, Pimienta, Aceite",
                "1. Bate los huevos con sal y pimienta.\n2. Cocina en una sartén con pavo.");
        agregarReceta("Atún con arroz", "Atún en lata, Arroz integral, Cebolla, Pimiento",
                "1. Cocina el arroz según instrucciones.\n2. Sofríe la cebolla y pimiento, añade el atún y mezcla con el arroz.");
        agregarReceta("Batido de proteínas", "Proteína en polvo, Plátano, Leche de almendras, Canela",
                "1. Mezcla todos los ingredientes en una licuadora.\n2. Sirve frío y disfruta.");
        agregarReceta("Salmón con almendras", "Salmón, Almendras, Limón, Aceite, Sal, Pimienta",
                "1. Cocina el salmón en sartén con aceite.\n2. Espolvorea almendras picadas y exprime limón antes de servir.");

        // Dieta Baja en Calorías
        agregarReceta("Ensalada de lechuga", "Lechuga, Tomate, Zanahoria rallada, Aceite de oliva, Vinagre, Sal",
                "1. Lava y corta los ingredientes.\n2. Mezcla y aliña con aceite y vinagre.");
        agregarReceta("Filete de merluza", "Merluza, Limón, Ajo, Perejil, Aceite de oliva",
                "1. Sazona la merluza con ajo y perejil.\n2. Cocina a la plancha con aceite.");
        agregarReceta("Manzana con yogur", "Manzana, Yogur natural, Canela",
                "1. Corta la manzana y mezcla con yogur.\n2. Espolvorea canela por encima.");
        agregarReceta("Verduras al vapor", "Brócoli, Zanahoria, Coliflor, Sal, Aceite de oliva",
                "1. Cocina las verduras al vapor hasta que estén tiernas.\n2. Aliña con sal y aceite.");
        agregarReceta("Pechuga a la plancha", "Pechuga de pollo, Sal, Pimienta, Aceite",
                "1. Sazona la pechuga y cocina a la plancha hasta dorar.");
        // Dieta Paleo
        agregarReceta("Carne a la parrilla", "Carne de res, Sal, Pimienta, Aceite de oliva",
                "1. Sazona la carne con sal y pimienta.\n2. Cocina en la parrilla hasta el punto deseado.");
        agregarReceta("Pollo asado con verduras", "Pollo, Zanahorias, Papas, Aceite, Especias",
                "1. Coloca el pollo y las verduras en una bandeja.\n2. Agrega aceite y especias.\n3. Hornea a 200°C por 40 minutos.");
        agregarReceta("Pescado al horno", "Filete de pescado, Limón, Ajo, Perejil, Aceite",
                "1. Sazona el pescado con ajo y perejil.\n2. Hornea a 180°C por 15 minutos.");
        agregarReceta("Batata asada con carne", "Batata, Carne molida, Cebolla, Pimiento, Especias",
                "1. Asa la batata en el horno.\n2. Cocina la carne con cebolla y pimiento.\n3. Rellena la batata con la carne cocida.");
        agregarReceta("Ensalada de nueces y pollo", "Pollo, Nueces, Lechuga, Tomate, Aceite",
                "1. Cocina y trocea el pollo.\n2. Mezcla con los demás ingredientes y aliña con aceite.");

        // Dieta Rica en Fibras
        agregarReceta("Avena con frutos rojos", "Avena, Leche, Frutos rojos, Miel",
                "1. Cocina la avena con leche.\n2. Añade frutos rojos y endulza con miel.");
        agregarReceta("Ensalada de garbanzos", "Garbanzos cocidos, Tomate, Cebolla, Aceite de oliva, Limón",
                "1. Mezcla los garbanzos con tomate y cebolla picados.\n2. Aliña con aceite y limón.");
        agregarReceta("Manzana con almendras", "Manzana, Almendras, Miel",
                "1. Corta la manzana en rodajas.\n2. Espolvorea almendras picadas y añade miel.");
        agregarReceta("Sopa de lentejas", "Lentejas, Zanahoria, Cebolla, Apio, Ajo, Aceite",
                "1. Sofríe la cebolla, zanahoria y apio.\n2. Agrega las lentejas y cocina hasta que estén tiernas.");
        agregarReceta("Batido de frutas y chía", "Plátano, Fresas, Leche, Semillas de chía",
                "1. Licúa todas las frutas con la leche.\n2. Añade semillas de chía y mezcla bien.");

        // Dieta Detox
        agregarReceta("Infusión de jengibre", "Jengibre, Agua, Limón, Miel",
                "1. Hierve el agua y añade jengibre rallado.\n2. Deja reposar y añade limón y miel.");
        agregarReceta("Zumo natural de naranja", "Naranjas frescas",
                "1. Exprime las naranjas y sirve el jugo recién hecho.");
        agregarReceta("Smoothie verde detox", "Espinacas, Pepino, Manzana, Agua, Limón",
                "1. Licúa todos los ingredientes hasta obtener una mezcla homogénea.\n2. Sirve frío.");
        agregarReceta("Sopa de verduras", "Zanahoria, Calabacín, Puerro, Apio, Agua, Sal",
                "1. Corta todas las verduras en trozos.\n2. Cocina en agua con sal hasta que estén tiernas.");
        agregarReceta("Agua con limón y menta", "Agua, Limón, Hojas de menta",
                "1. Exprime un limón en un vaso de agua.\n2. Añade hojas de menta y deja reposar.");


        // Otras dietas siguen la misma estructura con 5 recetas cada una...


    }

    private void agregarReceta(String nombre, String ingredientes, String pasos) {
        ingredientesRecetas.put(nombre, "Ingredientes:\n" + ingredientes);
        pasosRecetas.put(nombre, "Preparación:\n" + pasos);
    }

    void mostrarPantalla() {
        stage.setTitle("Planes de Alimentación");

        // 📌 Imagen de fondo adaptable
        ImageView fondoImagen = new ImageView(new Image(getClass().getResourceAsStream("/atomicmind/views/fondoalimentacion.jpg")));
        fondoImagen.setPreserveRatio(false);
        fondoImagen.fitWidthProperty().bind(stage.widthProperty());
        fondoImagen.fitHeightProperty().bind(stage.heightProperty());
        fondoImagen.setOpacity(1);

        ListView<String> listaDietas = new ListView<>();
        listaDietas.getItems().addAll(descripcionesDietas.keySet());
        listaDietas.setStyle("-fx-font-size: 16px; -fx-background-color: rgba(0, 0, 0, 0.5); "
                            + "-fx-border-color: #aaaaaa; -fx-border-width: 1.5px; -fx-border-radius: 5px;");

        // 🔥 Ajustar la altura exacta para que solo muestre las dietas sin scroll
        listaDietas.setPrefHeight(descripcionesDietas.size() * 35);
        listaDietas.setMaxHeight(descripcionesDietas.size() * 35); // Evita que aparezca el scroll

        // 🟢 Efecto Hover futurista pero sutil
        listaDietas.setCellFactory(lv -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    setStyle("-fx-padding: 10px; -fx-background-color: rgba(255, 255, 255, 0.1); -fx-text-fill: white;");
                    
                    setOnMouseEntered(e -> setStyle("-fx-padding: 10px; -fx-background-color: rgba(255, 255, 255, 0.3); -fx-text-fill: white;"));
                    setOnMouseExited(e -> setStyle("-fx-padding: 10px; -fx-background-color: rgba(255, 255, 255, 0.1); -fx-text-fill: white;"));
                }
            }
        });

        listaDietas.setOnMouseClicked(e -> {
            String dietaSeleccionada = listaDietas.getSelectionModel().getSelectedItem();
            if (dietaSeleccionada != null) {
                mostrarDetallesDieta(dietaSeleccionada);
            }
        });

     // 📌 Botón "Volver" con un azul normal y efecto hover
        Button btnVolver = new Button("🔙 Volver al Menú");
        btnVolver.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; "
                + "-fx-font-size: 14px; -fx-font-weight: bold; "
                + "-fx-border-color: #1976D2; -fx-border-width: 2px; "
                + "-fx-border-radius: 8px; -fx-padding: 10px;");

        // 📌 Efecto hover para resaltar al pasar el ratón
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


        VBox layout = new VBox(15);
        layout.getChildren().addAll(listaDietas, btnVolver);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(15));

        // 📌 Contenedor principal con la imagen de fondo
        StackPane root = new StackPane();
        root.getChildren().addAll(fondoImagen, layout);

        Scene scene = new Scene(root, 600, 600);
        stage.setScene(scene);
    }


    private void mostrarDetallesDieta(String dieta) {
        stage.setTitle(dieta);

        // 📌 Imagen de fondo adaptable
        ImageView fondoImagen = new ImageView(new Image(getClass().getResourceAsStream("/atomicmind/views/fondoalimentacion.jpg")));
        fondoImagen.setPreserveRatio(false);
        fondoImagen.fitWidthProperty().bind(stage.widthProperty());
        fondoImagen.fitHeightProperty().bind(stage.heightProperty());
        fondoImagen.setOpacity(1);

     // 📌 Descripción con negrita y en negro
        Label lblDescripcion = new Label(descripcionesDietas.get(dieta));
        lblDescripcion.setWrapText(true);
        lblDescripcion.setStyle("-fx-font-size: 16px; -fx-text-fill: black; -fx-font-weight: bold;");


        ListView<String> listaRecetas = new ListView<>();
        listaRecetas.getItems().addAll(recetasDietas.get(dieta));
        listaRecetas.setStyle("-fx-font-size: 14px; -fx-background-color: rgba(0, 0, 0, 0.5); "
                            + "-fx-border-color: #aaaaaa; -fx-border-width: 1.5px; -fx-border-radius: 5px;");
        
        // 🔥 Ajustar la altura exacta para que solo muestre las recetas sin scroll
        listaRecetas.setPrefHeight(recetasDietas.get(dieta).size() * 35);
        listaRecetas.setMaxHeight(recetasDietas.get(dieta).size() * 35); // Evita el scroll

        // 🟢 Efecto Hover
        listaRecetas.setCellFactory(lv -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    setStyle("-fx-padding: 10px; -fx-background-color: rgba(255, 255, 255, 0.1); -fx-text-fill: white;");
                    
                    setOnMouseEntered(e -> setStyle("-fx-padding: 10px; -fx-background-color: rgba(255, 255, 255, 0.3); -fx-text-fill: white;"));
                    setOnMouseExited(e -> setStyle("-fx-padding: 10px; -fx-background-color: rgba(255, 255, 255, 0.1); -fx-text-fill: white;"));
                }
            }
        });

        listaRecetas.setOnMouseClicked(e -> {
            String recetaSeleccionada = listaRecetas.getSelectionModel().getSelectedItem();
            if (recetaSeleccionada != null) {
                mostrarReceta(recetaSeleccionada);
            }
        });

        Button btnVolver = new Button("Volver a Dietas");
        btnVolver.setStyle("-fx-background-color: rgba(255, 255, 255, 0.3); -fx-text-fill: white; -fx-font-size: 14px; "
                         + "-fx-border-radius: 5px; -fx-padding: 8px; -fx-font-weight: bold; "
                         + "-fx-transition: all 0.3s;");

        // 🟢 Efecto hover con blanco más brillante
        btnVolver.setOnMouseEntered(e -> btnVolver.setStyle("-fx-background-color: rgba(255, 255, 255, 0.6); -fx-text-fill: white; -fx-font-size: 14px; "
                                                          + "-fx-border-radius: 5px; -fx-padding: 8px; -fx-font-weight: bold;"));
        btnVolver.setOnMouseExited(e -> btnVolver.setStyle("-fx-background-color: rgba(255, 255, 255, 0.3); -fx-text-fill: white; -fx-font-size: 14px; "
                                                          + "-fx-border-radius: 5px; -fx-padding: 8px; -fx-font-weight: bold;"));

        btnVolver.setOnAction(e -> mostrarPantalla());

        VBox layout = new VBox(15);
        layout.getChildren().addAll(lblDescripcion, listaRecetas, btnVolver);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(15));

        // 📌 Contenedor principal con la imagen de fondo
        StackPane root = new StackPane();
        root.getChildren().addAll(fondoImagen, layout);

        Scene scene = new Scene(root, 600, 600);
        stage.setScene(scene);
    }


    private void mostrarReceta(String receta) {
        stage.setTitle(receta);

        // 📌 Imagen de fondo adaptable
        ImageView fondoImagen = new ImageView(new Image(getClass().getResourceAsStream("/atomicmind/views/fondoalimentacion.jpg")));
        fondoImagen.setPreserveRatio(false);
        fondoImagen.fitWidthProperty().bind(stage.widthProperty());
        fondoImagen.fitHeightProperty().bind(stage.heightProperty());
        fondoImagen.setOpacity(1); // 🔥 Mantiene la coherencia con las otras pantallas

        // 🔥 Capa de oscurecimiento semitransparente
        StackPane overlay = new StackPane();
        overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4);");

        Label lblIngredientes = new Label("Ingredientes:");
        lblIngredientes.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-font-weight: bold;");

        VBox listaIngredientes = new VBox(10);
        listaIngredientes.setAlignment(Pos.CENTER_LEFT); // 🔥 Asegura que los checkboxes se mantengan en su sitio
        listaIngredientes.setMaxWidth(300); // 🔥 Evita que se expanda

        // Creamos checkboxes con efecto hover azul futurista
        String[] ingredientesArray = ingredientesRecetas.get(receta).replace("Ingredientes:\n", "").split(", ");
        for (String ingrediente : ingredientesArray) {
            CheckBox checkBox = new CheckBox(ingrediente);
            checkBox.setStyle("-fx-font-size: 14px; -fx-text-fill: white; -fx-background-color: rgba(0, 0, 0, 0.5); "
                            + "-fx-border-color: #00aaff; -fx-border-width: 1.5px; -fx-border-radius: 5px; -fx-padding: 5px;");
            
            // 🟢 Efecto Hover: Azul Futurista Suave
            checkBox.setOnMouseEntered(e -> checkBox.setStyle("-fx-font-size: 14px; -fx-text-fill: white; "
                                                            + "-fx-background-color: rgba(0, 170, 255, 0.3); " 
                                                            + "-fx-border-color: #00aaff; -fx-border-width: 1.5px; "
                                                            + "-fx-border-radius: 5px; -fx-padding: 5px;"));
            
            checkBox.setOnMouseExited(e -> checkBox.setStyle("-fx-font-size: 14px; -fx-text-fill: white; "
                                                           + "-fx-background-color: rgba(0, 0, 0, 0.5); "
                                                           + "-fx-border-color: #00aaff; -fx-border-width: 1.5px; "
                                                           + "-fx-border-radius: 5px; -fx-padding: 5px;"));

            listaIngredientes.getChildren().add(checkBox);
        }

        Label lblPasos = new Label(pasosRecetas.get(receta));
        lblPasos.setWrapText(true);
        lblPasos.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");
        lblPasos.setMaxWidth(300); // 🔥 Evita que se expanda y se mueva en pantalla completa

        // 🔥 Botón de "Volver a Recetas" con mismo hover futurista
        Button btnVolver = new Button("Volver a Recetas");
        btnVolver.setStyle("-fx-background-color: rgba(255, 255, 255, 0.3); -fx-text-fill: white; -fx-font-size: 14px; "
                         + "-fx-border-radius: 5px; -fx-padding: 8px; -fx-font-weight: bold;");
        
        btnVolver.setOnMouseEntered(e -> btnVolver.setStyle("-fx-background-color: rgba(255, 255, 255, 0.6); -fx-text-fill: white; -fx-font-size: 14px; "
                                                          + "-fx-border-radius: 5px; -fx-padding: 8px; -fx-font-weight: bold;"));
        btnVolver.setOnMouseExited(e -> btnVolver.setStyle("-fx-background-color: rgba(255, 255, 255, 0.3); -fx-text-fill: white; -fx-font-size: 14px; "
                                                          + "-fx-border-radius: 5px; -fx-padding: 8px; -fx-font-weight: bold;"));

        btnVolver.setOnAction(e -> mostrarPantalla());

        VBox layout = new VBox(15);
        layout.getChildren().addAll(lblIngredientes, listaIngredientes, lblPasos, btnVolver);
        layout.setAlignment(Pos.CENTER); // 🔥 Mantiene todo centrado siempre
        layout.setPadding(new Insets(15));
        layout.setMaxWidth(350); // 🔥 Evita que se expanda cuando la pantalla se agranda

        // 📌 Contenedor principal con fondo uniforme y semitransparente como en otras pantallas
        StackPane root = new StackPane();
        root.getChildren().addAll(fondoImagen, overlay, layout); // 🔥 Agregamos la capa de oscurecimiento

        Scene scene = new Scene(root, 600, 600);
        stage.setScene(scene);
    }

}

import javafx.application.Application;
import javafx.stage.Stage;

public class TestJavaFx extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("¡JavaFX funciona!");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

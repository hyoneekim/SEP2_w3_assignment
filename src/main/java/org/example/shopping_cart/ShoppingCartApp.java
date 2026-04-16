package org.example.shopping_cart;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class ShoppingCartApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Try absolute classpath path first
        URL fxmlUrl = getClass().getResource("/shopping-cart-view.fxml");

        // Fallback: relative to class package
        if (fxmlUrl == null) {
            fxmlUrl = getClass().getResource("shopping-cart-view.fxml");
        }

        if (fxmlUrl == null) {
            throw new IllegalArgumentException("Unable to load shopping-cart-view.fxml");

        }

        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        Parent root = loader.load(); // Parent instead of VBox — works for any root type

        Scene scene = new Scene(root, 360, 550);

        // CSS — same directory as FXML
        URL cssUrl = getClass().getResource("/style.css");
        if (cssUrl == null) cssUrl = getClass().getResource("style.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        }

        primaryStage.setTitle("Shopping Cart App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
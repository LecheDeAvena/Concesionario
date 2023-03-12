package ch.makery.address;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Stage primaryStage;

	public MainApp() {
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Login");

		initRootLayout();
	}

    /**
     * Este método lanza la pantalla
     */
	public void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			// Define la pantalla
			loader.setLocation(MainApp.class.getResource("view/login.fxml"));
			Scene scene = new Scene(loader.load());
			// Mostrar y cargar la pantalla
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    /**
     * Devuelve la primera pantalla
     * @return
     */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
package com.wapp.gui;

import java.io.IOException;
//import java.util.Optional;

import javafx.application.Application;
//import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.control.Alert;
//import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.fxml.FXMLLoader;

public class Main extends Application
{
	private static Scene scene;
	
	@Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("/fxml/logPanel"));
        stage.getIcons().add(new Image("/img/logo-transparent.png"));
        stage.setTitle("WineryApp");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(e ->{
			e.consume();
			closeApplication(stage);
		});
        stage.setResizable(false);
        }

	private void closeApplication(Stage stage) {
		/* AFTER ALL TESTS
		Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure want to exit?", ButtonType.YES, ButtonType.NO);
		Optional<ButtonType> btnType = alert.showAndWait();
		if (btnType.get() == ButtonType.YES) {
			//Platform.exit();
			System.exit(0);
		}
		*/
		
		//FOR NOW
		System.exit(0);
	}
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }    
}
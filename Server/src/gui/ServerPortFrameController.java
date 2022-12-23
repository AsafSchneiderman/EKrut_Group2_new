package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import controller.EchoServer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ServerPortFrameController implements Initializable {

	@FXML
	private Button connectServerButton;

	@FXML
	private TextArea textAreaConsole;

	@FXML
	private TextField textFielsPort;
	
	public static TextArea consoleArea;

	@FXML
	void connectServer(ActionEvent event) {

		int port = 0; // Port to listen on

		try {
			port = Integer.parseInt(textFielsPort.getText()); // Get port from command line
		} catch (Throwable t) {
			port = EchoServer.DEFAULT_PORT; // Set port to 5555
		}

		EchoServer sv = new EchoServer(port);

		try {
			sv.listen(); // Start listening for connections
		} catch (Exception ex) {
			appendToConsole("ERROR - Could not listen for clients!"+port);
			ex.printStackTrace();
		}
		db.mysqlConnection.connectionDb();
	}

	public void start(Stage primaryStage) throws IOException {
		primaryStage.setTitle("Ekrut");
		Parent root = FXMLLoader.load(getClass().getResource("/gui/ServerUiMenu.fxml"));
		Scene home = new Scene(root);
		primaryStage.setScene(home);
		primaryStage.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		consoleArea = textAreaConsole;
	}

	public void appendToConsole(String str) {
		consoleArea.appendText(str + "\n");
		
	}

}

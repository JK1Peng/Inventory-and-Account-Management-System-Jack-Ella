import java.io.PrintWriter;
import java.net.Socket;

import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public abstract class SceneBasic {
	private Stage stage;
	private Scene scene;
	protected VBox root = new VBox();

	public SceneBasic(String titleText) {
        Label message = new Label(titleText);
        message.setFont(new Font(40));
        root.getChildren().addAll(message);
        root.setAlignment(Pos.TOP_CENTER);
        scene = new Scene(root, 450, 250);
	}
	
	public Scene getScene() {
        return scene;
	}
	
	public void logout() {
		try {
            System.out.println("Quitting");
        	Socket connection = SceneManager.getSocket();
	    	PrintWriter outgoing;   // Stream for sending data.
			outgoing = new PrintWriter( connection.getOutputStream() );
			outgoing.println("QUIT");
			outgoing.flush();
			connection.close();
	    	SceneManager.setLoginScene();
        }
        catch (Exception e) {
            System.out.println("Error:  " + e);
        }
	}
}

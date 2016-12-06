package server;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	RootControll rc = new RootControll();
	@Override
	public void start(Stage st) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/server.fxml"));
			
			Scene scene = new Scene(root);		
			
			st.setScene(scene);
			st.setResizable(false);
			st.setTitle("캐치마인드 서버");
			st.setOnCloseRequest(event -> rc.stopServer());
			st.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

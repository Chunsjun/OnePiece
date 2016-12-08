package application;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
	static StackPane stackPane = new StackPane();
	static Scene loginScene = new Scene(stackPane, 500, 500);
	static Stage stage;
	static String nick;

	private static SqlSessionFactory sqlSessionFactory;
	static {
		try {
			// 마이바티스 환경설정 XML 파일경로
			String resource = "resources/config-mybatis.xml";
			Reader reader = Resources.getResourceAsReader(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void start(Stage stage) throws IOException {

		StackPane stackPane = new StackPane();
		Scene loginScene = new Scene(stackPane, 500, 500);

		Parent root = FXMLLoader.load(getClass().getResource("/fxml/client.fxml"));
		Scene clientScene = new Scene(root);

		try {
			Label idLabel = new Label("ID");
			Label pwLable = new Label("PASSWORD");
			Label massgeLabel = new Label();

			TextField idF = new TextField();
			PasswordField pwF = new PasswordField();

			Button loginBtn = new Button("Login");
			loginBtn.prefHeightProperty().bind(idF.heightProperty().add(pwF.heightProperty()));
			loginBtn.setOnAction(e -> {
				SqlSession sqlSession = sqlSessionFactory.openSession();
				massgeLabel.setStyle("-fx-text-fill: red;");

				String id = idF.getText();
				String pw = pwF.getText();
				
				nick = idF.getText();

				HashMap<String, String> map = new HashMap<String, String>();
				map.put("id", id);
				map.put("pw", pw);

				try {
					String userInfo = sqlSession.selectOne("org.mybatis.persistence.mapper.select", map);

					if (id.equals("")) {
						massgeLabel.setText("Please enter your ID");
					} else if (pw.equals("")) {
						massgeLabel.setText("Please enter your PASSWORD");
					} else if (userInfo == null) {
						massgeLabel.setText("Undefind your ID or PASSWORD");
					} else {
						massgeLabel.setStyle("-fx-text-fill: green;");
						massgeLabel.setText("Welcome");
						stage.setScene(clientScene);
					}
				} catch (Exception exception) {
					exception.printStackTrace();
				} finally {
					sqlSession.close();
				}
			});
			Join j = new Join();
			Button joinBtn = new Button("Join");
			joinBtn.setOnAction(e -> {
				stage.setScene(j.joinScene);
			});
			
			Button findBtn = new Button("Find ID/PW");
			findBtn.setOnAction(e ->{
				Parent root2 = null;
				try {
					root2 = FXMLLoader.load(getClass().getResource("/fxml/findidpw.fxml"));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				Scene scene2 = new Scene(root2);
				Stage findStage = new Stage(StageStyle.UTILITY);
				findStage.initModality(Modality.WINDOW_MODAL);
				findStage.setTitle("FIND ID/PW");
				findStage.setScene(scene2);
				findStage.show();
			});

			GridPane gridPane = new GridPane();
			gridPane.addRow(0, idLabel, idF);
			gridPane.addRow(1, pwLable, pwF);
			gridPane.addRow(3, joinBtn, findBtn);
			gridPane.add(loginBtn, 2, 0, 1, 2);
			gridPane.add(massgeLabel, 0, 2, 3, 1);
			gridPane.setAlignment(Pos.CENTER);

			stackPane.getChildren().add(gridPane);

			stage.setResizable(false);
			stage.setScene(loginScene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.stage = stage;
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
	
	public Main() throws IOException{
	}
	
	public String getNick(){
		return nick;
	}
}

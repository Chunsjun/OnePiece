package application;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Join {
	StackPane stackPane = new StackPane();
	Scene joinScene = new Scene(stackPane, 500, 500);
	static Stage stage;

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

	public Join() throws IOException {
		Label idLabel = new Label("ID");
		Label pwLable = new Label("PASSWORD");
		Label emailLable = new Label("EMAIL");
		Label massgeLabel = new Label();

		TextField idF = new TextField();
		PasswordField pwF = new PasswordField();
		TextField emailF = new TextField();

		Button joinBtn = new Button("Join");
		Button cancelBtn = new Button("Cancel");

		joinBtn.prefHeightProperty().bind(idF.heightProperty()
				.add(pwF.heightProperty().add(emailF.heightProperty())));
		cancelBtn.prefHeightProperty().bind(idF.heightProperty()
				.add(pwF.heightProperty().add(emailF.heightProperty())));
		
		GridPane gridPane = new GridPane();
		gridPane.addRow(0, idLabel, idF);
		gridPane.addRow(1, pwLable, pwF);
		gridPane.addRow(2, emailLable, emailF);
		gridPane.add(joinBtn, 3, 0, 1, 2);
		gridPane.add(cancelBtn, 4, 0, 2, 2);
		gridPane.add(massgeLabel, 0, 3, 3, 1);
		gridPane.setAlignment(Pos.CENTER);

		stackPane.getChildren().add(gridPane);
		
		cancelBtn.setOnAction(e->{
			try {
				Main main = new Main();
				main.start(main.stage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		
		joinBtn.setOnAction(e->{
			SqlSession sqlSession = sqlSessionFactory.openSession();
			massgeLabel.setStyle("-fx-text-fill: red;");
			String id = idF.getText();
			String pw = pwF.getText();
			String email = emailF.getText();
			
			if (id.equals("")) {
				massgeLabel.setText("Please enter ID");
			} else if (pw.equals("")) {
				massgeLabel.setText("Please enter PASSWORD");
			} else if(email.equals("")){
				massgeLabel.setText("Please enter NAME");
			} else {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("id", id);
				map.put("pw", pw);
				map.put("email", email);
				try {
					String userInfo = sqlSession.selectOne("org.mybatis.persistence.mapper.checkId", id);
					if(userInfo == null){
						sqlSession.insert("org.mybatis.persistence.mapper.insert", map);
						sqlSession.commit();
						massgeLabel.setStyle("-fx-text-fill: green;");
						massgeLabel.setText("Join Complete!!");
					}else{
						massgeLabel.setText("Already exist ID");
					}
				}catch(Exception e1){
					e1.printStackTrace();
				} finally {
					sqlSession.close();
				}
			}
		});
}}

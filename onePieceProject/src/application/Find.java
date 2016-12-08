package application;

import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Find implements Initializable{
	
	@FXML
	private Button findPw;
	@FXML
	private Button findId;
	@FXML
	private TextField findPwId;
	@FXML
	private TextField findIdEmail;
	@FXML
	private TextField findPwEmail;
	@FXML
	private Label pwF;
	@FXML
	private Label idF;
	
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
	
	public void findPw(){
		SqlSession sqlSession = sqlSessionFactory.openSession();
		if(findPwId.getText().equals("")){
			pwF.setStyle("-fx-text-fill: red;");
			pwF.setText("Pleas Enter Your ID");
			System.out.println("아이디없음");
		}else if(findPwEmail.getText().equals("")){
			System.out.println("이메일 없음");
			pwF.setStyle("-fx-text-fill: red;");
			pwF.setText("Pleas Enter Your Email");
		}else{
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("id", findPwId.getText());
			map.put("email", findPwEmail.getText());
			try {
				String str = sqlSession.selectOne("org.mybatis.persistence.mapper.findPw", map);
				if(str == null){
					pwF.setStyle("-fx-text-fill: red;");
					pwF.setText("It does not exist");
				}else{
					pwF.setStyle("-fx-text-fill: green;");
					pwF.setText("PASSWORD :: "+str);
				}
			}catch(Exception e1){
				e1.printStackTrace();
			} finally {
				sqlSession.close();
			}
		}
	}
	
	public void findId(){
		System.out.println(findIdEmail.getText());
		SqlSession sqlSession = sqlSessionFactory.openSession();
		if(findIdEmail.getText().equals("")){
			idF.setStyle("-fx-text-fill: red;");
			idF.setText("Please Enter your Email");
			System.out.println("이메일 없음");
		}else{
			String email = findIdEmail.getText();
			try {
				String str = sqlSession.selectOne("org.mybatis.persistence.mapper.findId", email);
				if(str == null){
					idF.setStyle("-fx-text-fill: red;");
					idF.setText("It does not exist");
				}else{
					idF.setStyle("-fx-text-fill: green;");
					idF.setText("ID :: "+str);
				}
			}catch(Exception e1){
				e1.printStackTrace();
			} finally {
				sqlSession.close();
			}
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

}

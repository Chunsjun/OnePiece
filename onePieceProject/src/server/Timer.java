package server;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Timer implements Initializable{
	
	@FXML
	private Label timer;
	@FXML
	private Button button;
	
	private static final Integer STARTTIME = 30;
	private Timeline timeline;
	private IntegerProperty timeSeconds = new SimpleIntegerProperty(STARTTIME);
	
	public void start(ActionEvent e) throws Exception{
		System.out.println(timer.getText());
		if (timeline != null) {
            timeline.stop();
        }
        timeSeconds.set(STARTTIME);
        timeline = new Timeline();
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(STARTTIME+1),
                new KeyValue(timeSeconds, 0)));
        timeline.playFromStart();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		timer.textProperty().bind(timeSeconds.asString());
		timer.setTextFill(Color.RED);
		timer.setStyle("-fx-font-size: 4em;");
	}

}

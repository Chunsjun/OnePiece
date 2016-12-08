package javaFX;
 
import java.net.URL;
import java.util.ResourceBundle;
 
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
 
public class RootController implements Initializable {
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label lblWorkDone;
    @FXML
    private Label lblResult;
    @FXML
    private Button btnStart;
    @FXML
    private Button btnStop;
 
    private TimeService timeService;
 
    @Override
    public void initialize(URL loc, ResourceBundle res) {
        btnStart.setOnAction(event -> handleBtnStart(event));
        btnStop.setOnAction(event -> handleBtnStop(event));
        timeService = new TimeService();
        timeService.start();
    }
 
    class TimeService extends Service<Integer> {
        @Override
        protected Task<Integer> createTask() {
            Task<Integer> task = new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    int result = 0;
 
                    for (int i = 1; i <= 100; i++) {
                        if (isCancelled()) {
                            break;
                        }
                        result += i;
                        updateProgress(i, 100);
                        updateMessage(String.valueOf(i));
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            if (isCancelled()) {
                                break;
                            }
                        }
                    }
                    return result;
                }
            };
 
            progressBar.progressProperty().bind(task.progressProperty());
            lblWorkDone.textProperty().bind(task.messageProperty());
 
            return task;
        }
 
        @Override
        protected void cancelled() {
            lblResult.setText("취소됨");
        }
 
        @Override
        protected void failed() {
            lblResult.setText("실패");
        }
 
        @Override
        protected void succeeded() {
            lblResult.setText(String.valueOf(getValue()));
        }
        
        
    }
 
    public void handleBtnStart(ActionEvent e) {
        timeService.start();
        lblResult.setText("");
    }
 
    public void handleBtnStop(ActionEvent e) {
        timeService.cancel();
    }
 
}

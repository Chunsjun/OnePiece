package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class GameGUI extends Application {
    Parent root;
    ArrayList<ImageView> cels = new ArrayList<>();
    @Override
    public void start(Stage stage) throws Exception {
        root = FXMLLoader.load(getClass().getResource("SceneFXML/menuScene.fxml"));
        Scene scene = new Scene(root, 610, 625);
        stage.setMaxHeight(625);
        stage.setMaxWidth(610);
        stage.setTitle("TiX Tac TOe");
        stage.setScene(scene);
        stage.show();

        Button newGameButton = (Button) scene.lookup("#newGameButton");
        Button exitButton = (Button) scene.lookup("#exitButton");
        Button $1mode = (Button) scene.lookup("#spbutton");
        Button $2mode = (Button) scene.lookup("#mpbutton");

        // define behaviours for buttons
        exitButton.setOnAction(
                event -> {
                    stage.close();
                }
        );

        newGameButton.setOnAction(
                event-> {
                        $1mode.setVisible(true);
                        $2mode.setVisible(true);
                        newGameButton.setVisible(false);
                    }
        );

        $1mode.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    root = FXMLLoader.load(getClass().getResource("SceneFXML/gameScene.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Scene scene = new Scene(root, 610, 625);
                stage.setTitle("Tic Tac Toe");
                stage.setMaxHeight(625);
                stage.setMaxWidth(610);
                stage.setScene(scene);
                // Gaming part
                GameView view = new GameView();
                BotGamer bot = new BotGamer("novice");
                GameModel model = new GameModel(1);
                GameController controller = new GameController(model, view, bot);
                BotController botController = new BotController(bot, model, controller);
                controller.setPlayer((int) (Math.random() * 2));
                Text msg = (Text) scene.lookup("#toast");
                msg.setText(view.displayPlayerTurn(controller.getPlayer(), controller.getMode()));

                if (controller.getPlayer() == 1) {
                    Pair x = botController.nextMove(controller.getPlayer(), controller.getConfiguration());
                    String id = "#iv" + String.valueOf(x.getX()) + String.valueOf(x.getY());
                    ImageView cell = (ImageView) scene.lookup(id);
                    String url = "sample/Graphics/".concat(String.valueOf(controller.getActualPlayersChar())).concat(".png");
                    cell.setImage(new Image(url));
                    controller.setCell((int) id.charAt(3) - '0', (int) id.charAt(4) - '0', controller.getActualPlayersChar());
                    controller.changeTurn();
                    msg.setText(view.displayPlayerTurn(controller.getPlayer(), controller.getMode()));
                }

                for (int i = 0; i < 3; ++i) {
                    for (int j = 0; j < 3; ++j) {
                        final String id = "#iv".concat(String.valueOf(i)).concat(String.valueOf(j));
                        ImageView img = (ImageView) scene.lookup(id);
                        img.addEventHandler(MouseEvent.MOUSE_CLICKED,
                                eventSon -> {
                                    if (img.getImage() == null && controller.continueGame() == 0) {
                                        String url = "sample/Graphics/".concat(String.valueOf(controller.getActualPlayersChar())).concat(".png");
                                        // frontend
                                        img.setImage(new Image(url));
                                        // backend
                                        controller.setCell((int) id.charAt(3) - '0', (int) id.charAt(4) - '0', controller.getActualPlayersChar());
                                        if (controller.continueGame() == 0) {
                                            controller.changeTurn();
                                            msg.setText(view.displayPlayerTurn(controller.getPlayer(), controller.getMode()));
                                            // CPU's move
                                            if (controller.getPlayer() == 1) {
                                                Pair x = botController.nextMove(1, controller.getConfiguration());
                                                ImageView cell = (ImageView) scene.lookup("#iv" + String.valueOf(x.getX()) + String.valueOf(x.getY()));
                                                url = "sample/Graphics/";
                                                url = url.concat(String.valueOf(controller.getActualPlayersChar()));
                                                url = url.concat(".png");
                                                cell.setImage(new Image(url));
                                                controller.setCell(x.getX(),x.getY(), controller.getActualPlayersChar());
                                                if (controller.continueGame() == 0) {
                                                    controller.changeTurn();
                                                    msg.setText(view.displayPlayerTurn(controller.getPlayer(), controller.getMode()));
                                                } else {
                                                    msg.setText(view.displayWinner(controller.continueGame(), controller.getPlayer(), controller.getMode()));
                                                }
                                            } else {
                                                msg.setText(view.displayWinner(controller.continueGame(), controller.getPlayer(), controller.getMode()));
                                            }
                                            eventSon.consume();
                                        }
                                    }
                                });
                        }
                    }
                }
        });

        $2mode.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    root = FXMLLoader.load(getClass().getResource("SceneFXML/gameScene.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Scene scene = new Scene(root, 610, 625);
                stage.setTitle("Tic Tac Toe");
                stage.setMaxHeight(625);
                stage.setMaxWidth(610);
                stage.setScene(scene);

                GameView view = new GameView();
                GameController controller = new GameController(new GameModel(2), view);
                controller.setPlayer((int) Math.random() * 2);
                controller.setCharacters();
                Text msg = (Text) scene.lookup("#toast");
                msg.setText(view.displayPlayerTurn(controller.getPlayer(), controller.getMode()));
                // ImageView Behaviour
                for (int i = 0; i < 3; ++i) {
                    for (int j = 0; j < 3; ++j) {
                        final String id = "#iv".concat(String.valueOf(i)).concat(String.valueOf(j));
                        ImageView img = (ImageView) scene.lookup(id);
                        img.addEventHandler(MouseEvent.MOUSE_CLICKED,
                                event2 -> {
                                    if (img.getImage() == null && controller.continueGame() == 0) {
                                        //frontend
                                        String url = "sample/Graphics/";
                                        url = url.concat(String.valueOf(controller.getActualPlayersChar()));
                                        url = url.concat(".png");
                                        img.setImage(new Image(url));
                                        // backend
                                        controller.setCell((int) id.charAt(3) - '0', (int) id.charAt(4) - '0', controller.getActualPlayersChar());
                                        if (controller.continueGame() == 0) {
                                            controller.changeTurn();
                                            msg.setText(view.displayPlayerTurn(controller.getPlayer(), controller.getMode()));
                                        } else {
                                            msg.setText(view.displayWinner(controller.continueGame(), controller.getPlayer(), controller.getMode()));
                                        }
                                        event.consume();
                                    }
                                });
                        }
                    }
                }
        });
    }

    public static void main(String[] args){
        launch(args);
    }
}

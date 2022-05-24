package sample;

public class GameController {
    private GameModel model;
    private GameView view;
    private int actualPlayer;

    public GameController(GameModel model, GameView view, BotGamer bot) {
        this.model = model;
        this.view = view;
    }
    public GameController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;
    }
    public char getActualPlayersChar(){
        return this.model.getCharacter()[actualPlayer];
    }
    public void setCell(int x,int y,char c){
        model.setCell(x, y, c);
    }

    public void setPlayer(int player){
       actualPlayer = player;
    }
    public void setCharacters(){
        model.changeCaracters();
    }

    public char[][] getConfiguration(){
        return model.getConfig();
    }
    public int getPlayer(){
        return actualPlayer;
    }

    public void changeTurn(){
       actualPlayer = 1-actualPlayer;
    }
    public int getMode(){
        return model.getMode();
    }


    public int continueGame(){
        // check if actual player hasn't lost
        // check horizontal possibilities
        int sum ;
        for (int i=0;i<3;++i){
            sum = 0;
            for (int j=0;j<3;++j)
                if (model.getConfig()[i][j] == model.getCharacter()[1-actualPlayer])
                    ++sum;
            if (sum==3) return 2;
        }
        // check vertical possibilities
        for (int j=0;j<3;++j){
            sum = 0;
            for (int i=0;i<3;++i)
                if (model.getConfig()[i][j] == model.getCharacter()[1-actualPlayer])
                    ++sum;
            if (sum==3) return 2;
        }

        sum = 0;
        for (int i=0;i<3;++i)
            if (model.getConfig()[i][i]==model.getCharacter()[1-actualPlayer])
                ++sum;
        if (sum==3) return 2;

        sum = 0;
        for (int i=0;i<3;++i)
            if (model.getConfig()[i][2-i]==model.getCharacter()[1-actualPlayer])
                ++sum;
        if (sum==3) return 2;

        // check if actual player has won
        // check horizontal possibilities
        for (int i=0;i<3;++i){
            sum = 0;
            for (int j=0;j<3;++j)
                if (model.getConfig()[i][j]==model.getCharacter()[actualPlayer])
                    ++sum;
            if (sum==3) return 1;
        }
        // check vertical possibilities
        for (int j=0;j<3;++j){
            sum = 0;
            for (int i=0;i<3;++i)
                if (model.getConfig()[i][j]==model.getCharacter()[actualPlayer])
                    ++sum;
            if (sum==3) return 1;
        }
        // check the main diagonal
        sum = 0;
        for (int i=0;i<3;++i)
            if (model.getConfig()[i][i]==model.getCharacter()[actualPlayer])
                ++sum;
        if (sum==3) return 1;
        // check the secondary diagonal
        sum = 0;
        for (int i=0;i<3;++i)
            if (model.getConfig()[i][2-i]==model.getCharacter()[actualPlayer])
                ++sum;
        if (sum==3) return 1;

        // check if the cobfiguration is not full
        int ok = 1;
        for (int i=0;i<3;++i)
            for (int j=0;j<3;++j)
                if (model.getConfig()[i][j]=='_')
                    ok = 0;
        if (ok==1) return 3;
        return 0;
    }
}

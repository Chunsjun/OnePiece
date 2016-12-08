package sample;

public class BotController  {
    private BotGamer bot;
    private GameModel model;
    private GameController gameController;

    public BotController(BotGamer bot, GameModel model, GameController gameController) {
        this.bot = bot;
        this.model = model;
        this.gameController = gameController;
    }

    public String getLevel() {
        return this.bot.getLevel();
    }


   /* public Pair miniMax(int init_player,char[][] config, boolean maximizingPlayer) {
        if (this.gameController.checkEndOfTheGame(init_player, config) == init_player)return new Pair(0, 0, 1);
        if (this.gameController.checkEndOfTheGame(init_player, config) == 3) return new Pair(0, 0, 0);
        if (this.gameController.checkEndOfTheGame(init_player, config) == 3-init_player) return new Pair(0,0,-1);
        Pair best = new Pair(0,0,0);
        if (maximizingPlayer){
            best.setZ(-1);
            for (int i=0;i<3;++i)
                for (int j=0;j<3;++j)
                    if (config[i][j]=='_'){
                        config[i][j] = model.getCharacter()[init_player-1];
                        Pair val = miniMax(init_player,config,false);
                        if (best.getZ() < val.getZ()) best = new Pair(i,j,val.getZ());
                        config[i][j] = '_';
                    }
            return best;
        } else {
            best.setZ(1);
            for (int i=0;i<3;++i)
                for (int j=0;j<3;++j)
                    if (config[i][j]=='_'){
                        config[i][j] = model.getCharacter()[2-init_player];
                        Pair val = miniMax(init_player,config,true);
                        if (best.getZ() > val.getZ()) best = new Pair(i,j,val.getZ());
                        config[i][j] = '_';
                    }
            return best;
        }
    }*/

    public Pair nextMove(int player, char[][] config) {
        Pair sol = new Pair(0, 0);
        switch (this.bot.getLevel()) {
            case "novice":
                int x = (int) (Math.random() * 3);
                int y = (int) (Math.random() * 3);
                while (!(x < 3 && x >= 0 && y < 3 && y >= 0) || config[x][y] != '_') {
                    x = (int) (Math.random() * 3);
                    y = (int) (Math.random() * 3);
                }
                sol = new Pair(x, y);
                break;
          /*  case "master":
                sol =  miniMax(player,config,true);*/
        }
        return sol;
    }
}

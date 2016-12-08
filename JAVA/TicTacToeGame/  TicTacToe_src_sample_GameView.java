package sample;

public class GameView {

    public String displayWinner(int endGame, int player,int mode) {
        String ans ="Game ended! ";
        if (endGame==2) ans = ans.concat("Deuce!");
            else {
            if (mode == 1){
                if( player == 0) ans = ans.concat("\n You won!");
                    else ans = ans.concat("\n You lost!");
            } else ans = ans.concat("\n" + String.valueOf(player+1) + " player won!");
        }
        return ans;
    }

    public String displayPlayerTurn(int player,int mode) {
        if (mode==1) {
            if (player == 1) return "It's CPU's turn!";
            else return "It's your turn!";
        } else
        return ("It's " + (player+1) + " player's turn!");
    }

    public void displayConfiguration(char[][] c){
        for (int i=0;i<3;++i){
            for (int j=0;j<3;++j){
                System.out.print(c[i][j]+" ");
            }
            System.out.println();
        }
    }
}

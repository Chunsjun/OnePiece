package sample;

public class GameModel {
    private int mode;
    private char[] character = {'X','O'};
    private char[][] config = new char[3][3] ;

    public GameModel(int mode) {
        config = new char[][]{
            {'_', '_', '_'},
            {'_', '_', '_'},
            {'_', '_', '_'}
        };
        this.mode = mode;
    }

    public void changeCaracters(){
            char a = character[0];
            character[0] = character[1];
            character[1] = a;
    }

    public char[] getCharacter() {
        return character;
    }

    public int getMode(){
        return this.mode;
    }

    public char[][] getConfig(){
        return config;
    }

    public void setCell(int x,int y,char c){
        config[x][y] = c;
    }
}

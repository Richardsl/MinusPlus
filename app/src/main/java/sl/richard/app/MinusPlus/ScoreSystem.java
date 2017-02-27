package sl.richard.app.MinusPlus;

/**
 * Created by Richard on 31.05.2016.
 */
public class ScoreSystem {

    private int rounds;
    private int score;


    public ScoreSystem(){

    }
    public void start(){
        score = 0;
        rounds = 0;
    }
    public void add(int score){
        this.score += score;
    }

    public String getScore(){
        return score+"/"+rounds;
    }
    public void newRound(){
        rounds++;
    }
}



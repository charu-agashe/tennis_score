
public class Match {

    private Score score;
    private String player1Name;
    private String player2Name;

    public Match(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.score = new Score(player1Name,player2Name);
    }

    public void pointWonBy(String playerName){
        score.addPoint(playerName);
    }

    public String score(){
        return score.calculate(this.player1Name,this.player2Name);
    }
}

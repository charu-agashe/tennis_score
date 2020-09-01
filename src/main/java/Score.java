import java.util.HashMap;
import java.util.Map;

public class Score {

    private static final Integer[] scores = new Integer[]{0, 15, 30 , 40};
    private Map<String,Integer> playerPointsScored;
    private Map<String,Integer> tieBreakerPointsScored;
    private Integer gamesPlayer1;
    private Integer gamesPlayer2;
    private Boolean tieBreaker;

    public Score(String player1Name, String player2Name) {
        playerPointsScored = new HashMap<>();
        playerPointsScored.put(player1Name,0);
        playerPointsScored.put(player2Name,0);

        tieBreakerPointsScored = new HashMap<>();
        tieBreakerPointsScored.put(player1Name,0);
        tieBreakerPointsScored.put(player2Name,0);
        gamesPlayer1 = 0;
        gamesPlayer2 = 1;
        tieBreaker = false;
    }

    public void addPoint(String playerName) {
        if(tieBreaker){
            Integer score = tieBreakerPointsScored.get(playerName);
            tieBreakerPointsScored.put(playerName, ++score);
        }else {
            Integer score = playerPointsScored.get(playerName);
            playerPointsScored.put(playerName, ++score);
        }
    }

    public String calculate(String player1Name, String player2Name) {
        if(tieBreaker) {
            return tieBreakerCalculate(player1Name,player2Name);
        }else{
            return normalGameCalculate(player1Name,player2Name);
        }
    }

    private String tieBreakerCalculate(String player1Name, String player2Name) {
        Integer player1Points = tieBreakerPointsScored.get(player1Name);
        Integer player2Points = tieBreakerPointsScored.get(player2Name);

        if(Math.abs(player1Points - player2Points) >=2 ){
            if(player1Points == 7){
                gamesPlayer1++;
                return StaticVariables.PLAYER_WINS.replace("#PlayerName", player1Name);
            }else if(player2Points == 7){
                gamesPlayer2++;
                return StaticVariables.PLAYER_WINS.replace("#PlayerName", player2Name);
            }
        }
        return null;
    }

    private String normalGameCalculate(String player1Name, String player2Name) {
        Integer player1Points = playerPointsScored.get(player1Name);
        Integer player2Points = playerPointsScored.get(player2Name);

        if(isDeuce(player1Points,player2Points)){
            return StaticVariables.INITIAL_SET + StaticVariables.SEPARATOR + StaticVariables.DEUCE;
        }else if(isWin(player1Points,player2Points)){
            return getGameWinner(player1Points, player2Points);
        }else if(isAdvantage(player1Points, player2Points)){
            return getGameAdvantageString(player1Points, player2Points);
        }else{
            return StaticVariables.INITIAL_SET + StaticVariables.SEPARATOR + getScoreString(player1Points,player2Points);
        }

    }

    private String getGameAdvantageString(Integer player1Points, Integer player2Points) {
        if(player1Points > player2Points){
            return StaticVariables.INITIAL_SET+ StaticVariables.SEPARATOR + StaticVariables.ADVANTAGE.replace("#playerName","1");
        }else{
            return StaticVariables.INITIAL_SET+ StaticVariables.SEPARATOR + StaticVariables.ADVANTAGE.replace("#playerName","2");
        }
    }

    private String getGameWinner(Integer player1Points, Integer player2Points) {
        if (player1Points > player2Points) {
            gamesPlayer1++;
        } else {
            gamesPlayer2++;
       }
        resetPlayerScores();
        checkForTieBreaker();
        return gamesPlayer1 + "-" + gamesPlayer2;
    }

    private void checkForTieBreaker() {
        if(Math.abs(gamesPlayer1 - gamesPlayer2) == 1){
            if(gamesPlayer1 >= 6 && gamesPlayer2 >= 6 ) {
                tieBreaker = true;
            }else{
                tieBreaker = false;
            }
        }else {
            tieBreaker = false;
        }
    }

    private String getScoreString(Integer player1Points, Integer player2Points) {
        return scores[player1Points] + "-" + scores[player2Points];
    }


    private boolean isAdvantage(Integer player1Points, Integer player2Points) {
        if(Math.abs(player1Points - player2Points) == 1 && player1Points >= 3 && player2Points >= 3){
            return true;
        }
        return false;
    }

    private boolean isWin(Integer player1Points, Integer player2Points) {
        if(Math.abs(player1Points - player2Points) >= 2){
            if(player1Points > 3 || player2Points > 3) {
                return true;
            }else{
                return false;
            }

        }
        return false;
    }

    private boolean isDeuce(Integer player1Points, Integer player2Points) {
        if(player1Points == player2Points && player1Points >= 3){
            return true;
        }
        return false;
    }

    private void resetPlayerScores() {
        for (String player : playerPointsScored.keySet()) {
            playerPointsScored.put(player,0);
        }
    }

    private void resetTieBreakerScores() {
        for (String player : tieBreakerPointsScored.keySet()) {
            tieBreakerPointsScored.put(player,0);
        }
    }

    public void resetScore() {
        resetPlayerScores();
        resetTieBreakerScores();
        gamesPlayer1 = 0;
        gamesPlayer2 = 0;
        tieBreaker = false;
    }


    public Map<String, Integer> getPlayerPointsScored() {
        return playerPointsScored;
    }


    public Map<String, Integer> getTieBreakerPointsScored() {
        return tieBreakerPointsScored;
    }


    public void setPlayerPointsScored(Map<String, Integer> playerPointsScored) {
        this.playerPointsScored = playerPointsScored;
    }

    public void setTieBreakerPointsScored(Map<String, Integer> tieBreakerPointsScored) {
        this.tieBreakerPointsScored = tieBreakerPointsScored;
    }

    public void setGamesPlayer1(Integer gamesPlayer1) {
        this.gamesPlayer1 = gamesPlayer1;
    }

    public void setGamesPlayer2(Integer gamesPlayer2) {
        this.gamesPlayer2 = gamesPlayer2;
    }


    public Boolean getTieBreaker() {
        return tieBreaker;
    }

    public void setTieBreaker(Boolean tieBreaker) {
        this.tieBreaker = tieBreaker;
    }



}

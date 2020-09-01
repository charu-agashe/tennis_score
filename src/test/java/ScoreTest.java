import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ScoreTest {

    Score score;
    String player1;
    String player2;

    @BeforeEach
    public void setUp(){
        player1 = "A";
        player2 = "B";
        score = new Score(player1,player2);
    }

    @Test
    void initializeScoreMap() {

        Map<String, Integer> playerScores = score.getPlayerPointsScored();
        assertEquals(2,playerScores.size());
        assertTrue(playerScores.containsKey("A"));
        assertTrue(playerScores.containsKey("B"));
    }

    @Test
    void testAddPoint() {
        score.resetScore();
        score.addPoint(player1);
        score.addPoint(player2);
        score.addPoint(player2);
        Map<String, Integer> playerScores = score.getPlayerPointsScored();
        Integer player1Score = playerScores.get(player1);
        Integer player2Score = playerScores.get(player2);
        assertEquals(1,player1Score);
        assertEquals(2,player2Score);
    }

    @Test
    void testAddPointForTieBreaker() {
        score.resetScore();
        //setup test condition where player 1 has won 7 games and player 2 has won 5
        // the next point sets up player 2 with six games
        // now any further points scored should update the tiebreaker map
        Map<String,Integer> testScoresMap = new HashMap<>();
        testScoresMap.put(player1,1);
        testScoresMap.put(player2,3);

        score.setGamesPlayer1(7);
        score.setGamesPlayer2(5);
        score.setPlayerPointsScored(testScoresMap);

        score.addPoint(player2);
        score.calculate(player1,player2);
        score.addPoint(player2);
        Integer tieBreakerPointsPlayer2 = score.getTieBreakerPointsScored().get(player2);
        assertEquals(1,tieBreakerPointsPlayer2);
    }

    @Test
    void testCalculateWithOnePointScored() {
        score.resetScore();
        score.addPoint(player1);

        assertEquals("0-0, 15-0" , score.calculate(player1,player2));
    }

    @Test
    void testCalculateWithGameWon() {
        score.resetScore();
        score.addPoint(player1);
        score.addPoint(player1);
        score.addPoint(player1);
        score.addPoint(player1);

        assertEquals("1-0" , score.calculate(player1,player2));
    }

    @Test
    void testCalculateWithDeuce(){
        score.resetScore();
        score.addPoint(player1);
        score.addPoint(player2);
        score.addPoint(player1);
        score.addPoint(player1);
        score.addPoint(player2);
        score.addPoint(player2);
        assertEquals("0-0, Deuce" , score.calculate(player1,player2));
    }

    @Test
    public void testCalculateForAdvantage(){
        score.resetScore();
        score.addPoint(player1);
        score.addPoint(player2);
        score.addPoint(player1);
        score.addPoint(player1);
        score.addPoint(player2);
        score.addPoint(player2);
        score.addPoint(player1);
        assertEquals("0-0, Advantage player 1" , score.calculate(player1,player2));
    }

    @Test
    void testCalculateForPlayer1WinsSet() {
        score.resetScore();
        Map<String,Integer> testScoresMap = new HashMap<>();
        testScoresMap.put(player1,6);
        testScoresMap.put(player2,5);

        score.setGamesPlayer1(7);
        score.setGamesPlayer2(6);
        score.setTieBreakerPointsScored(testScoresMap);
        score.setTieBreaker(true);

        score.addPoint(player1);
        String winner = score.calculate(player1, player2);
        assertEquals("A wins set",winner);
    }

}
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatchTest {

    //test creation of a match with players
    // with points scored upto advantage for player1
    // and win for player1
    @Test
    public void testMatch(){
        Match match = new Match("player 1", "player 2");
        match.pointWonBy("player 1");
        match.pointWonBy("player 2");
        String score = match.score();
        assertEquals("0-0, 15-15",score);

        match.pointWonBy("player 1");
        match.pointWonBy("player 1");
        score = match.score();
        assertEquals("0-0, 40-15",score);

        match.pointWonBy("player 2");
        match.pointWonBy("player 2");
        score = match.score();
        assertEquals("0-0, Deuce",score);

        match.pointWonBy("player 1");
        score = match.score();
        assertEquals("0-0, Advantage player 1",score);

        match.pointWonBy("player 1");
        score = match.score();
        assertEquals("1-0",score);
    }

}
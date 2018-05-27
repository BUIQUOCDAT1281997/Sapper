import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void eatFreeCell() {
        Game game = new Game();
        game.chooseBoardPerformed(4, 4, 0);
        game.createBoard();
        game.eatFreeCell(1, 2);
        assertTrue(game.getStateBoard()[0][1].getOpen());
        assertTrue(game.getStateBoard()[0][2].getOpen());
        assertTrue(game.getStateBoard()[1][1].getOpen());
        assertTrue(game.getStateBoard()[1][2].getOpen());
        assertTrue(game.getStateBoard()[3][2].getOpen());
    }
}
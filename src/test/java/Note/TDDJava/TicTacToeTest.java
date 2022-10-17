package Note.TDDJava;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


class TicTacToeTest {

    private TicTacToe ticTacToe;
    @BeforeEach
    void setUp() {
        ticTacToe = new TicTacToe();
    }

    @AfterEach
    void tearDown() {
        ticTacToe = null;
    }

    @Test
    @DisplayName("When a piece is placed anywhere outside the x-axis, then RuntimeException is thrown.")
    public void whenXOutsideBoardThenRuntimeException(){
       Exception exception = assertThrows(RuntimeException.class, ()-> ticTacToe.play(5, 2));
       assertThat(exception.getMessage()).contains("X is outside board");
    }

    @Test
    @DisplayName("When a piece is placed anywhere outside the y-axis, then RuntimeException is thrown.")
    public void whenYOutsideBoardThenRuntimeException(){
        Exception exception = assertThrows(RuntimeException.class, ()-> ticTacToe.play(2, 5));
        assertThat(exception.getMessage()).contains("X is outside board");
    }

    @Test
    @DisplayName("When a piece is placed on an occupied space, then RuntimeException is thrown.")
    public void whenOccupiedThenRuntimeException() {
        ticTacToe.play(2, 1);
        Exception exception = assertThrows(RuntimeException.class, ()-> ticTacToe.play(2, 1));
        assertThat(exception.getMessage()).contains("Box is occupied");
    }

    @Test
    @DisplayName("The first turn should be played by Player X.")
    public void givenFirstTurnWhenNextPlayerThenX(){
        assertThat(ticTacToe.nextPlayer()).isEqualTo('X');
    }

    @Test
    @DisplayName("If the last turn was played by X, then the next turn should be played by O.")
    public void givenLastTurnWasXWhenNextPlayerThenO(){
        ticTacToe.play(1, 1);
        assertThat(ticTacToe.nextPlayer()).isEqualTo('O');
    }

    @Test
    @DisplayName("If no winning condition is fulfilled, then there is no winner.")
    public void whenPlayThenNoWinner(){
        String actual = ticTacToe.play(1, 1);
        assertThat(actual).isEqualTo("No winner");
    }

    @Test
    @DisplayName("The player wins when the whole horizontal line is occupied by his pieces.")
    public void whenPlayAndWholeHorizontalLineThenWinner(){
        ticTacToe.play(1, 1); // X
        ticTacToe.play(1, 2); // O
        ticTacToe.play(2, 1); // X
        ticTacToe.play(2, 2); // O
        String actual = ticTacToe.play(3, 1); // X
        assertThat(actual).isEqualTo("X is the winner");
    }

    @Test
    @DisplayName("The player wins when the whole vertical line is occupied by his pieces.")
    public void whenPlayAndWholeVerticalLineThenWinner(){
        ticTacToe.play(1, 1); // X
        ticTacToe.play(1, 2); // O
        ticTacToe.play(2, 2); // X
        ticTacToe.play(1, 3); // O
        String actual = ticTacToe.play(3, 3); // X
        assertThat(actual).isEqualTo("X is the winner");
    }

    @Test
    @DisplayName("The player wins when the whole diagonal line from the bottom-left to top- right is occupied by his pieces.")
    public void whenPlayAndBottomTopDiagonalLineThenWinner(){
        ticTacToe.play(1, 1); // X
        ticTacToe.play(1, 2); // O
        ticTacToe.play(2, 2); // X
        ticTacToe.play(1, 3); // O
        String actual = ticTacToe.play(3, 3); // X
        assertThat(actual).isEqualTo("X is the winner");
    }

    @Test
    @DisplayName("The result is a draw when all the boxes are filled.")
    public void whenAllBoxesAreFilledThenDraw(){
        ticTacToe.play(1, 1);
        ticTacToe.play(1, 2);
        ticTacToe.play(1, 3);
        ticTacToe.play(2, 1);
        ticTacToe.play(2, 3);
        ticTacToe.play(2, 2);
        ticTacToe.play(3, 1);
        ticTacToe.play(3, 3);
        String actual = ticTacToe.play(3, 2);
        assertThat(actual).isEqualTo("The result is draw");
    }
}
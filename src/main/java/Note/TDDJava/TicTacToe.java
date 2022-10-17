package Note.TDDJava;

public class TicTacToe {
    private char lastPlayer = '\0';
    private static final int SIZE = 3;

    public char nextPlayer() {
        if (lastPlayer == 'X')
            return 'O';
        return 'X';
    }


    private final Character[][] board = {
            {'\0', '\0', '\0'},
            {'\0', '\0', '\0'},
            {'\0', '\0', '\0'}
    };

    public String play(int x, int y) {
        String message = "No winner";
        checkAxis(x);
        checkAxis(y);
        lastPlayer = nextPlayer();
        setBox(x, y);
        if (isWin(x, y)) {
            message = lastPlayer + " is the winner";
        } else if (isDraw()) {
            message = "The result is draw";
        }
        return message;
    }

    private boolean isDraw() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == '\0') {
                    return false;
                }
            }
        }
        return true;
    }

    private void checkAxis(int axis) {
        if (axis < 1 || axis > 3) {
            throw new RuntimeException("X is outside board");
        }
    }

    private void setBox(int x, int y) {
        if (board[x - 1][y - 1] != '\0') {
            throw new RuntimeException("Box is occupied");
        } else {
            board[x - 1][y - 1] = lastPlayer;
        }
    }

    private boolean isWin(int row, int col) {
        int playerTotal = lastPlayer * SIZE;
        char horizontal, vertical, diagonal1, diagonal2;
        horizontal = vertical = diagonal1 = diagonal2 = '\0';

        for (int i = 0; i < SIZE; i++) {
            horizontal += board[i][col - 1];
            vertical += board[row - 1][i];
            diagonal1 += board[i][i];
            diagonal2 += board[i][SIZE - i - 1];
        }
        return horizontal == playerTotal ||
                vertical == playerTotal ||
                diagonal1 == playerTotal ||
                diagonal2 == playerTotal;
    }


    public Character[][] getBoard() {
        return board;
    }
}

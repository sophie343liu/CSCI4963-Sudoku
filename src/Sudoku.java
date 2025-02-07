import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Sudoku {
    private int[][] board;
    private int[][] solution; // Added this field to store the solution board
    private static final int BOARD_SIZE = 9;
    private static final int SUBGRID_SIZE = 3;
    private static final int EMPTY_CELL = 0;

    public Sudoku() {
        board = new int[BOARD_SIZE][BOARD_SIZE];
        solution = new int[BOARD_SIZE][BOARD_SIZE]; // Initialize the solution board
        clearBoard();
        generatePuzzle();
    }

    public int[][] getBoard() {
        return board;
    }

    public int[][] getSolution() {
        return solution;
    }

    public int[][] copyBoard() {
        int[][] copy = new int[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, BOARD_SIZE);
        }
        return copy;
    }

    public boolean isValidMove(int row, int col, int num) {
        for (int x = 0; x < BOARD_SIZE; x++) {
            if (board[row][x] == num || board[x][col] == num) {
                return false;
            }
        }

        int startRow = row - row % SUBGRID_SIZE;
        int startCol = col - col % SUBGRID_SIZE;
        for (int i = 0; i < SUBGRID_SIZE; i++) {
            for (int j = 0; j < SUBGRID_SIZE; j++) {
                if (board[i + startRow][j + startCol] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean solve() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col] == EMPTY_CELL) {
                    for (int num = 1; num <= BOARD_SIZE; num++) {
                        if (isValidMove(row, col, num)) {
                            board[row][col] = num;
                            if (solve()) {
                                return true;
                            }
                            board[row][col] = EMPTY_CELL;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public void clearBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = EMPTY_CELL;
            }
        }
    }

    public void generatePuzzle() {
        Random random = new Random();
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= BOARD_SIZE; i++) {
            numbers.add(i);
        }

        for (int i = 0; i < BOARD_SIZE; i += SUBGRID_SIZE) {
            Collections.shuffle(numbers, random);
            for (int row = 0; row < SUBGRID_SIZE; row++) {
                for (int col = 0; col < SUBGRID_SIZE; col++) {
                    board[i + row][i + col] = numbers.get(row * SUBGRID_SIZE + col);
                }
            }
        }

        solve(); // Solve the board fully to generate the solution
        copyBoardToSolution(); // Copy the fully solved board to the solution
        printSolution();
        int cellsToRemove = BOARD_SIZE * BOARD_SIZE / 2;
        while (cellsToRemove > 0) {
            int row = random.nextInt(BOARD_SIZE);
            int col = random.nextInt(BOARD_SIZE);
            if (board[row][col] != EMPTY_CELL) {
                board[row][col] = EMPTY_CELL;
                cellsToRemove--;
            }
        }
    }

    private void copyBoardToSolution() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                solution[i][j] = board[i][j]; // Copy solved board to solution array
            }
        }
    }

    public void printSolution() {
        System.out.println("Correct Solution:");
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(solution[i][j] + " ");
            }
            System.out.println(); // New line after each row
        }
    }
}

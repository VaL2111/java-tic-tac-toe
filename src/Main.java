import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static byte ROW_COUNT = 3;
    private static byte COL_COUNT = 3;

    private static String CELL_STATE_EMPTY = " ";
    private static String CELL_STATE_X = "X";
    private static String CELL_STATE_O = "O";

    private static String GAME_STATE_X_WIN = "Ви перемогли!";
    private static String GAME_STATE_O_WIN = "Ви програли.";
    private static String GAME_STATE_DRAW = "Нічия!";
    private static String GAME_STATE_NOT_FINISHED = "Гра ще не закінчена!";

    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();

    public static void main(String[] args) {
        startGameRound();
    }

    public static void startGameRound() {
        String[][] board = createBoard();
        startGameLoop(board);
    }

    public static void startGameLoop(String[][] board) {
        while (true) {
            makePlayerTurn(board);
            printBoard(board);

            System.out.println();

            if (isGameOver(board)) {
                break;
            }

            makeBotTurn(board);
            printBoard(board);

            if (isGameOver(board)) {
                break;
            }
        }
    }

    public static boolean isGameOver(String[][] board) {
        String gameState = checkGameState(board);
        if (!Objects.equals(gameState, GAME_STATE_NOT_FINISHED)) {
            System.out.println(gameState);
            return true;
        }

        return false;
    }

    public static void makePlayerTurn(String[][] board) {
        int[] coordinates = inputCellCoordinates(board);
        board[coordinates[0]][coordinates[1]] = CELL_STATE_X;
    }

    public static void makeBotTurn(String[][] board) {
        System.out.println("Хід бота:");
        int[] coordinates = getRandomEmptyCellCoordinates(board);
        board[coordinates[0]][coordinates[1]] = CELL_STATE_O;
    }

    public static String checkGameState(String[][] board) {
        ArrayList<Byte> sums = new ArrayList<>();

        byte firstDiagonalSum = 0;
        byte secondDiagonalSum = 0;
        for (int row = 0; row < ROW_COUNT; row++) {
            byte rowSum = 0;
            byte colSum = 0;
            for (int col = 0; col < COL_COUNT; col++) {
                rowSum += calculateNumValue(board[row][col]);
                colSum += calculateNumValue(board[col][row]);
            }
            sums.add(rowSum);
            sums.add(colSum);

            firstDiagonalSum += calculateNumValue(board[row][row]);
            secondDiagonalSum += calculateNumValue((board[row][ROW_COUNT - 1 - row]));
        }
        sums.add(firstDiagonalSum);
        sums.add(secondDiagonalSum);

        if (sums.contains((byte) 3)) {
            return GAME_STATE_X_WIN;
        } else if (sums.contains((byte) -3)) {
            return GAME_STATE_O_WIN;
        } else if (areAllCellsTaken(board)) {
            return GAME_STATE_DRAW;
        } else {
            return GAME_STATE_NOT_FINISHED;
        }
    }

    private static byte calculateNumValue(String cellState) {
        if (Objects.equals(cellState, CELL_STATE_X)) {
            return 1;
        } else if (Objects.equals(cellState, CELL_STATE_O)) {
            return -1;
        } else {
            return 0;
        }
    }

    public static boolean areAllCellsTaken(String[][] board) {
        for (int row = 0; row < ROW_COUNT; row++) {
            for (int col = 0; col < COL_COUNT; col++) {
                if (Objects.equals(board[row][col], CELL_STATE_EMPTY)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static int[] getRandomEmptyCellCoordinates(String[][] board) {
        while (true) {
            int row = random.nextInt(ROW_COUNT);
            int col = random.nextInt(COL_COUNT);

            if (Objects.equals(board[row][col], CELL_STATE_EMPTY)) {
                return new int[]{row, col};
            }
        }
    }

    public static String[][] createBoard() {
        String[][] board = new String[ROW_COUNT][COL_COUNT];

        for (int row = 0; row < ROW_COUNT; row++) {
            for (int col = 0; col < COL_COUNT; col++) {
                board[row][col] = CELL_STATE_EMPTY;
            }
        }

        return board;
    }

    public static int[] inputCellCoordinates(String[][] board) {
        System.out.print("Введіть дві цифри від 0 до 2 через пробіл: ");

        do {
            String[] input = scanner.nextLine().split(" ");

            int row = Integer.parseInt(input[0]);
            int col = Integer.parseInt(input[1]);

            if ((row < 0) || (row >= ROW_COUNT) || (col < 0) || (col >= COL_COUNT)) {
                System.out.print("Неправильний ввід! Введіть дві цифри від 0 до 2 через пробіл: ");
            } else if (!Objects.equals(board[row][col], CELL_STATE_EMPTY)) {
                System.out.print("Ця комірка вже зайнята. Виберіть іншу: ");
            } else {
                return new int[]{row, col};
            }
        } while (true);
    }

    public static void printBoard(String[][] board) {
        System.out.println("---------");
        for (int row = 0; row < ROW_COUNT; row++) {
            StringBuilder line = new StringBuilder("| ");
            for (int col = 0; col < COL_COUNT; col++) {
                line.append(board[row][col]).append(" ");
            }
            line.append("|");
            System.out.println(line);
        }
        System.out.println("---------");
    }
}

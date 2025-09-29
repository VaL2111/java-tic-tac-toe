import java.util.Objects;
import java.util.Scanner;

public class Main {

    private static byte ROW_COUNT = 3;
    private static byte COL_COUNT = 3;

    private static String CELL_STATE_EMPTY = " ";
    private static String CELL_STATE_X = "X";
    private static String CELL_STATE_O = "O";

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String[][] board = createBoard();
        board[0][0] = CELL_STATE_X;

        int[] ints = inputBoardCoordinates(board);
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

    public static int[] inputBoardCoordinates(String[][] board) {
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
}

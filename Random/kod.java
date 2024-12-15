import java.util.Scanner;

public class TicTacToe {

    private static char[][] board = new char[3][3];
    private static char currentPlayer = 'X';
    private static boolean gameWon = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        initializeBoard();
        
        while (!gameWon) {
            printBoard();
            playerTurn(scanner);
            gameWon = checkWin();
            if (!gameWon) {
                switchPlayer();
            }
        }
        
        printBoard();
        System.out.println("Игрок " + currentPlayer + " победил!");
    }

    // Инициализация игрового поля
    private static void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    // Печать текущего состояния игрового поля
    private static void printBoard() {
        System.out.println("  0 1 2");
        for (int i = 0; i < 3; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Ход игрока
    private static void playerTurn(Scanner scanner) {
        int row, col;
        boolean validMove = false;

        while (!validMove) {
            System.out.println("Игрок " + currentPlayer + ", введите номер строки (0-2) и столбца (0-2) через пробел:");
            row = scanner.nextInt();
            col = scanner.nextInt();

            if (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == ' ') {
                board[row][col] = currentPlayer;
                validMove = true;
            } else {
                System.out.println("Неверный ход! Ячейка занята или выход за границы.");
            }
        }
    }

    // Проверка на победу
    private static boolean checkWin() {
        // Проверка строк
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) {
                return true;
            }
        }

        // Проверка столбцов
        for (int i = 0; i < 3; i++) {
            if (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer) {
                return true;
            }
        }

        // Проверка диагоналей
        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) {
            return true;
        }
        if (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) {
            return true;
        }

        // Нет победителя
        return false;
    }

    // Переключение игрока
    private static void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }
}

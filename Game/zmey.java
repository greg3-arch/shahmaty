import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class SnakeGame {
    private static final int WIDTH = 20;
    private static final int HEIGHT = 10;
    private static LinkedList<Position> snake = new LinkedList<>();
    private static Position food;
    private static char[][] board = new char[HEIGHT][WIDTH];
    private static char snakeChar = 'O';
    private static char foodChar = 'X';
    private static char emptyChar = ' ';
    private static char borderChar = '#';
    private static boolean isGameOver = false;
    private static int score = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        initializeGame();

        while (!isGameOver) {
            printBoard();
            char direction = getDirection(scanner);
            moveSnake(direction);
            checkGameOver();
            if (!isGameOver) {
                checkFoodCollision();
            }
            try {
                Thread.sleep(200); // Замедляем скорость игры
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Игра завершена! Ваш счёт: " + score);
    }

    // Инициализация игры
    private static void initializeGame() {
        snake.clear();
        snake.add(new Position(HEIGHT / 2, WIDTH / 2)); // Начальная позиция змейки
        placeFood();
    }

    // Печать игрового поля
    private static void printBoard() {
        clearBoard();
        drawBorders();
        drawSnake();
        drawFood();
        System.out.println("Счёт: " + score);
    }

    private static void clearBoard() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                board[i][j] = emptyChar;
            }
        }
    }

    private static void drawBorders() {
        for (int i = 0; i < WIDTH; i++) {
            board[0][i] = borderChar;
            board[HEIGHT - 1][i] = borderChar;
        }
        for (int i = 0; i < HEIGHT; i++) {
            board[i][0] = borderChar;
            board[i][WIDTH - 1] = borderChar;
        }
    }

    private static void drawSnake() {
        for (Position p : snake) {
            board[p.row][p.col] = snakeChar;
        }
    }

    private static void drawFood() {
        board[food.row][food.col] = foodChar;
    }

    // Получение направления от пользователя
    private static char getDirection(Scanner scanner) {
        char direction = ' ';
        while (direction != 'W' && direction != 'A' && direction != 'S' && direction != 'D') {
            System.out.println("Введите направление (W - вверх, A - влево, S - вниз, D - вправо):");
            direction = scanner.next().toUpperCase().charAt(0);
        }
        return direction;
    }

    // Движение змейки
    private static void moveSnake(char direction) {
        Position head = snake.getFirst();
        Position newHead = null;

        switch (direction) {
            case 'W':
                newHead = new Position(head.row - 1, head.col); // Вверх
                break;
            case 'A':
                newHead = new Position(head.row, head.col - 1); // Влево
                break;
            case 'S':
                newHead = new Position(head.row + 1, head.col); // Вниз
                break;
            case 'D':
                newHead = new Position(head.row, head.col + 1); // Вправо
                break;
        }

        // Сдвиг змейки: добавляем новую голову и удаляем хвост
        snake.addFirst(newHead);

        // Если змейка не съела еду, удаляем хвост
        if (!newHead.equals(food)) {
            snake.removeLast();
        }
    }

    // Проверка на конец игры
    private static void checkGameOver() {
        Position head = snake.getFirst();
        
        // Проверка на столкновение с границей
        if (head.row < 1 || head.row >= HEIGHT - 1 || head.col < 1 || head.col >= WIDTH - 1) {
            isGameOver = true;
        }

        // Проверка на столкновение с самим собой
        for (int i = 1; i < snake.size(); i++) {
            if (snake.get(i).equals(head)) {
                isGameOver = true;
                break;
            }
        }
    }

    // Проверка на столкновение с едой
    private static void checkFoodCollision() {
        Position head = snake.getFirst();
        if (head.equals(food)) {
            score++;
            placeFood();
        }
    }

    // Размещение еды в случайной позиции
    private static void placeFood() {
        Random rand = new Random();
        food = new Position(rand.nextInt(HEIGHT - 2) + 1, rand.nextInt(WIDTH - 2) + 1);
        while (snake.contains(food)) { // Проверяем, чтобы еда не появилась на змейке
            food = new Position(rand.nextInt(HEIGHT - 2) + 1, rand.nextInt(WIDTH - 2) + 1);
        }
    }

    // Класс для представления позиции
    private static class Position {
        int row, col;

        Position(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Position position = (Position) obj;
            return row == position.row && col == position.col;
        }

        @Override
        public int hashCode() {
            return 31 * row + col;
        }
    }
}

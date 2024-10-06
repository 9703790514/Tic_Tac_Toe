import java.util.Random;
import java.util.Scanner;

class TicTacToe {
    static char board[][];

    TicTacToe() {
        board = new char[3][3];
        init();
    }

    void init() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    static void display() {
        System.out.println(" ------------- ");
        for (int i = 0; i < 3; i++) {
            System.out.print(" | ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println();
            System.out.println(" ------------- ");
        }
    }

    public static boolean checkRowWin() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != ' ' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkColWin() {
        for (int i = 0; i < 3; i++) {
            if (board[0][i] != ' ' && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkDiagWin() {
        return (board[0][0] != ' ' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) ||
               (board[0][2] != ' ' && board[0][2] == board[1][1] && board[1][1] == board[2][0]);
    }

    public static boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false; 
                }
            }
        }
        return true; 
    }
    public static void placeMark(int row, int col, char mark) {
        if (board[row][col] == ' ' && row >= 0 && row < 3 && col >= 0 && col < 3) {
            board[row][col] = mark;
        } else {
            System.out.println("Invalid Move");
        }
    }
}

abstract class Player {
    String name;
    char mark;

    public boolean isValid(int row, int col) {
        return TicTacToe.board[row][col] == ' ' && row >= 0 && row < 3 && col >= 0 && col < 3;
    }

    abstract void makeMove(); 
}

class AIPlayer extends Player {
    AIPlayer(String name, char mark) {
        this.name = name;
        this.mark = mark;
    }

    public void makeMove() {
        Random r = new Random();
        int row;
        int col;

        do {
            row = r.nextInt(3);
            col = r.nextInt(3);
        } while (!isValid(row, col));

        TicTacToe.placeMark(row, col, mark);
    }
}

class HumanPlayer extends Player {
    HumanPlayer(String name, char mark) {
        this.name = name;
        this.mark = mark;
    }

    public void makeMove() {
        Scanner sc = new Scanner(System.in);
        int row = -1;
        int col = -1;

        while (true) {
            System.out.print("Enter your row (0/1/2): ");
            String rowInput = sc.nextLine();
            System.out.print("Enter your column (0/1/2): ");
            String colInput = sc.nextLine();

            if (rowInput.matches("[0-2]") && colInput.matches("[0-2]")) {
                row = Integer.parseInt(rowInput);
                col = Integer.parseInt(colInput);
                if (isValid(row, col)) {
                    break; 
                } else {
                    System.out.println("Invalid move, that cell is already taken.");
                }
            } else {
                System.out.println("Invalid input, please enter numbers between 0 and 2.");
            }
        }

        TicTacToe.placeMark(row, col, mark);
    }

}

public class LaunchGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;
        do {
            System.out.print("Do you want to play with a friend or AI: ");
            String choice = scanner.nextLine().trim().toLowerCase();

            HumanPlayer p1 = null;
            Player p2 = null;

            System.out.print("Enter name for Player 1 (X): ");
            String player1Name = scanner.nextLine();
            p1 = new HumanPlayer(player1Name, 'X');

            if (choice.equals("friend")) {
                System.out.print("Enter name for Player 2 (O): ");
                String player2Name = scanner.nextLine();
                p2 = new HumanPlayer(player2Name, 'O');
            } else if (choice.equals("ai")) {
                p2 = new AIPlayer("AI", 'O');
            } else {
                System.out.println("Invalid choice, please enter 'friend' or 'AI'.");
                continue; // Restart the loop for valid input
            }

            TicTacToe t = new TicTacToe();
            Player cp = p1; // Start with Player 1

            while (true) {
                System.out.println(cp.name + "'s turn");
                cp.makeMove();
                TicTacToe.display();

                if (TicTacToe.checkRowWin() || TicTacToe.checkColWin() || TicTacToe.checkDiagWin()) {
                    System.out.println(cp.name + " has won!");
                    break;
                } else if (TicTacToe.isBoardFull()) {
                    System.out.println("The game is a draw!");
                    break;
                } else {
                    cp = (cp == p1) ? p2 : p1; // Switch player
                }
            }

            System.out.println("Do you want to play again (Yes/No): ");
            String s = scanner.nextLine();
            flag = s.equalsIgnoreCase("Yes");
        } while (flag);

        System.out.println("Thank you for playing.......");

        scanner.close();
    }
}

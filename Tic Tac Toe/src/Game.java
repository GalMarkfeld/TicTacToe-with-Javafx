import java.util.LinkedList;
import java.util.Scanner;

public class Game {


    //global variables
    public static char[][] GAME_BOARD;
    public static LinkedList<Integer> FREE_POSITIONS;
    public static int[][] WINNING_POSITIONS;
    public static Player PLAYER;
    public static Player COMPUTER;


    //main method
    public static void main(String[] args) {
        //start
        System.out.println("Welcome to Tic Tac Toe");
        System.out.println("------------------------\n");
        init();

        //instructions
        System.out.println("////////Instructions://////////\n");
        System.out.println("You are the first to start, at the moment you can only play vs the computer.\n");
        System.out.println("In each turn you are required to enter the position in which you want to place your piece.\n");
        System.out.println("Here is an empty game board for example:\n");
        printGameBoard();
        System.out.println("The positions on the board are numbered from 1-9 like the following:\n\n" +
                "1|2|3\n-+-+-\n4|5|6\n-+-+-\n7|8|9\n");
        System.out.println("////////Start of game://////////\n");

        //receive play char from user
        System.out.println("Do you want to play as X or O?");
        Scanner scanner = new Scanner(System.in);
        char c = scanner.next().charAt(0);

        //input validation
        while (c != 'X' && c != 'O') {
            System.out.println("Enter a valid character:");
            c = scanner.next().charAt(0);
        }

        //set player and computer char
        if (c == 'X') {
            PLAYER.setCharacter('X');
            COMPUTER.setCharacter('O');
        } else {
            PLAYER.setCharacter('O');
            COMPUTER.setCharacter('X');
        }

        //game loop
        while (true) {

            //get user position
            System.out.println("Enter a valid position to place your piece:");
            int position = scanner.nextInt();

            //validation using placePiece function
            while (!placePiece(position, PLAYER)) {
                System.out.println("Please enter a valid position:");
                position = scanner.nextInt();
            }

            //print game board after placing a piece
            printGameBoard();

            //check if user won
            if (isWinner(PLAYER)) {
                System.out.println("Congratulations, you won!");
                break;
            }

            //check if board is full and nobody won
            if (FREE_POSITIONS.isEmpty()) {
                System.out.println("It is a draw! \n" +
                        "game is over");
                break;
            }

            //computer place piece - smart solution
            int smartPosition = closeToWin(PLAYER); //get position required to block
            //if the user is in a position to win
            if (smartPosition != -1) {
                placePiece(smartPosition, COMPUTER);
            }
            //random placement
            else {
                int index = (int) (FREE_POSITIONS.size() * Math.random());
                int computerPosition = FREE_POSITIONS.get(index);
                placePiece(computerPosition, COMPUTER); //we can place without validation since we receive the index from the free positions
            }


            //print game board after placing a piece
            System.out.println("Computer placing:\n");
            printGameBoard();

            //check if computer won
            if (isWinner(COMPUTER)) {
                System.out.println("You lost, don't be so bad next time");
                break;
            }

            //check if board is full and nobody won
            if (FREE_POSITIONS.isEmpty()) {
                System.out.println("It is a draw! \n" +
                        "game is over");
                break;
            }


        }


    }


    // places piece in a valid position
    public static boolean placePiece(int position, Player player) {
        char c = player.getCharacter();
        switch (position) {
            case (1):
                if (FREE_POSITIONS.remove((Object) position)) {
                    GAME_BOARD[0][0] = c;
                    player.positions.add(position);
                    return true;
                }
                return false;
            case (2):
                if (FREE_POSITIONS.remove((Object) position)) {
                    GAME_BOARD[0][2] = c;
                    player.positions.add(position);
                    return true;
                }
                return false;
            case (3):
                if (FREE_POSITIONS.remove((Object) position)) {
                    GAME_BOARD[0][4] = c;
                    player.positions.add(position);
                    return true;
                }
                return false;
            case (4):
                if (FREE_POSITIONS.remove((Object) position)) {
                    GAME_BOARD[2][0] = c;
                    player.positions.add(position);
                    return true;
                }
                return false;
            case (5):
                if (FREE_POSITIONS.remove((Object) position)) {
                    GAME_BOARD[2][2] = c;
                    player.positions.add(position);
                    return true;
                }
                return false;
            case (6):
                if (FREE_POSITIONS.remove((Object) position)) {
                    GAME_BOARD[2][4] = c;
                    player.positions.add(position);
                    return true;
                }
                return false;
            case (7):
                if (FREE_POSITIONS.remove((Object) position)) {
                    GAME_BOARD[4][0] = c;
                    player.positions.add(position);
                    return true;
                }
                return false;
            case (8):
                if (FREE_POSITIONS.remove((Object) position)) {
                    GAME_BOARD[4][2] = c;
                    player.positions.add(position);
                    return true;
                }
                return false;
            case (9):
                if (FREE_POSITIONS.remove((Object) position)) {
                    GAME_BOARD[4][4] = c;
                    player.positions.add(position);
                    return true;
                }
                return false;

        }
        return false;
    }


    // initialize global variables
    public static void init() {
        //initialize empty game board
        GAME_BOARD = new char[][]{
                {' ', '|', ' ', '|', ' '},
                {'-', '+', '-', '+', '-'},
                {' ', '|', ' ', '|', ' '},
                {'-', '+', '-', '+', '-'},
                {' ', '|', ' ', '|', ' '}
        };

        //initialize free positions list from 1 to 9
        FREE_POSITIONS = new LinkedList<>();
        for (int i = 1; i <= 9; i++) {
            FREE_POSITIONS.add(i);
        }

        //initialize players
        PLAYER = new Player();
        COMPUTER = new Player();

        //initialize winning positions double array
        WINNING_POSITIONS = new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
                {1, 4, 7},
                {2, 5, 8},
                {3, 6, 9},
                {1, 5, 9},
                {3, 5, 7}
        };

    }


    // prints game board
    public static void printGameBoard() {
        for (char[] row : GAME_BOARD) {
            for (char c : row) {
                System.out.print(c);
            }
            System.out.println();
        }
        System.out.println();

    }


    // checks if a certain player is a winner
    public static boolean isWinner(Player player) {
        for (int[] sequence : WINNING_POSITIONS) {
            if (player.positions.contains(sequence[0]) && player.positions.contains(sequence[1]) && player.positions.contains(sequence[2]))
                return true;
        }
        return false;
    }

    //checks if player is close to winning and returns the position required to block
    public static int closeToWin(Player player) {

        //combinations with middle piece
        if (player.positions.contains(5) && player.positions.contains(1) && FREE_POSITIONS.contains(9)) return 9;
        if (player.positions.contains(5) && player.positions.contains(2) && FREE_POSITIONS.contains(8)) return 8;
        if (player.positions.contains(5) && player.positions.contains(3) && FREE_POSITIONS.contains(7)) return 7;
        if (player.positions.contains(5) && player.positions.contains(4) && FREE_POSITIONS.contains(6)) return 6;
        if (player.positions.contains(5) && player.positions.contains(6) && FREE_POSITIONS.contains(4)) return 4;
        if (player.positions.contains(5) && player.positions.contains(7) && FREE_POSITIONS.contains(3)) return 3;
        if (player.positions.contains(5) && player.positions.contains(8) && FREE_POSITIONS.contains(2)) return 2;
        if (player.positions.contains(5) && player.positions.contains(9) && FREE_POSITIONS.contains(1)) return 1;

        //diagonals
        if (player.positions.contains(1) && player.positions.contains(9) && FREE_POSITIONS.contains(5)) return 5;
        if (player.positions.contains(7) && player.positions.contains(3) && FREE_POSITIONS.contains(5)) return 5;

        //top row combinations
        if (player.positions.contains(1) && player.positions.contains(2) && FREE_POSITIONS.contains(3)) return 3;
        if (player.positions.contains(1) && player.positions.contains(3) && FREE_POSITIONS.contains(2)) return 2;
        if (player.positions.contains(2) && player.positions.contains(3) && FREE_POSITIONS.contains(1)) return 1;

        //right row combinations
        if (player.positions.contains(3) && player.positions.contains(6) && FREE_POSITIONS.contains(9)) return 9;
        if (player.positions.contains(3) && player.positions.contains(9) && FREE_POSITIONS.contains(6)) return 6;
        if (player.positions.contains(9) && player.positions.contains(6) && FREE_POSITIONS.contains(3)) return 3;

        //left row combinations
        if (player.positions.contains(1) && player.positions.contains(4) && FREE_POSITIONS.contains(7)) return 7;
        if (player.positions.contains(1) && player.positions.contains(7) && FREE_POSITIONS.contains(4)) return 4;
        if (player.positions.contains(4) && player.positions.contains(7) && FREE_POSITIONS.contains(1)) return 1;

        //bottom row combinations
        if (player.positions.contains(7) && player.positions.contains(9) && FREE_POSITIONS.contains(8)) return 8;
        if (player.positions.contains(7) && player.positions.contains(8) && FREE_POSITIONS.contains(9)) return 9;
        if (player.positions.contains(9) && player.positions.contains(8) && FREE_POSITIONS.contains(7)) return 7;

        //if player is not close to winning
        return -1;
    }


}




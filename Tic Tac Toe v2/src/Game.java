import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Scanner;

public class Game {


    //global variables
    public static char[][] GAME_BOARD;
    public static LinkedList<Integer> FREE_POSITIONS;
    public static int[][] WINNING_POSITIONS;


    //main method
    public static void main(String[] args) {
        //start
        System.out.println("Welcome to Tic Tac Toe");
        System.out.println("------------------------\n");
        init();

        //create players
        Player user = new Player();
        Player computer = new Player();

        //instructions
        printInstructions();

        //run demo?
        System.out.println("Do you understand the rules?");

        //get answer from user
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.next();
        answer = answer.toLowerCase();

        //validate input
        while (!answer.equals("yes") && !answer.equals("no")) {
            System.out.println("Answer the question");
            answer = scanner.next();
        }

        //if user didn't understand the rules run demo
        if (answer.equals("no")) runDemo();

        //start of game
        System.out.println("////////Start of game://////////\n");

        //receive play char from user
        System.out.println("Do you want to play as X or O?");
        char c = scanner.next().charAt(0);

        //input validation
        while (c != 'X' && c != 'O' && c != 'x' && c != 'o' && c != '0') {
            System.out.println("Enter a valid character:");
            c = scanner.next().charAt(0);
        }

        //set player and computer char
        if (c == 'X' || c == 'x') {
            user.setCharacter('X');
            computer.setCharacter('O');
        } else {
            user.setCharacter('O');
            computer.setCharacter('X');
        }

        //game loop
        while (true) {

            //get user position
            System.out.println("Enter a valid position to place your piece:");
            //using ascii in order to avoid Input Missmatch Exeption
            int position = scanner.next().charAt(0) - 48;

            //validation using placePiece function
            while (!placePiece(position, user)) {
                System.out.println("Please enter a valid position:");
                //using ascii in order to avoid Input Missmatch Exeption
                position = scanner.next().charAt(0) - 48;
            }

            //print game board after placing a piece
            printGameBoard();

            //check if user won
            if (isWinner(user)) {
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
            //if the user is in a position to win the computer should block unless it can't win
            int smartPosition = closeToWin(user); //get position required to block
            //if a position to block exists
            if (smartPosition != -1) {
                //if computer can win anyways, it wins and doesn't block
                int computerWinPosition = closeToWin(computer); //get position to win
                if (computerWinPosition != -1) placePiece(computerWinPosition, computer);
                    //if computer can't win, it blocks
                else placePiece(smartPosition, computer);
            }
            //otherwise, try to win
            else {
                //if user places first in one of the corners, computer places in middle
                if ((user.positions.contains(1) || user.positions.contains(3)
                        || user.positions.contains(7) || user.positions.contains(9))
                        && user.positions.size() == 1) {
                    placePiece(5, computer);

                }
                //if user places in 2 oposite conrners, place in 2 and since computer has middle, user have to place in 8
                else if (((user.positions.contains(1) && user.positions.contains(9)) || (user.positions.contains(7) && user.positions.contains(3)))
                        && user.positions.size() == 2) {
                    placePiece(2, computer);
                }
                //else try to win with smartPlacement function
                else smartPlacement(computer);

            }


            //print game board after placing a piece
            System.out.println("Computer placing:\n");
            printGameBoard();

            //check if computer won
            if (isWinner(computer)) {
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

        //middle without middle combinations
        if (player.positions.contains(1) && player.positions.contains(9) && FREE_POSITIONS.contains(5)) return 5;
        if (player.positions.contains(7) && player.positions.contains(3) && FREE_POSITIONS.contains(5)) return 5;
        if (player.positions.contains(2) && player.positions.contains(8) && FREE_POSITIONS.contains(5)) return 5;
        if (player.positions.contains(4) && player.positions.contains(6) && FREE_POSITIONS.contains(5)) return 5;

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


    //demo to make sure user understand the rules
    public static void runDemo() {
        //create dummy player for demo
        Player dummy = new Player();
        dummy.setCharacter('X');

        //start demo
        System.out.println("////////Start of demo://////////\n");

        //ask user to place an X in position 1
        System.out.println("Place an X in the top left ");

        //get position from user and validate
        Scanner scanner = new Scanner(System.in);
        //using ascii in order to avoid Input Missmatch exeption
        char position = scanner.next().charAt(0);
        while (position != 49) {
            System.out.println("Enter the correct position:");
            position = scanner.next().charAt(0);
        }

        //place an X and print to show the user
        placePiece(1, dummy);
        printGameBoard();

        //restart everything to prepare for the game
        init();

        //end demo
        System.out.println("You understand the rules and may play\n");


    }


    //print instructions
    public static void printInstructions() {
        System.out.println("////////Instructions://////////\n");
        //System.out.println("You are the first to start, at the moment you can only play vs the computer.\n");
        System.out.println("In each turn you are required to enter a 1-9 position in which you want to place your piece.\n");
        System.out.println("Here is an empty game board for example:\n");
        printGameBoard();
        System.out.println("The positions on the board are numbered as the the following:\n\n" +
                "1|2|3\n-+-+-\n4|5|6\n-+-+-\n7|8|9\n");
    }


    //smart placement for computer if a user is not in a position to win
    public static void smartPlacement(Player player) {
        //checks if a player has a winning position
        int winningPosition = closeToWin(player);
        //if he does, place in it
        if (winningPosition != -1) {
            placePiece(winningPosition, player);
        }
        //if he doesn't, try and place in all of the corners + middle, otherwise random placement
        else {
            //trying to place in every corner + middle
            for (int i = 1; i <= 9; i += 2) {
                //check if position is avaliable
                if (FREE_POSITIONS.contains(i)) {
                    placePiece(i, player);
                    return;
                }
            }

            //random placement
            int index = (int) (FREE_POSITIONS.size() * Math.random()); // get random index between 0 and FREE_POSITIONS.size() not including the latest
            int computerPosition = FREE_POSITIONS.get(index); //get position from the randomized index
            placePiece(computerPosition, player); //we can place without validation since we receive the index from the free positions

        }

    }

    //functions for game loop? clean main
    // make computer start as well

}




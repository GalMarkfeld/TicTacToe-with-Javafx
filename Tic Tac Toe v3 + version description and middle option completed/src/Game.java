import java.util.LinkedList;
import java.util.Scanner;

public class Game {


    //global variables
    public static char[][] GAME_BOARD;
    public static LinkedList<Integer> FREE_POSITIONS;
    public static int[][] WINNING_POSITIONS;


    //main method
    public static void main(String[] args) {
        //start message
        System.out.println("Welcome to Tic Tac Toe");
        System.out.println("------------------------\n");

        //call init function
        init();

        //create scanner object that receives input from keyboard
        Scanner scanner = new Scanner(System.in);

        //create players
        Player user = new Player();
        Player computer = new Player();

        //instructions
        printInstructions();

        //run demo
        runDemo(scanner);

        //start game
        start(user, computer, scanner);


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
    public static void runDemo(Scanner scanner) {
        //make sure user understand rules
        System.out.println("Do you understand the rules?");

        //get answer from user
        String answer = scanner.next();
        answer = answer.toLowerCase();

        //validate input
        while (!answer.equals("yes") && !answer.equals("no")) {
            System.out.println("Answer the question");
            answer = scanner.next();
        }

        //if user understand the rules return
        if (answer.equals("yes")) {
            System.out.println("There is no need for demo\n");
            return;
        }

        //start demo
        System.out.println("////////Start of demo://////////\n");

        //create dummy player for demo
        Player dummy = new Player();
        dummy.setCharacter('X');

        //explain how to place
        System.out.println("In order to place in the top left corner, press 1");

        //ask to place in the top right corner
        System.out.println("Now place an X in the top right corner");

        //get position from user and validate
        //using ascii in order to avoid Input Missmatch exeption
        char position = scanner.next().charAt(0);
        while (position != 51) {
            System.out.println("Enter the correct position:");
            position = scanner.next().charAt(0);
        }

        //place an X and print to show the user
        placePiece(3, dummy);
        printGameBoard();

        //restart everything to prepare for the game
        init();

        //end demo
        System.out.println("You may play\n");
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


    //checks if it is a draw
    public static boolean isDraw() {
        //if there are no positions left to place in and nobody won
        if (FREE_POSITIONS.isEmpty()) {
            System.out.println("It is a draw! \n" +
                    "game is over");
            return true;
        }
        return false;
    }

    //sets user to X or 0 and computer to the other one
    public static void setGameCharacters(Player user, Player computer, Scanner scanner) {
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

    }


    //computer place piece - smart solution
    public static void computerPlacement(Player user, Player computer) {
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
            //if user places first in one of the corners, computer places in middle which is a good counter for many strategies
            if ((user.positions.contains(1) || user.positions.contains(3)
                    || user.positions.contains(7) || user.positions.contains(9))
                    && user.positions.size() == 1) {
                placePiece(5, computer);

            }
            //if user places in 2 opposite conrners, computer places in 2 and since it has middle, user is forced to block with 8 and the game will go to draw
            //counters the 3 corners strategy
            else if (((user.positions.contains(1) && user.positions.contains(9)) || (user.positions.contains(7) && user.positions.contains(3)))
                    && user.positions.size() == 2) {
                placePiece(2, computer);
            }
            //else try to win with smartPlacement function
            else smartPlacement(computer);

        }

    }


    //start game loop if user starts
    public static void initGameLoop1(Player user, Player computer, Scanner scanner) {
        //start of game
        System.out.println("////////Start of game://////////\n");

        //set user and computer char
        setGameCharacters(user, computer, scanner);

        //game loop
        while (true) {

            //user's turn
            placeUser(user, scanner);

            //check if user won
            if (isWinner(user)) {
                System.out.println("Congratulations, you won!");
                break;
            }

            //check if it is a draw
            if (isDraw()) break;

            //computer turn
            computerPlacement(user, computer);

            //print game board after placing a piece
            System.out.println("Computer placing:\n");
            printGameBoard();

            //check if computer won
            if (isWinner(computer)) {
                System.out.println("You lost, don't be so bad next time");
                break;
            }

            //check if it is a draw
            if (isDraw()) break;

        }
    }


    //start game
    public static void start(Player user, Player computer, Scanner scanner) {
        //who starts
        System.out.println("Do you want to go first?");
        String answer = scanner.next();
        answer = answer.toLowerCase();

        //input validation
        while (!answer.equals("yes") && !answer.equals("no")) {
            System.out.println("Answer the question");
            answer = scanner.next();
        }

        //initiate game loop according to user answer
        if (answer.equals("yes")) initGameLoop1(user, computer, scanner);
        else initGameLoop2(user, computer, scanner);

    }


    //start game loop if computer starts ///////needs work
    public static void initGameLoop2(Player user, Player computer, Scanner scanner) {
        //start of game
        System.out.println("////////Start of game://////////\n");

        //set user and computer char
        setGameCharacters(user, computer, scanner);

        //start using strategy 1
        winningStrategy(user, computer, scanner);


    }


    //computer starts from a random corner and responds according to user's placemnt
    public static void winningStrategy(Player user, Player computer, Scanner scanner) {
        //the first move is to place in one of the corners
        //create list of corners
        LinkedList<Integer> corners = new LinkedList<>();

        //add corners
        corners.add(1);
        corners.add(3);
        corners.add(7);
        corners.add(9);

        // get random corner according to random index
        int randomCorner1 = corners.get((int) (Math.random() * 4));

        //remove randomCorner from corners for later
        corners.remove((Object) (randomCorner1));

        //place in it
        System.out.println("Computer placing:");
        placePiece(randomCorner1, computer);

        //print game board after placing a piece
        printGameBoard();

        //get and place user position for first move
        placeUser(user, scanner);

        //check if user placed in middle, if so computer only blocks and places randomly in corners
        if (user.positions.contains(5)) {

            //game loop
            while (true) {

                //computer turn
                computerPlacement(user, computer);

                //print game board after placing a piece
                System.out.println("Computer placing:\n");
                printGameBoard();

                //check if computer won
                if (isWinner(computer)) {
                    System.out.println("You lost, don't be so bad next time");
                    break;
                }

                //check if it is a draw
                if (isDraw()) break;

                //user's turn
                placeUser(user, scanner);

                //check if user won
                if (isWinner(user)) {
                    System.out.println("Congratulations, you won!");
                    break;
                }

                //check if it is a draw
                if (isDraw()) break;

            }

        }

        //if user placed in a corner the computer wins by placing in 3 corners
        else if (user.positions.contains(1) || user.positions.contains(3) || user.positions.contains(7) || user.positions.contains(9)) {
            //remove user's corner for later
            corners.remove((Object) user.positions.getFirst());

            //place in another random corner
            int randomCorner2 = corners.get((int) (Math.random() * 2)); //get random corner index

            //remove randomCorner from corners for later
            corners.remove((Object) (randomCorner2));

            //place in it
            System.out.println("Computer placing:");
            placePiece(randomCorner2, computer);

            //print game board
            printGameBoard();

            //user's second turn
            placeUser(user, scanner);

            //if user made a mistake and didn't block. computer wins
            if (closeToWin(computer) != -1) {
                System.out.println("Computer placing:");
                placePiece(closeToWin(computer), computer);
                printGameBoard();
                //end message
                System.out.println("You lost, don't be so bad next time");
                return;
            }

            //computer placing in the last corner
            System.out.println("Computer placing:");
            placePiece(corners.getFirst(), computer);

            //print game board
            printGameBoard();

            //user's third turn
            placeUser(user, scanner);

            //computer wins
            System.out.println("Computer placing:");
            placePiece(closeToWin(computer), computer);

            //print gameboard
            printGameBoard();

            //end message
            System.out.println("You lost, don't be so bad next time");

        }

        //if user placed in the middle of a row/column but not in middle the computer wins by creating an x shaped pattern in which it has 2 options to win
        else {
            //if user placed next to a corner
            int userFirst = user.positions.getFirst(); //get user position

            //find the corner which is not diagonal to the first one and not in the same row as the user's position and place
            for (int i = 0; i < 3; i++) {
                int corner = corners.get(i);
                if (!sameOutsideRowOrColumn(corner, userFirst) && !(diagonalCorners(corner, randomCorner1))) {
                    System.out.println("Computer placing:");
                    placePiece(corner, computer);
                    printGameBoard();
                    corners.remove((Object) corner);
                    break;
                }
            }

            //user second turn
            placeUser(user, scanner);

            //if user made a mistake and didn't block. computer wins
            if (closeToWin(computer) != -1) {
                System.out.println("Computer placing:");
                placePiece(closeToWin(computer), computer);
                printGameBoard();
                //end message
                System.out.println("You lost, don't be so bad next time");

            } else {
                //place in the middle and get to a 2 way win in an X shape
                System.out.println("Computer placing:");
                placePiece(5, computer);
                printGameBoard();

                //user's third turn
                placeUser(user, scanner);

                //computer wins
                System.out.println("Computer placing:");
                placePiece(closeToWin(computer), computer);

                //print gameboard
                printGameBoard();

                //end message
                System.out.println("You lost, don't be so bad next time");
            }

        }

    }


    //middle with 2/3 row strategy - will be picked 40% of times
    public static void winningStrategy2(Player user, Player computer, Scanner scanner) {

    }


    //unexpected strategy - easy to counter so will be picked only 20% of times
    public static void winningStrategy3(Player user, Player computer, Scanner scanner) {


    }


    //receives input position from user and places in it
    public static void placeUser(Player user, Scanner scanner) {
        System.out.println("Enter a valid position to place your piece:");
        //using ascii in order to avoid Input Missmatch Exeption
        int position = scanner.next().charAt(0) - 48;

        //validation using placePiece function
        while (!placePiece(position, user)) {
            System.out.println("Position taken! enter a new one:");
            //using ascii in order to avoid Input Missmatch Exeption
            position = scanner.next().charAt(0) - 48;
        }

        //print game board after placing a piece
        printGameBoard();
    }


    //checks if a position in middle of a row is next to a corner
    public static boolean nextToCorner(int corner, int position) {
        switch (position) {
            case (1):
                if (position == 2 || position == 4) return true;
                return false;
            case (3):
                if (position == 2 || position == 6) return true;
                return false;
            case (7):
                if (position == 8 || position == 4) return true;
                return false;
            case (9):
                if (position == 8 || position == 6) return true;
                return false;

        }
        return false;

    }


    //checks if 2 positions are in the same row or column but without middle
    public static boolean sameOutsideRowOrColumn(int position1, int position2) {
        //top row
        if (position1 == 1 && position2 == 2) return true;
        if (position1 == 1 && position2 == 3) return true;
        if (position1 == 2 && position2 == 1) return true;
        if (position1 == 2 && position2 == 3) return true;
        if (position1 == 3 && position2 == 1) return true;
        if (position1 == 3 && position2 == 2) return true;

        //left
        if (position1 == 1 && position2 == 4) return true;
        if (position1 == 1 && position2 == 7) return true;
        if (position1 == 4 && position2 == 1) return true;
        if (position1 == 4 && position2 == 7) return true;
        if (position1 == 7 && position2 == 4) return true;
        if (position1 == 7 && position2 == 1) return true;

        //bottom row
        if (position1 == 7 && position2 == 8) return true;
        if (position1 == 7 && position2 == 9) return true;
        if (position1 == 8 && position2 == 7) return true;
        if (position1 == 8 && position2 == 9) return true;
        if (position1 == 9 && position2 == 7) return true;
        if (position1 == 9 && position2 == 8) return true;

        //left
        if (position1 == 3 && position2 == 6) return true;
        if (position1 == 3 && position2 == 9) return true;
        if (position1 == 6 && position2 == 3) return true;
        if (position1 == 6 && position2 == 9) return true;
        if (position1 == 9 && position2 == 3) return true;
        if (position1 == 9 && position2 == 6) return true;

        return false;

    }


    //check if corners are diagonal
    public static boolean diagonalCorners(int corner1, int corner2) {
        if (corner1 == 1 && corner2 == 9) return true;
        if (corner1 == 9 && corner2 == 1) return true;
        if (corner1 == 7 && corner2 == 3) return true;
        if (corner1 == 3 && corner2 == 7) return true;

        return false;
    }


}




package com.example.tictactoev4;

import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class HelloApplication extends Application {

    //app details
    final LinearGradient APP_COLOR = new LinearGradient(0, 0, 1, 1, true, null, new Stop(0, Color.AQUA), new Stop(1, Color.LIGHTYELLOW), new Stop(0.5, Color.AQUAMARINE));
    final double APP_WIDTH = 580;
    final double APP_HEIGHT = 580;


//    static Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
//    final double APP_WIDTH = bounds.getWidth() / 2;
//    final double APP_HEIGHT = bounds.getHeight() * 0.75;


    //Winning streaks represented with a 2d array
    final int[][] WINNING_POSITIONS = new int[][]{
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9},
            {1, 4, 7},
            {2, 5, 8},
            {3, 6, 9},
            {1, 5, 9},
            {3, 5, 7}
    };

    //set players and default chars
    Player PLAYER_1 = new Player('X', 1);
    Player PLAYER_2 = new Player('O', 2);

    //iterator in order to check whose turn it is, will get initialized at Scene 5
    Player ITERATOR;

    //Available positions list, will get initialize with initPos method
    public LinkedList<Integer> FREE_POSITIONS;


    @Override
    public void start(Stage stage) throws IOException {

        //initializing free positions
        initFreePos();
        //set app icon in task bar
        stage.getIcons().add(new Image("file:TicTacToe.jpg"));
        //set app width
        stage.setWidth(APP_WIDTH);
        //set app height
        stage.setHeight(APP_HEIGHT);
        //user can't resize app
        stage.setResizable(false);
        //fix the alignment bug
        stage.sizeToScene();
        //app title
        stage.setTitle("Tic Tac Toe");
        //start with the first scene
        stage.setScene(createStartScene(stage));
        //show scenes
        stage.show();


    }


    /////////////////////////scenes////////////////////////////////


    //Scene 1 - start scene with welcome text and photo of the game
    public Scene createStartScene(Stage stage) {

        //create Welcome text
        Text welcome = createText("Welcome To Tic Tac Toe!");

        //create start button
        Button button = createButton("Start Game");
        //add event: when clicked we move on to scene 2
        button.setOnMouseClicked(mouseEvent -> stage.setScene(createSelectGameModeScene(stage)));

        //load game image from project library
        Image image = new Image("file:TicTacToe.jpg"); //photo is in project library so we can use shortcut instead of a full path
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true); //preserve initial ratio of photo
        imageView.setFitHeight(100); //when we set preserve ratio to true , setting the height sets the width automatically


        return createVboxScene(welcome, imageView, button);
    }


    //Scene 2 - select game mode scene, 1 0r 2 players. currently, 1 player isn't available
    public Scene createSelectGameModeScene(Stage stage) {


        //create select game mode text
        Text headLine = createText("Select Game Mode:");

        //create buttons
        Button button1 = createButton("1 player");
        Button button2 = createButton("2 players");
        //event for button 1: if clicked present a coming soon message and the possibility to continue to 2 players
        button1.setOnMouseClicked(mouseEvent -> stage.setScene(createVboxScene(createText("Coming Soon..."), button2)));
        //event for button 2: continue to scene 3
        button2.setOnMouseClicked(mouseEvent -> stage.setScene(createPlayer1DetailsScene(stage)));


        return createVboxScene(headLine, button1, button2);
    }


    //Scene 3 - set player 1's character and name
    public Scene createPlayer1DetailsScene(Stage stage) {
        //create player Name text for scene headline
        Text playerName = createText("Player 1");

        //create text field
        TextField nameField = new TextField("Player 1's name");
        nameField.setMaxWidth(100);

        //create hbox for text field and for player 1's radio buttons
        HBox hBox = new HBox(20);
        hBox.setAlignment(Pos.CENTER);

        //create radio buttons
        RadioButton X = new RadioButton("X");
        //we set the x button to get auto selected and player 1's initial char to x to avoid the user not pressing any of 0 or x
        X.setSelected(true);
        RadioButton O = new RadioButton("O");

        //create events
        X.setOnMouseClicked(mouseEvent -> PLAYER_1.setCharacter('X'));
        O.setOnMouseClicked(mouseEvent -> PLAYER_1.setCharacter('O'));

        //create toggle group and add buttons so both buttons won't get pressed at the same time
        ToggleGroup toggleGroup = new ToggleGroup();
        X.setToggleGroup(toggleGroup);
        O.setToggleGroup(toggleGroup);

        //add buttons and name field to hbox
        hBox.getChildren().addAll(X, O, nameField);

        //create continue button
        Button continueButton = createButton("continue");
        //create event: when the button gets clicked set player 1's to whatever is in the name field and move on to scene 4
        continueButton.setOnMouseClicked(mouseEvent -> {
            PLAYER_1.setName(nameField.getText());
            stage.setScene(createPlayer2DetailsScene(stage));
        });


        return createVboxScene(playerName, hBox, continueButton);

    }


    //Scene 4 - set player 2's character and name
    public Scene createPlayer2DetailsScene(Stage stage) {
        //create player Name text for scene headline
        Text playerName = createText("Player 2");

        //create text field
        TextField nameField = new TextField("Player 2's name");
        nameField.setMaxWidth(100);

        //set player 2's playing char according to player 1
        if (PLAYER_1.getCharacter() == 'O') PLAYER_2.setCharacter('X');
        else PLAYER_2.setCharacter('O');


        //create continue button
        Button continueButton = createButton("continue");
        //create event: when the button gets clicked set player 2's to whatever is in the name field and move on to scene 4
        continueButton.setOnMouseClicked(mouseEvent -> {
            PLAYER_2.setName(nameField.getText());
            stage.setScene(createWhoStartsScene(stage));
        });


        return createVboxScene(playerName, nameField, continueButton);

    }


    //Scene 5 - sets who start
    public Scene createWhoStartsScene(Stage stage) {

        //create scene headline
        Text healine = createText("Who Starts?");

        //create buttons
        Button player1 = createButton(PLAYER_1.getName());
        Button player2 = createButton(PLAYER_2.getName());
        //create events: if player 1 gets clicked change iterator to player 1 and the same for player 2. Then move to the next scene
        player1.setOnMouseClicked(mouseEvent -> {
            ITERATOR = PLAYER_1;
            stage.setScene(createGameBoardScene(stage));
        });
        player2.setOnMouseClicked(mouseEvent -> {
            ITERATOR = PLAYER_2;
            stage.setScene(createGameBoardScene(stage));
        });

        return createVboxScene(healine, player1, player2);

    }


    //Scene 6 - creates the game board from a pane and calculated coordinates and the events for when the rectangles will get clicked.
    //For the game board: We will use a black rectangle and place 9 smaller rectangles in the app color
    //on top of it in order to create the black lines between them. We are using this and not something simpler in order to avoid double borders
    public Scene createGameBoardScene(Stage stage) {

        //create stage background
        Rectangle background = new Rectangle(APP_WIDTH, APP_HEIGHT, APP_COLOR);
        //place the top left corner of the background in 0,0
        background.setX(0);
        background.setY(0);

        //create black rectangle for the separating lines
        Rectangle gameBackground = new Rectangle(APP_WIDTH / 2, APP_HEIGHT / 2, Color.BLACK);
        //place it in a calculated coords
        gameBackground.setX(APP_WIDTH / 4);
        gameBackground.setY(APP_WIDTH / 4);

        //create variables to be used in calculations
        double borderLine = 5; //black border between colored rectangles
        double side = 90; //side of each colored rectangles
        double start = gameBackground.getX() + borderLine; // coordinate of the first colored rectangle
        double jump = side + borderLine; //coordinate of the difference between the colored rectangles

        //create pane, the pane dimensions will get auto selected according to the largest node (background in our case)
        Pane pane = new Pane(background, gameBackground);


        //we will create the rectangles 1 by 1 and not with a for loop since
        // you can't use non-final variable inside a lambda expression, and we need each rectangle to represented its own number from 1 to 9

        //create all of the rectangles with sidexside dimensions and in the app color
        //each one will have the calculated x,y and call the place method with its own number when clicked

        Rectangle rectangle1 = new Rectangle(side, side, APP_COLOR);
        rectangle1.setX(start);
        rectangle1.setY(start);
        rectangle1.setOnMouseClicked(mouseEvent -> place(rectangle1, 1, ITERATOR, pane, stage));


        Rectangle rectangle2 = new Rectangle(side, side, APP_COLOR);
        rectangle2.setX(start + jump);
        rectangle2.setY(start);
        rectangle2.setOnMouseClicked(mouseEvent -> place(rectangle2, 2, ITERATOR, pane, stage));

        Rectangle rectangle3 = new Rectangle(side, side, APP_COLOR);
        rectangle3.setX(start + 2 * jump);
        rectangle3.setY(start);
        rectangle3.setOnMouseClicked(mouseEvent -> place(rectangle3, 3, ITERATOR, pane, stage));

        Rectangle rectangle4 = new Rectangle(side, side, APP_COLOR);
        rectangle4.setX(start);
        rectangle4.setY(start + jump);
        rectangle4.setOnMouseClicked(mouseEvent -> place(rectangle4, 4, ITERATOR, pane, stage));

        Rectangle rectangle5 = new Rectangle(side, side, APP_COLOR);
        rectangle5.setX(start + jump);
        rectangle5.setY(start + jump);
        rectangle5.setOnMouseClicked(mouseEvent -> place(rectangle5, 5, ITERATOR, pane, stage));

        Rectangle rectangle6 = new Rectangle(side, side, APP_COLOR);
        rectangle6.setX(start + 2 * jump);
        rectangle6.setY(start + jump);
        rectangle6.setOnMouseClicked(mouseEvent -> place(rectangle6, 6, ITERATOR, pane, stage));

        Rectangle rectangle7 = new Rectangle(side, side, APP_COLOR);
        rectangle7.setX(start);
        rectangle7.setY(start + 2 * jump);
        rectangle7.setOnMouseClicked(mouseEvent -> place(rectangle7, 7, ITERATOR, pane, stage));

        Rectangle rectangle8 = new Rectangle(side, side, APP_COLOR);
        rectangle8.setX(start + jump);
        rectangle8.setY(start + 2 * jump);
        rectangle8.setOnMouseClicked(mouseEvent -> place(rectangle8, 8, ITERATOR, pane, stage));

        Rectangle rectangle9 = new Rectangle(side, side, APP_COLOR);
        rectangle9.setX(start + 2 * jump);
        rectangle9.setY(start + 2 * jump);
        rectangle9.setOnMouseClicked(mouseEvent -> place(rectangle9, 9, ITERATOR, pane, stage));

        //add rectangles to pane
        pane.getChildren().addAll(rectangle1, rectangle2, rectangle3, rectangle4, rectangle5
                , rectangle6, rectangle7, rectangle8, rectangle9);

        //create and return the scene with the pane
        return new Scene(pane);
    }


    //Scene 7 - creates the end scene and present an appropriate message, user win count and the possibility to play again
    public Scene createEndScene(Stage stage, int mode) {

        //make headline text empty in order to get used in the switch
        Text endText = createText("");

        //sets the end text according to mode
        switch (mode) {
            case 1:
                endText = createText(PLAYER_1.getName() + " Won!");
                break;
            case 2:
                endText = createText(PLAYER_2.getName() + " Won!");
                break;
            case 0:
                endText = createText("It Is A Draw!");
            default:
                break;
        }

        //player 1 name and win count
        Text player1Count = createText(PLAYER_1.getName() + ": " + PLAYER_1.winCount);

        //player 2 name and win count
        Text player2Count = createText(PLAYER_2.getName() + ": " + PLAYER_2.winCount);

        //add players names and win count to and hBox in order to get horizontally displayed
        HBox hBox = new HBox(player1Count, player2Count);
        hBox.setAlignment(Pos.CENTER); //set hBox alignment
        hBox.setSpacing(40); //set space between node

        //play again and close buttons
        Button playAgain = createButton("Play Again");
        Button close = createButton("Close");

        //event: when clicked call the next scene
        playAgain.setOnMouseClicked(mouseEvent -> stage.setScene(createPrepareScene(stage)));

        //event: close app when clicked
        close.setOnMouseClicked(mouseEvent -> stage.close());


        return createVboxScene(endText, hBox, playAgain, close);
    }


    //Scene 8 - prepare for a new game with the same players. They can switch the playing chars and who starts
    public Scene createPrepareScene(Stage stage) {

        //text for selecting char for player 1
        Text headLine = createText("Choose X or O For " + PLAYER_1.getName());

        //create x and o radio buttons
        RadioButton x = new RadioButton("X");
        RadioButton O = new RadioButton("O");

        //create toggle group to prevent both buttons from getting pressed
        ToggleGroup toggleGroup = new ToggleGroup();
        //add buttons to toggle group
        x.setToggleGroup(toggleGroup);
        O.setToggleGroup(toggleGroup);

        //if nothing is pressed, set default char for players
        x.setSelected(true);
        PLAYER_1.setCharacter('X');
        PLAYER_2.setCharacter('O');

        //create hbox fro radio buttons to be displayed horizontally
        HBox hBox = new HBox(20); //set hbox spacing
        hBox.setAlignment(Pos.CENTER); //set hbox alignment
        hBox.getChildren().addAll(x, O); //add radio buttons to hbox

        //event: set player's playing chars according to the button that gets clicked
        x.setOnMouseClicked(mouseEvent -> {
            PLAYER_1.setCharacter('X');
            PLAYER_2.setCharacter('O');
        });
        O.setOnMouseClicked(mouseEvent -> {
            PLAYER_1.setCharacter('O');
            PLAYER_2.setCharacter('X');
        });

        //who starts text
        Text whoStarts = createText("Who starts?");

        //buttons
        Button player1 = createButton(PLAYER_1.getName());
        Button player2 = createButton(PLAYER_2.getName());

        //initialize free positions for a new game
        initFreePos();

        //reset every player positions
        PLAYER_1.resetPos();
        PLAYER_2.resetPos();


        //if a player's button gets clicked, change the iterator to that player and start a new game
        player1.setOnMouseClicked(mouseEvent -> {
            ITERATOR = PLAYER_1;
            stage.setScene(createGameBoardScene(stage));
        });
        player2.setOnMouseClicked(mouseEvent -> {
            ITERATOR = PLAYER_2;
            stage.setScene(createGameBoardScene(stage));
        });


        return createVboxScene(headLine, hBox, whoStarts, player1, player2);

    }


    /////////////////////////homogeneity functions////////////////////////////////

    //The next methods will make every button, text and scene in this app the same.
    //In this way will be able to change,add or remove details easily throughout the whole app


    //sets a text to a specific font, color and stroke color
    private Text createText(String string) {
        Text text = new Text(string);
        text.setFill(Color.BLACK);
        text.setStroke(Color.BLACK);
        text.setFont(Font.loadFont("file:Roboto-Thin.ttf",50));
        return text;
    }


    //sets a button to a specific width
    private Button createButton(String string) {
        Button button = new Button(string);
        button.setMaxWidth(80);
        return button;
    }

    //creates an app scene.
    //Using the varargs feature, adds the input nodes to a vbox and places the box on top of a rectangle in the app dimensions
    //and color using a stackpane
    public Scene createVboxScene(Node... nodes) {

        //create the vbox
        VBox vbox = new VBox(20); //set vertical space between nodes
        vbox.setAlignment(Pos.CENTER); //set alignment

        //add all the nodes to the vbox
        for (Node node : nodes) {
            vbox.getChildren().add(node);
        }

        //create the stackpane
        StackPane stackPane = new StackPane();


        //create the scene background from a rectangle with the app dimensions and app color
        Rectangle background = new Rectangle(APP_WIDTH, APP_HEIGHT, APP_COLOR);


        //add background and vbox to the stack pane
        stackPane.getChildren().addAll(background, vbox);

        //return a scene created from the stackpane and in the app dimensions (the stackpane recieves its biggest node's dimensions, the backgroung
        // rectangle in our case)
        return new Scene(stackPane);

    }


    ///////////////////////////////////game functions///////////////////////////////////////


    //creates a black  x on top of the input rectangle
    private Group createX(Rectangle rectangle) {

        //get rectangle coordinates
        double recX = rectangle.getX();
        double recY = rectangle.getY();

        //create lines
        Line line1 = new Line();
        Line line2 = new Line();

        //set line coordinates to a calculated values according to the input rectangle
        line1.setStartX(recX + 5);
        line1.setStartY((recY + 5));
        line1.setEndX(recX + 85);
        line1.setEndY(recY + 85);
        line2.setStartX(recX + 85);
        line2.setStartY(recY + 5);
        line2.setEndX(recX + 5);
        line2.setEndY(recY + 85);

        //return a group with the lines (lines doesn't work without a group)
        return new Group(line1, line2);
    }


    //creates a black  x on top of the input rectangle
    private Circle createO(Rectangle rectangle) {

        //create a circle with a calculated coordinates based on the input rectangle and a set radius
        Circle circle = new Circle(rectangle.getX() + 45, rectangle.getY() + 45, 40);

        //set the circle's color to app color
        circle.setFill(APP_COLOR);

        //set the stroke color to black
        circle.setStroke(Color.BLACK);

        //return the circle
        return circle;
    }

    //initiates free positions
    public void initFreePos() {
        //set free positions to 1-9
        FREE_POSITIONS = new LinkedList<>();
        for (int i = 1; i <= 9; i++) {
            FREE_POSITIONS.add(i);
        }

    }


    //if possible, draws the player's char in the free clicked rectangle and adds the rectangle number to the player's positions
    //checks if the player won or if it ended as a draw after each placement
    public void place(Rectangle rectangle, int recNum, Player player, Pane pane, Stage stage) {

        //if remove was successful the position was available and placement was good
        //else the user will just press another rectangle
        if (FREE_POSITIONS.remove((Object) recNum)) {

            //draw the player's char on the pane
            if (player.getCharacter() == 'X') {
                pane.getChildren().add(createX(rectangle));
            } else {
                pane.getChildren().add(createO(rectangle));
            }

            //add rectangle number to player's positions
            player.positions.add(recNum);

            //change iterator
            if (player.getPlayerNumber() == 1) ITERATOR = PLAYER_2;
            else ITERATOR = PLAYER_1;

            //check if the player won after placement. For each winning streak, we check if the player's positions list contains
            // all the positions in that streak
            for (int[] sequence : WINNING_POSITIONS) {
                if (player.positions.contains(sequence[0]) && player.positions.contains(sequence[1]) && player.positions.contains(sequence[2])) {

                    //if the player won, we increment its win count and go to end scene
                    player.winCount++;
                    //advance to the end scene with the winning player game mode
                    stage.setScene(createEndScene(stage, player.getPlayerNumber()));

                }

            }

            //we check for draw after the check for win because the player may win on the final round.
            //We can just check if the free positions list is empty and if nobody won until now. it is a draw
            if (FREE_POSITIONS.isEmpty()) {
                //advance to the end scene with draw game mode
                stage.setScene(createEndScene(stage, 0));
            }

        }

    }


    //main method
    public static void main(String[] args) {
        launch();


    }
}

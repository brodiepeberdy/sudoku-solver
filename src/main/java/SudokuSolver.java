import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.collections.ObservableList;

import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Button;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
 
public class SudokuSolver extends Application {
  sudoku.SolutionFinder solutionFinder;
  TextField[][] squares = new TextField[9][9];
  @Override
  public void start(Stage stage) {

    GridPane gridPane = new GridPane();
    gridPane.setStyle("-fx-background-color: WHITE;");
    gridPane.setMinSize(600, 600);
    gridPane.setPadding(new Insets(10, 10, 10, 10));
    gridPane.setVgap(5);
    gridPane.setHgap(5);
    gridPane.setAlignment(Pos.CENTER);

    GridPane sudokuPane = new GridPane();
    sudokuPane.setStyle("-fx-background-color: WHITE;");
    sudokuPane.setMinSize(450, 450);
    gridPane.add(sudokuPane, 0, 2);

    Text titleText = new Text("Sudoku Solver");
    titleText.setStyle("-fx-font: normal bolder 40px 'sans-serif';");
    gridPane.add(titleText, 0, 0);

    Text descText = new Text("This application uses a backtracking algorithm to solve an entered Sudoku puzzle.");
    descText.setStyle("-fx-font: normal 20px 'sans-serif';");
    gridPane.add(descText, 0, 1);

    Button solvePuzzle = new Button("Solve Sudoku Puzzle");
    solvePuzzle.setStyle("-fx-background-color: ROYALBLUE; -fx-text-fill: white;");
    solvePuzzle.setOnAction(new EventHandler<ActionEvent>(){
      @Override
      public void handle(final ActionEvent AE){
        int[][] nums = new int[9][9];
        for (int y = 0; y < 9; y++){
          for (int x = 0; x < 9; x++){
            try {
               nums[y][x] = Integer.parseInt(squares[y][x].getText());
            }
            catch (NumberFormatException e)
            {
               nums[y][x] = 0;
            }
          }
        }
        sudoku.Sudoku puzzle = new sudoku.Sudoku(nums);
        solutionFinder = new sudoku.SolutionFinder(puzzle);
      }
    });
    gridPane.add(solvePuzzle, 0, 3);

    for (int y = 0; y < 9; y++){
      for (int x = 0; x < 9; x++){
        TextField square = new TextField();
        square.setMinSize(50,50);
        square.setMaxSize(50,50);
        square.setAlignment(Pos.CENTER);
        square.setTextFormatter(new TextFormatter<>(change -> {
          if (change.getControlNewText().matches("[1-9]?")) {
            return change;
          }
          return null;
        }));
        square.setStyle("-fx-border-color: top right bottom left; -fx-border-color: transparent transparent transparent transparent; -fx-focus-color: PURPLE;");
        if (x % 3 == 0 && x != 0 && y % 3 == 0 && y != 0){
          square.setStyle("-fx-border-color: top right bottom left; -fx-border-color: black transparent transparent black; -fx-focus-color: PURPLE;");
        }
        else if (x % 3 == 0 && x != 0){
          square.setStyle("-fx-border-color: top right bottom left; -fx-border-color: transparent transparent transparent black; -fx-focus-color: PURPLE;");
        }
        else if (y % 3 == 0 && y != 0){
          square.setStyle("-fx-border-color: top right bottom left; -fx-border-color: black transparent transparent transparent; -fx-focus-color: PURPLE;");
        }
        sudokuPane.add(square, x, y);
        squares[y][x] = square;
      }
    }

    Scene scene = new Scene(gridPane);
    stage.setTitle("Sudoku Solver");
    stage.setScene(scene);
    stage.show();
  }
    public static void main(String args[]){
       launch(args);
    }
}

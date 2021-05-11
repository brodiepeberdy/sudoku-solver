package sudoku;
import java.util.List;
import java.util.ArrayList;

public class SolutionFinder {
  private int[][] puzzle;

  // Constructor
  public SolutionFinder (int[][] puzzle){
    this.puzzle = puzzle;
  }

  // Checks that the puzzle is valid (solvable), then calculates the solution.
  public int[][] findSolution(){
    boolean valid = true;
    // Checks whether there are duplicate values (apart from the default/empty
    // value given a '0') in rows and columns.
    for (int y = 0; y < 9; y++){
      for (int x = 0; x < 9; x++){
        for (int z = 0; z < 9; z++){
          if (puzzle[y][x] == puzzle[y][z] && x != z && puzzle[y][x] != 0){
            valid = false;
          }
          else if (puzzle[x][y] == puzzle[z][y] && x != z && puzzle[x][y] != 0){
            valid = false;
          }
        }
        // Checks for clashes in the current 3x3 square of the grid for
        // each "coordinate" in the grid.
        List<Integer> known = new ArrayList<Integer>();
        for (int r = y - y % 3; r < y - y % 3 + 3; r++){
          for (int d = x - x % 3; d < x - x % 3 + 3; d++){
            if(known.contains(puzzle[r][d])){
                valid = false;
            }
            else if (puzzle[r][d] != 0){
              known.add(puzzle[r][d]);
            }
          }
        }
      }
    }

    // If the puzzle is solvable, then it is solved accordingly.
    if (valid == true){
      if(solvePuzzle(puzzle)){
        return puzzle;
      }
      else{
        return null;
      }
    }
    else{
      return null;
    }
  }

  // Recursive function that finds possible value combinations for each item
  // that don't invalidate the entire puzzle grid. If a value can't be found,
  // then the algorithm 'backtracks' to try some different values in
  // previous items.
  public boolean solvePuzzle(int[][] puzzle){
    int row = -1; int column = -1; boolean complete = true;
    for (int i = 0; i < 9; i++){
        for (int j = 0; j < 9; j++){
            if (puzzle[i][j] == 0){
                row = i; column = j;
                complete = false;
                break;
            }
        }
        if (!complete) {
            break;
        }
    }
    if (complete){
        return true;
    }

    for (int num = 1; num <= 9; num++){
        if (isValid(puzzle, row, column, num)){
            puzzle[row][column] = num;
            if (solvePuzzle(puzzle)){
                return true;
            }
            else{
                // replace it
                puzzle[row][column] = 0;
            }
        }
    }
    return false;
  }

  // Checks whether given a Sudoku puzzle, the row and column index of
  // which the current item is being calculated is located in, and the
  // number which is being tested over.
  public boolean isValid(int[][] puzzle, int row, int column, int num){
    // Checks for clashes in the row and column.
    for (int i = 0; i < 9; i++){
        if (puzzle[row][i] == num || puzzle[i][column] == num) {
            return false;
        }
    }
    // Checks for clashes in the current 3x3 square of the grid.
    for (int r = row - row % 3; r < row - row % 3 + 3; r++){
        for (int d = column - column % 3; d < column - column % 3 + 3; d++){
            if (puzzle[r][d] == num){
                return false;
            }
        }
    }
    // No clashes found
    return true;
  }
}

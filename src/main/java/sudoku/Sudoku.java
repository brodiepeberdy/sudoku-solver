package sudoku;

import java.util.List;
import java.util.ArrayList;

public class Sudoku {
  private int[][] squares = new int[9][9];
  public Sudoku (int[][] squares){
    this.squares = squares;
  }
  public int[][] getPuzzle(){
    return squares;
  }
  public void setPuzzle(int[][] squares){
    this.squares = squares;
  }
}

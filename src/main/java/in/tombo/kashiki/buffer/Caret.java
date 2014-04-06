package in.tombo.kashiki.buffer;

public class Caret implements Comparable<Caret> {
  private int col = 0;
  private int row = 0;

  public Caret(int row, int col) {
    this.row = row;
    this.col = col;
  }

  @Override
  public int compareTo(Caret o) {
    int rowCompare = Integer.compare(this.row, o.row);
    if (rowCompare == 0) {
      return Integer.compare(this.col, o.col);
    } else {
      return rowCompare;
    }
  }

  @Override
  public boolean equals(Object o) {
    return compareTo((Caret) o) == 0;
  }

  @Override
  public String toString() {
    return "[row:" + row + ", col:" + col + "]";
  }

  public void incCol(int gain) {
    col += gain;
  }

  public void incRow(int gain) {
    row += gain;
  }

  public void decCol(int gain) {
    incCol(-gain);
  }

  public void decRow(int gain) {
    incRow(-gain);
  }

  public int getCol() {
    return col;
  }

  public void setCol(int col) {
    this.col = col;
  }

  public int getRow() {
    return row;
  }

  public void setRow(int row) {
    this.row = row;
  }
}

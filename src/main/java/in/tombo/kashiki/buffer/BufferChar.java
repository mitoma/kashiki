package in.tombo.kashiki.buffer;

public class BufferChar implements Comparable<BufferChar> {

  private String c;
  private int row;
  private int col;
  private BufferCharObserver observer = new BufferCharObserver();

  public BufferChar(String c, int row, int col) {
    this.c = c;
    this.row = row;
    this.col = col;
  }

  public void addListener(BufferCharListener listener) {
    observer.addListener(listener);
  }

  public void updatePosition(int row, int col) {
    this.row = row;
    this.col = col;
    observer.update(this);
  }

  public String getChar() {
    return c;
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  @Override
  public int compareTo(BufferChar o) {
    int rowCompare = Integer.compare(row, o.row);
    if (rowCompare != 0) {
      return rowCompare;
    }
    return Integer.compare(col, o.col);
  }
}

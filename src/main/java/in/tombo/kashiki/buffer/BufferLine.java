package in.tombo.kashiki.buffer;

import java.util.LinkedList;
import java.util.List;

public class BufferLine implements Comparable<BufferLine> {

  private int rowNum;
  private List<BufferChar> chars = new LinkedList<>();
  private BufferLineObserver observer = new BufferLineObserver();

  public void addListener(BufferLineListener listener) {
    observer.addListener(listener);
  }

  public void updatePosition(int rowNum) {
    this.rowNum = rowNum;
    for (int i = 0; i < chars.size(); i++) {
      chars.get(i).updatePosition(rowNum, i);
    }
    observer.update(this);
  }

  public int getLength() {
    return chars.size();
  }

  public String toLineString() {
    StringBuilder buf = new StringBuilder();
    for (BufferChar bc : chars) {
      buf.append(bc.getChar());
    }
    return buf.toString();
  }

  public String posString(int position) {
    return chars.get(position).getChar();
  }

  public void insertChar(int col, String c) {
    BufferChar bc = new BufferChar(c, rowNum, col);
    chars.add(col, bc);
    updatePosition(rowNum);
    observer.addChar(bc);
  }

  public List<BufferChar> insertEnter(int col) {
    List<BufferChar> results = new LinkedList<>();
    if (col == chars.size()) {
      return results;
    }
    while (chars.size() > col) {
      results.add(chars.remove(col));
    }
    observer.update(this);
    return results;
  }

  public BufferChar removeChar(int col) {
    BufferChar removedChar = chars.remove(col);
    observer.removeChar(removedChar);
    return removedChar;
  }

  public void join(BufferLine line) {
    for (BufferChar bc : line.chars) {
      chars.add(bc);
    }
    updatePosition(rowNum);
  }

  public List<BufferChar> getChars() {
    return chars;
  }

  public int getRowNum() {
    return rowNum;
  }

  @Override
  public int compareTo(BufferLine o) {
    return Integer.compare(rowNum, o.rowNum);
  }
}

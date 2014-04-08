package in.tombo.kashiki.buffer;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Buffer {
  String bufferName;
  Caret currentCaret = new Caret(0, 0);
  Caret mark = new Caret(0, 0);
  LinkedList<BufferLine> lines = new LinkedList<BufferLine>();
  BufferObserver observer = new BufferObserver();

  public Buffer(String bufferName, String value) {
    this.bufferName = bufferName;
    BufferLine bl = new BufferLine();
    lines.add(bl);
    observer.addLine(bl);
    update();
    insertString(value);
  }

  public void addListener(BufferListener listener) {
    observer.addListener(listener);
  }

  private void update() {
    for (int i = 0; i < lines.size(); i++) {
      lines.get(i).updatePosition(i);
    }
    observer.update(this);
    observer.updateCaret(currentCaret);
  }

  public void insertString(String string) {
    String[] values = string.split("(\r\n|\n)");
    if (values.length == 0) {
      insertEnter();
    } else if (values.length == 1) {
      values[0].codePoints().forEach(
          codePoint -> insertChar(new String(Character.toChars(codePoint))));
    } else {
      Arrays.stream(values).forEach(v -> {
        v.codePoints().forEach(codePoint -> insertChar(new String(Character.toChars(codePoint))));
        insertEnter();
      });
    }
  }

  public void insertChar(String c) {
    currentLine().insertChar(currentCaret.getCol(), c);
    currentCaret.incCol(1);
    observer.updateCaret(currentCaret);
  }

  public void insertEnter() {
    BufferLine currentLine = currentLine();
    BufferLine nextLine = new BufferLine();
    List<BufferChar> leaveChars = currentLine.insertEnter(currentCaret.getCol());
    lines.add(currentCaret.getRow() + 1, nextLine);
    update();
    observer.addLine(nextLine);
    leaveChars.stream().forEach(c -> {
      nextLine.getChars().add(c);
      observer.moveChar(currentLine, nextLine, c);
    });
    currentCaret.setCol(0);
    currentCaret.incRow(1);
    observer.updateCaret(currentCaret);
  }

  public int getMaxColNum() {
    return lines.stream().max((l, r) -> Integer.compare(l.getLength(), r.getLength()))
        .orElse(new BufferLine()).getLength();
  }

  public LinkedList<BufferLine> getLines() {
    return lines;
  }

  public BufferLine currentLine() {
    return lines.get(currentCaret.getRow());
  }

  public BufferLine preLine() {
    if (currentCaret.getRow() == 0) {
      return null;
    }
    return lines.get(currentCaret.getRow() - 1);
  }

  public BufferLine postLine() {
    if (currentCaret.getRow() == lines.size() - 1) {
      return null;
    }
    return lines.get(currentCaret.getRow() - 1);
  }

  public String toBufferString() {
    return lines.stream().map((l) -> l.toLineString()).collect(Collectors.joining("\n"));
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    buf.append(toBufferString());
    buf.append(String.format("Caret:[%d,%d]", currentCaret.getCol(), currentCaret.getRow()));
    return buf.toString();
  }

  public Caret getCaret() {
    return currentCaret;
  }

  public void backspace() {
    if (isBufferHead() && isLineHead()) {
      return;
    }
    back();
    delete();
  }

  public void delete() {
    if (isBufferLast() && isLineLast()) {
      return;
    } else if (!isLineLast()) {
      currentLine().removeChar(currentCaret.getCol());
    } else if (isLineLast() && !isBufferLast()) {
      BufferLine currentLine = currentLine();
      BufferLine removedLine = lines.remove(currentCaret.getRow() + 1);
      currentLine.join(removedLine);
      removedLine.getChars().stream().forEach(c -> observer.moveChar(removedLine, currentLine, c));
      observer.removeLine(removedLine);
    }
  }

  public void head() {
    currentCaret.setCol(0);
    observer.updateCaret(currentCaret);
  }

  public void last() {
    currentCaret.setCol(currentLine().getLength());
    observer.updateCaret(currentCaret);
  }

  public void back() {
    if (isLineHead()) {
      boolean isDocHead = isBufferHead();
      previous();
      if (!isDocHead) {
        last();
      }
      return;
    }
    currentCaret.decCol(1);
    observer.updateCaret(currentCaret);
  }

  public void forward() {
    if (isLineLast()) {
      boolean isDocLast = isBufferLast();
      next();
      if (!isDocLast) {
        head();
      }
      return;
    }
    currentCaret.incCol(1);
    observer.updateCaret(currentCaret);
  }

  public void previous() {
    if (isBufferHead()) {
      return;
    }
    currentCaret.decRow(1);
    if (currentCaret.getCol() > currentLine().getLength()) {
      last();
    }
    observer.updateCaret(currentCaret);
  }

  public void next() {
    if (isBufferLast()) {
      return;
    }
    currentCaret.incRow(1);
    if (currentCaret.getCol() > currentLine().getLength()) {
      last();
    }
    observer.updateCaret(currentCaret);
  }

  public void bufferHead() {
    currentCaret.setRow(0);
    currentCaret.setCol(0);
    observer.updateCaret(currentCaret);
  }

  public void bufferLast() {
    currentCaret.setRow(lines.size() - 1);
    currentCaret.setCol(currentLine().getLength());
    observer.updateCaret(currentCaret);
  }

  public boolean isBufferHead() {
    return currentCaret.getRow() == 0;
  }

  public boolean isLineHead() {
    return currentCaret.getCol() == 0;
  }

  public boolean isBufferLast() {
    return currentCaret.getRow() == lines.size() - 1;
  }

  public boolean isLineLast() {
    return currentCaret.getCol() == currentLine().getLength();
  }

  public void save() {
    new BufferRepository().saveBuffer(this);
  }

  public void mark() {
    mark.setCol(currentCaret.getCol());
    mark.setRow(currentCaret.getRow());
  }

  public String copy() {
    StringBuilder buf = new StringBuilder();
    if (mark.compareTo(currentCaret) == 0) {
      return buf.toString();
    }

    Caret head = mark;
    Caret tail = currentCaret;
    if (mark.compareTo(currentCaret) > 0) {
      head = currentCaret;
      tail = mark;
    }

    if (head.getRow() == tail.getRow()) {
      buf.append(lines.get(head.getRow()).toLineString().substring(head.getCol(), tail.getCol()));
    } else {
      buf.append(lines.get(head.getRow()).toLineString().substring(head.getCol()));
      if (tail.getRow() - head.getRow() > 1) {
        for (int i = head.getRow() + 1; i < tail.getRow(); i++) {
          buf.append("\n");
          buf.append(lines.get(i).toLineString());
        }
      }
      buf.append("\n");
      buf.append(lines.get(tail.getRow()).toLineString().substring(0, tail.getCol()));
    }
    return buf.toString();
  }

  public void cut() {
    if (mark.compareTo(currentCaret) == 0) {
      return;
    }
    if (mark.compareTo(currentCaret) > 0) {
      Caret tmp = mark;
      mark = currentCaret;
      currentCaret = tmp;
    }
    while (mark.compareTo(currentCaret) != 0) {
      backspace();
    }
  }
}

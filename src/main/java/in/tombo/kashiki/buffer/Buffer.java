package in.tombo.kashiki.buffer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;

public class Buffer {

  private String bufferName;

  private CaretHandler caretHandler = new CaretHandlerImpl(this);
  private Caret mark = new Caret(0, 0);
  private List<BufferLine> lines = new ArrayList<BufferLine>();
  private BufferObserver observer = new BufferObserver();

  public Buffer(String bufferName, String value) {
    this.bufferName = bufferName;
    BufferLine bl = new BufferLine();
    lines.add(bl);
    observer.addLine(bl);
    update();
    insertString(value);
  }

  public String getBufferName() {
    return bufferName;
  }

  public void addListener(BufferListener listener) {
    observer.addListener(listener);
  }

  private void update() {
    for (int i = 0; i < lines.size(); i++) {
      lines.get(i).updatePosition(i);
    }
    observer.update(this);
    caretHandler.forEach(c -> observer.updateCaret(c));
  }

  public void insertString(String string) {
    String[] values = string.split("(\r\n|\n)");
    if (values.length == 0) {
      insertEnter();
    } else if (values.length == 1) {
      values[0].codePoints()
          .forEach(codePoint -> insertChar(new String(Character.toChars(codePoint))));
    } else {
      Arrays.stream(values).forEach(v -> {
        v.codePoints().forEach(codePoint -> insertChar(new String(Character.toChars(codePoint))));
        insertEnter();
      });
    }
  }

  public void insertChar(String charStr) {
    caretHandler.forEach(c -> {
      getCaretLine(c).insertChar(c.getCol(), charStr);
      c.incCol(1);
      observer.updateCaret(c);
    });
  }

  public BufferLine getCaretLine(Caret caret) {
    return lines.get(caret.getRow());
  }

  public void insertEnter() {
    caretHandler.forEach(c -> {
      BufferLine currentLine = getCaretLine(c);
      BufferLine nextLine = new BufferLine();
      List<BufferChar> leaveChars = currentLine.insertEnter(c.getCol());
      lines.add(c.getRow() + 1, nextLine);
      update();
      observer.addLine(nextLine);
      leaveChars.stream().forEach(bufferChar -> {
        nextLine.insertLast(bufferChar);
        observer.moveChar(currentLine, nextLine, bufferChar);
      });
      c.setCol(0);
      c.incRow(1);
      observer.updateCaret(c);
    });
  }

  public int getMaxColNum() {
    return lines.stream().max((l, r) -> Integer.compare(l.getLength(), r.getLength()))
        .orElse(new BufferLine()).getLength();
  }

  public List<BufferLine> getLines() {
    return ImmutableList.copyOf(lines);
  }

  public BufferLine preLine() {
    if (caretHandler.currentCaret().getRow() == 0) {
      return null;
    }
    return lines.get(caretHandler.currentCaret().getRow() - 1);
  }

  public BufferLine postLine() {
    if (caretHandler.currentCaret().getRow() == lines.size() - 1) {
      return null;
    }
    return lines.get(caretHandler.currentCaret().getRow() - 1);
  }

  public String toBufferString() {
    return lines.stream().map((l) -> l.toLineString()).collect(Collectors.joining("\n"));
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    buf.append(toBufferString());
    caretHandler.forEach(c -> {
      buf.append(String.format("Caret:[%d,%d]", c.getCol(), c.getRow()));
    });
    return buf.toString();
  }

  public Caret getCaret() {
    return caretHandler.currentCaret();
  }

  public void backspace() {
    caretHandler.forEach(c -> {
      if (isBufferHead(c) && isLineHead(c)) {
        return;
      }
      back();
      delete();
    });
  }

  public void delete() {
    caretHandler.forEach(c -> {
      if (isBufferLast(c) && isLineLast(c)) {
        return;
      } else if (!isLineLast(c)) {
        getCaretLine(c).removeChar(c.getCol());
      } else if (isLineLast(c) && !isBufferLast(c)) {
        BufferLine currentLine = getCaretLine(c);
        BufferLine removedLine = lines.remove(c.getRow() + 1);
        currentLine.join(removedLine);
        removedLine.getChars().stream()
            .forEach(bufferChar -> observer.moveChar(removedLine, currentLine, bufferChar));
        observer.removeLine(removedLine);
      }
    });
  }

  public void head() {
    caretHandler.head();
    caretHandler.forEach(observer::updateCaret);
  }

  public void last() {
    caretHandler.last();
    caretHandler.forEach(observer::updateCaret);
  }

  public void back() {
    caretHandler.back();
    caretHandler.forEach(observer::updateCaret);
  }

  public void forward() {
    caretHandler.forward();
    caretHandler.forEach(observer::updateCaret);
  }

  public void previous() {
    caretHandler.previous();
    caretHandler.forEach(observer::updateCaret);
  }

  public void next() {
    caretHandler.next();
    caretHandler.forEach(observer::updateCaret);
  }

  public void bufferHead() {
    caretHandler.bufferHead();
    caretHandler.forEach(observer::updateCaret);
  }

  public void bufferLast() {
    caretHandler.bufferLast();
    caretHandler.forEach(observer::updateCaret);
  }

  public boolean isBufferHead(Caret caret) {
    return caret.getRow() == 0;
  }

  public boolean isLineHead(Caret caret) {
    return caret.getCol() == 0;
  }

  public boolean isBufferLast(Caret caret) {
    return caret.getRow() == lines.size() - 1;
  }

  public boolean isLineLast(Caret caret) {
    return caret.getCol() == lines.get(caret.getRow()).getLength();
  }

  public void save() {
    new BufferRepository().saveBuffer(this);
  }

  public void mark() {
    mark.setCol(caretHandler.currentCaret().getCol());
    mark.setRow(caretHandler.currentCaret().getRow());
  }

  public String copy() {
    StringBuilder buf = new StringBuilder();
    if (mark.compareTo(caretHandler.currentCaret()) == 0) {
      return buf.toString();
    }

    Caret head = mark;
    Caret tail = caretHandler.currentCaret();
    if (mark.compareTo(caretHandler.currentCaret()) > 0) {
      head = caretHandler.currentCaret();
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
    Caret currentCaret = caretHandler.currentCaret();
    if (mark.compareTo(currentCaret) == 0) {
      return;
    }
    if (mark.compareTo(currentCaret) > 0) {
      Caret tmp = new Caret(mark.getRow(), mark.getCol());
      mark.setRow(currentCaret.getRow());
      mark.setCol(currentCaret.getCol());
      currentCaret.setRow(tmp.getRow());
      currentCaret.setCol(tmp.getCol());
    }
    while (mark.compareTo(currentCaret) != 0) {
      backspace();
    }
  }
}

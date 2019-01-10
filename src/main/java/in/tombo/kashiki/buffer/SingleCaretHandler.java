package in.tombo.kashiki.buffer;

import java.util.function.Consumer;

public class SingleCaretHandler implements CaretHandler {

  private final Buffer buffer;

  private final Caret caret;

  public SingleCaretHandler(Buffer buffer, Caret caret) {
    this.buffer = buffer;
    this.caret = caret;
  }

  @Override
  public void head() {
    caret.setCol(0);
  }

  @Override
  public void last() {
    caret.setCol(buffer.getCaretLine(caret).getLength());
  }

  @Override
  public void back() {
    if (buffer.isLineHead(caret)) {
      boolean isDocHead = buffer.isBufferHead(caret);
      previous();
      if (!isDocHead) {
        last();
      }
      return;
    }
    caret.decCol(1);
  }

  @Override
  public void forward() {
    if (buffer.isLineLast(caret)) {
      boolean isDocLast = buffer.isBufferLast(caret);
      next();
      if (!isDocLast) {
        head();
      }
      return;
    }
    caret.incCol(1);
  }

  @Override
  public void previous() {
    if (buffer.isBufferHead(caret)) {
      return;
    }
    caret.decRow(1);
    if (caret.getCol() > buffer.getCaretLine(caret).getLength()) {
      last();
    }
  }

  @Override
  public void next() {
    if (buffer.isBufferLast(caret)) {
      return;
    }
    caret.incRow(1);
    if (caret.getCol() > buffer.getCaretLine(caret).getLength()) {
      last();
    }
  }

  @Override
  public void bufferHead() {
    caret.setRow(0);
    caret.setCol(0);
  }

  @Override
  public void bufferLast() {
    caret.setRow(buffer.getLines().size() - 1);
    caret.setCol(buffer.getCaretLine(caret).getLength());
  }

  @Override
  public void addCaret() {}

  @Override
  public void clearCaret() {}

  @Override
  public Caret currentCaret() {
    return caret;
  }

  @Override
  public void forEach(Consumer<Caret> consumer) {
    consumer.accept(caret);
  }
}

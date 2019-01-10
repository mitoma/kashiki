package in.tombo.kashiki.buffer;

import java.util.ArrayList;
import java.util.List;

public class CaretHandlerImpl implements CaretHandler {

  private final Caret currentCaret;

  private final List<Caret> carets;

  private final Buffer buffer;

  public CaretHandlerImpl(Buffer buffer) {
    this.currentCaret = new Caret(0, 0);
    this.carets = new ArrayList<Caret>();
    this.carets.add(currentCaret);
    this.buffer = buffer;
  }

  @Override
  public void addCaret() {
    this.carets.add(new Caret(currentCaret.getRow(), currentCaret.getCol()));
  }

  @Override
  public void clearCaret() {
    this.carets.clear();
    this.carets.add(currentCaret);
  }

  @Override
  public void head() {
    carets.forEach(c -> c.setCol(0));
  }

  @Override
  public void last() {
    carets.forEach(c -> {
      c.setCol(getCaretLine(c).getLength());
    });
  }

  @Override
  public void back() {
    carets.forEach(c -> {
      if (buffer.isLineHead(c)) {
        boolean isDocHead = buffer.isBufferHead(c);
        previous();
        if (!isDocHead) {
          last();
        }
        return;
      }
      currentCaret.decCol(1);
    });
  }

  @Override
  public void forward() {
    carets.forEach(c -> {
      if (buffer.isLineLast(c)) {
        boolean isDocLast = buffer.isBufferLast(c);
        next();
        if (!isDocLast) {
          head();
        }
        return;
      }
      currentCaret.incCol(1);
    });
  }

  @Override
  public void previous() {
    carets.forEach(c -> {
      if (buffer.isBufferHead(c)) {
        return;
      }
      currentCaret.decRow(1);
      if (currentCaret.getCol() > getCaretLine(c).getLength()) {
        last();
      }
    });
  }

  @Override
  public void next() {
    carets.forEach(c -> {
      if (buffer.isBufferLast(c)) {
        return;
      }
      currentCaret.incRow(1);
      if (currentCaret.getCol() > getCaretLine(c).getLength()) {
        last();
      }
    });
  }

  @Override
  public void bufferHead() {
    carets.forEach(c -> {
      currentCaret.setRow(0);
      currentCaret.setCol(0);
    });
  }

  @Override
  public void bufferLast() {
    carets.forEach(c -> {
      currentCaret.setRow(buffer.getLines().size() - 1);
      currentCaret.setCol(getCaretLine(c).getLength());
    });
  }

  private BufferLine getCaretLine(Caret c) {
    return buffer.getLines().get(c.getRow());
  }
}

package in.tombo.kashiki.buffer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

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
      c.setCol(buffer.getCaretLine(c).getLength());
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
      c.decCol(1);
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
      c.incCol(1);
    });
  }

  @Override
  public void previous() {
    carets.forEach(c -> {
      if (buffer.isBufferHead(c)) {
        return;
      }
      c.decRow(1);
      if (c.getCol() > buffer.getCaretLine(c).getLength()) {
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
      c.incRow(1);
      if (c.getCol() > buffer.getCaretLine(c).getLength()) {
        last();
      }
    });
  }

  @Override
  public void bufferHead() {
    carets.forEach(c -> {
      c.setRow(0);
      c.setCol(0);
    });
  }

  @Override
  public void bufferLast() {
    carets.forEach(c -> {
      c.setRow(buffer.getLines().size() - 1);
      c.setCol(buffer.getCaretLine(c).getLength());
    });
  }

  @Override
  public void forEach(Consumer<Caret> consumer) {
    carets.sort((l, r) -> r.compareTo(l));
    for (Caret caret : carets) {
      consumer.accept(caret);
    }
  }

  @Override
  public Caret currentCaret() {
    return currentCaret;
  }

}

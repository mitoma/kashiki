package in.tombo.kashiki.buffer;

import java.util.ArrayList;
import java.util.List;

public class BufferObserver implements BufferListener {

  List<BufferListener> listeners = new ArrayList<>();

  public void addListener(BufferListener listener) {
    listeners.add(listener);
  }

  @Override
  public void update(Buffer buffer) {
    listeners.forEach((l) -> l.update(buffer));
  }

  @Override
  public void updateCaret(Caret caret) {
    listeners.forEach((l) -> l.updateCaret(caret));
  }

  @Override
  public void addLine(BufferLine bufferLine) {
    listeners.forEach((l) -> l.addLine(bufferLine));
  }

  @Override
  public void removeLine(BufferLine bufferLine) {
    listeners.forEach((l) -> l.removeLine(bufferLine));
  }

  public void moveChar(BufferLine fromLine, BufferLine toLine, BufferChar c) {
    listeners.forEach((l) -> l.moveChar(fromLine, toLine, c));
  }

}

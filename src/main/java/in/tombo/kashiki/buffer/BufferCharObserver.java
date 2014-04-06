package in.tombo.kashiki.buffer;

import java.util.ArrayList;
import java.util.List;

public class BufferCharObserver implements BufferCharListener {

  private List<BufferCharListener> listeners = new ArrayList<>();

  public void addListener(BufferCharListener listener) {
    listeners.add(listener);
  }

  @Override
  public void update(BufferChar bc) {
    listeners.forEach((l) -> l.update(bc));
  }
}

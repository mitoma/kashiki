package in.tombo.kashiki;

import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.List;

import in.tombo.kashiki.buffer.Buffer;
import in.tombo.kashiki.buffer.BufferRepository;
import in.tombo.kashiki.keybind.ActionRepository;
import in.tombo.kashiki.keybind.KashikiKeyListener;
import in.tombo.kashiki.keybind.SupportKey;
import in.tombo.kashiki.view.Base;
import in.tombo.kashiki.view.BufferView;
import in.tombo.kashiki.view.SmoothValue;

public class Editor {

  private Buffer currentBuffer;
  private List<Buffer> buffers = new ArrayList<>();
  private List<Base> currentDrawables = new ArrayList<>();

  private ActionRepository actionRepository;
  private KashikiKeyListener keyListener;
  private SmoothValue scale = new SmoothValue(1);
  private Frame frame;

  public Editor(ActionRepository actionRepository, KashikiKeyListener keyListener, Frame frame) {
    this.actionRepository = actionRepository;
    this.keyListener = keyListener;
    this.frame = frame;

    Buffer buf = new BufferRepository().loadBuffer("scratch");
    buffers.add(buf);
    currentBuffer = buf;

    BufferView bufView = new BufferView(buf);
    currentDrawables.add(bufView);
  }

  private KashikiKeyListener currentListener() {
    return keyListener;
  }

  public boolean keyPressed(SupportKey supportKey, int keyCode, long when) {
    return currentListener().keyPressed(this, supportKey, keyCode, when);
  }

  public boolean keyTyped(char typedString, long when) {
    return currentListener().keyTyped(this, typedString, when);
  }

  public void executeAction(String name, String... args) {
    actionRepository.executeAction(this, name, args);
  }

  public Buffer getCurrentBuffer() {
    return this.currentBuffer;
  }

  public List<Base> getDrawables() {
    return currentDrawables;
  }

  public SmoothValue getScale() {
    return this.scale;
  }

  public void createNewBuffer() {
    Buffer buf = new Buffer("scratch-" + System.currentTimeMillis(), "");
    buffers.add(buf);
    currentBuffer = buf;
    currentDrawables.clear();
    BufferView bufView = new BufferView(buf);
    currentDrawables.add(bufView);
  }

  public void reflesh() {
    currentDrawables.clear();
    currentDrawables.add(new BufferView(currentBuffer));
  }

  public void tearDown() {
    // FIXME
  }

  public void toggleFullScreen() {
    GraphicsDevice screenDevice =
        GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    if (screenDevice.isFullScreenSupported()) {
      if (screenDevice.getFullScreenWindow() != null) {
        screenDevice.setFullScreenWindow(null);
      } else {
        screenDevice.setFullScreenWindow(frame);
      }
    }
  }
}

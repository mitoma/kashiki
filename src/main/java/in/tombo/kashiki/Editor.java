package in.tombo.kashiki;

import in.tombo.kashiki.buffer.Buffer;
import in.tombo.kashiki.buffer.BufferRepository;
import in.tombo.kashiki.keybind.ActionRepository;
import in.tombo.kashiki.keybind.EmacsKeyListener;
import in.tombo.kashiki.keybind.KashikiKeyListener;
import in.tombo.kashiki.keybind.SupportKey;
import in.tombo.kashiki.view.Base;
import in.tombo.kashiki.view.BufferView;
import in.tombo.kashiki.view.SmoothValue;

import java.util.ArrayList;
import java.util.List;

public class Editor implements KashikiKeyListener {

  private static boolean INITIALIZED = false;
  private static Editor INSTANCE = new Editor();

  private Buffer currentBuffer;
  private List<Buffer> buffers = new ArrayList<>();
  private List<Base> currentDrawables = new ArrayList<>();

  private ActionRepository actionRepository;
  private KashikiKeyListener keyListener;
  private SmoothValue scale = new SmoothValue(1);

  private Editor() {
    Buffer buf = new BufferRepository().loadBuffer("scratch");
    buffers.add(buf);
    currentBuffer = buf;

    BufferView bufView = new BufferView(buf);
    currentDrawables.add(bufView);
  }

  public static Editor getInstance() {
    if (!INITIALIZED) {
      INSTANCE.actionRepository = new ActionRepository();
      INSTANCE.keyListener = new EmacsKeyListener();
      INITIALIZED = true;
    }
    return INSTANCE;
  }

  private KashikiKeyListener currentListener() {
    return keyListener;
  }

  @Override
  public boolean keyPressed(SupportKey supportKey, int keyCode, long when) {
    return currentListener().keyPressed(supportKey, keyCode, when);
  }

  @Override
  public boolean keyTyped(char typedString, long when) {
    return currentListener().keyTyped(typedString, when);
  }

  public void executeAction(String name, String... args) {
    actionRepository.executeAction(name, args);
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
    setBuffer(buf);
  }

  public void setBuffer(Buffer buf) {
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
}

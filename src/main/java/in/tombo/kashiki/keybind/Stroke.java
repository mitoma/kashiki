package in.tombo.kashiki.keybind;

public class Stroke {
  private SupportKey supportKey;
  private int key;

  public Stroke(SupportKey supportKey, int key) {
    this.supportKey = supportKey;
    this.key = key;
  }

  public SupportKey getSupportKey() {
    return supportKey;
  }

  public int getKey() {
    return key;
  }

  @Override
  public String toString() {
    return String.format("Stroke[%s-%s]", supportKey, key);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Stroke) {
      Stroke stroke = (Stroke) obj;
      return (this.key == stroke.key) && (this.supportKey == stroke.supportKey);
    }
    return false;
  }
}

package in.tombo.kashiki.view;

import com.jogamp.opengl.GL2;

public class Scale {
  private final SmoothValue x;
  private final SmoothValue y;
  private final SmoothValue z;

  public Scale(double x, double y, double z) {
    this.x = new SmoothValue(1);
    this.y = new SmoothValue(1);
    this.z = new SmoothValue(1);
  }

  public SmoothValue getX() {
    return x;
  }

  public SmoothValue getY() {
    return y;
  }

  public SmoothValue getZ() {
    return z;
  }

  public boolean isAnimated() {
    return x.isAnimated() && y.isAnimated() && z.isAnimated();
  }

  public void update(double x, double y, double z) {
    this.x.setValue(x);
    this.y.setValue(y);
    this.z.setValue(z);
  }

  public void updateWithoutSmooth(double x, double y, double z) {
    this.x.setValueWithoutSmooth(x);
    this.y.setValueWithoutSmooth(y);
    this.z.setValueWithoutSmooth(z);
  }

  public void updateScale(GL2 gl) {
    double _x = x.getValue();
    double _y = y.getValue();
    double _z = z.getValue();
    if (_x == 1 && _y == 1 && _z == 1) {
      return;
    }
    gl.glScaled(_x, _y, _z);
  }

}

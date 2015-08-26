package in.tombo.kashiki.view;

import com.jogamp.opengl.GL2;

public class Position {
  private SmoothValue x = new SmoothValue(0);
  private SmoothValue y = new SmoothValue(0);
  private SmoothValue z = new SmoothValue(0);

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

  public void updateTranslate(GL2 gl) {
    double _x = x.getValue();
    double _y = y.getValue();
    double _z = z.getValue();
    if (_x == 0 && _y == 0 && _z == 0) {
      return;
    }
    gl.glTranslated(_x, _y, _z);
  }

  public SmoothValue getX() {
    return x;
  }

  public void setX(SmoothValue x) {
    this.x = x;
  }

  public SmoothValue getY() {
    return y;
  }

  public void setY(SmoothValue y) {
    this.y = y;
  }

  public SmoothValue getZ() {
    return z;
  }

  public void setZ(SmoothValue z) {
    this.z = z;
  }
}

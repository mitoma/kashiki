package in.tombo.kashiki.view;

import com.jogamp.opengl.GL2;

public class Angle {
  private SmoothValue x = new SmoothValue(0);
  private SmoothValue y = new SmoothValue(0);
  private SmoothValue z = new SmoothValue(0);

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

  public void updateRotate(GL2 gl) {
    double _x = x.getValue();
    if (_x != 0) {
      gl.glRotated(_x, 1, 0, 0);
    }
    double _y = y.getValue();
    if (_y != 0) {
      gl.glRotated(_y, 0, 1, 0);
    }
    double _z = z.getValue();
    if (_z != 0) {
      gl.glRotated(_z, 0, 0, 1);
    }
  }
}

package in.tombo.kashiki.view;

import com.jogamp.opengl.GL2;

public class Color {
  private SmoothValue red = new SmoothValue(1);
  private SmoothValue green = new SmoothValue(1);
  private SmoothValue blue = new SmoothValue(1);
  private SmoothValue alpha = new SmoothValue(1);

  public SmoothValue getRed() {
    return red;
  }

  public void setRed(SmoothValue red) {
    this.red = red;
  }

  public SmoothValue getGreen() {
    return green;
  }

  public void setGreen(SmoothValue green) {
    this.green = green;
  }

  public SmoothValue getBlue() {
    return blue;
  }

  public void setBlue(SmoothValue blue) {
    this.blue = blue;
  }

  public SmoothValue getAlpha() {
    return alpha;
  }

  public void setAlpha(SmoothValue alpha) {
    this.alpha = alpha;
  }

  public boolean isAnimated() {
    return red.isAnimated() && green.isAnimated() && blue.isAnimated() && alpha.isAnimated();
  }

  public void updateColor(GL2 gl) {
    gl.glColor4d(red.getValue(), green.getValue(), blue.getValue(), alpha.getValue());
  }

  public void update(double red, double blue, double green, double alpha) {
    this.red.setValue(red);
    this.blue.setValue(blue);
    this.green.setValue(green);
    this.alpha.setValue(alpha);
  }

  public void updateWithoutSmooth(double red, double blue, double green, double alpha) {
    this.red.setValueWithoutSmooth(red);
    this.blue.setValueWithoutSmooth(blue);
    this.green.setValueWithoutSmooth(green);
    this.alpha.setValueWithoutSmooth(alpha);
  }
}

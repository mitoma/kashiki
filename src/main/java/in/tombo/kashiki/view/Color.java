package in.tombo.kashiki.view;

import com.jogamp.opengl.GL2;

public class Color {
  private final SmoothValue red;
  private final SmoothValue green;
  private final SmoothValue blue;
  private final SmoothValue alpha;

  public Color(double red, double blue, double green, double alpha) {
    this.red = new SmoothValue(red);
    this.green = new SmoothValue(blue);
    this.blue = new SmoothValue(green);
    this.alpha = new SmoothValue(alpha);
  }

  public SmoothValue getRed() {
    return red;
  }

  public SmoothValue getGreen() {
    return green;
  }

  public SmoothValue getBlue() {
    return blue;
  }

  public SmoothValue getAlpha() {
    return alpha;
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

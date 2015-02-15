package in.tombo.kashiki.view;

import javax.media.opengl.GL2;

public class Color {
  private SmoothValue red = new SmoothValue(SolarizedColor.BASE0.getR());
  private SmoothValue green = new SmoothValue(SolarizedColor.BASE0.getG());
  private SmoothValue blue = new SmoothValue(SolarizedColor.BASE0.getB());
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

  public void update(SolarizedColor color) {
    update(color, 1.0);
  }

  public void update(SolarizedColor color, double alpha) {
    update(color.getR(), color.getB(), color.getG(), alpha);
  }

  private void update(double red, double blue, double green, double alpha) {
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

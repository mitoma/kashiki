package in.tombo.kashiki.view;

import javax.media.opengl.GL2;

public abstract class Base {

  private Position position = new Position();
  private Angle angle = new Angle();
  private Scale scale = new Scale();
  private Color color = new Color();

  public void draw(GL2 gl) {
    preDraw(gl);
    innerDraw(gl);
    postDraw(gl);
  }

  public abstract void innerDraw(GL2 gl);

  public void preDraw(GL2 gl) {
    gl.glPushMatrix();
    position.updateTranslate(gl);
    angle.updateRotate(gl);
    scale.updateScale(gl);
    color.updateColor(gl);
  }

  public void postDraw(GL2 gl) {
    gl.glPopMatrix();
  }

  public boolean isAnimated() {
    return position.isAnimated() && angle.isAnimated() && scale.isAnimated() && color.isAnimated();
  }

  public Position getPosition() {
    return position;
  }

  public void setPosition(Position position) {
    this.position = position;
  }

  public Angle getAngle() {
    return angle;
  }

  public void setAngle(Angle angle) {
    this.angle = angle;
  }

  public Scale getScale() {
    return scale;
  }

  public void setScale(Scale scale) {
    this.scale = scale;
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

}

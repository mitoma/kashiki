package in.tombo.kashiki.view;

import in.tombo.kashiki.buffer.BufferChar;
import in.tombo.kashiki.buffer.BufferCharListener;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;

public class CharView extends Base implements BufferCharListener, Comparable<CharView> {
  private TextureProvider textureProvider;
  private BufferChar bufferChar;

  public CharView(BufferChar bufferChar) {
    this.textureProvider = TextureProvider.getInstance();
    this.bufferChar = bufferChar;
    bufferChar.addListener(this);
    update(bufferChar);
  }

  public double getWidth() {
    return textureProvider.getWidth(String.valueOf(bufferChar.getChar()));
  }

  @Override
  public void innerDraw(GL2 gl) {
    Texture texture = textureProvider.getTexture(gl, String.valueOf(bufferChar.getChar()));
    texture.enable(gl);
    texture.bind(gl);
    gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
    gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
    gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_ALPHA_TYPE, GL2.GL_LINEAR);
    gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);

    gl.glBegin(GL2.GL_POLYGON);
    gl.glTexCoord2f(0, 1);
    gl.glVertex2d(-0.5, -0.5);
    gl.glTexCoord2f(0, 0);
    gl.glVertex2d(-0.5, 0.5);
    gl.glTexCoord2f(1, 0);
    gl.glVertex2d(0.5, 0.5);
    gl.glTexCoord2f(1, 1);
    gl.glVertex2d(0.5, -0.5);
    gl.glEnd();
  }

  @Override
  public void update(BufferChar bc) {
    // TODO
  }

  public BufferChar getBufferChar() {
    return bufferChar;
  }

  @Override
  public int compareTo(CharView o) {
    return bufferChar.compareTo(o.bufferChar);
  }

}

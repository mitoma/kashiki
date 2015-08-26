package in.tombo.kashiki.view;

import in.tombo.kashiki.buffer.BufferChar;
import in.tombo.kashiki.buffer.BufferLine;
import in.tombo.kashiki.buffer.BufferLineListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.jogamp.opengl.GL2;

public class LineView extends Base implements BufferLineListener, Comparable<LineView> {

  private BufferLine bufferLine;
  private List<CharView> chars = new ArrayList<>();
  private List<CharView> removedChars = new ArrayList<>();

  public LineView(BufferLine bufferLine) {
    this.bufferLine = bufferLine;
    bufferLine.addListener(this);
    List<BufferChar> bufferChars = bufferLine.getChars();
    for (BufferChar bc : bufferChars) {
      chars.add(new CharView(bc));
    }
    updatePositions();
  }

  public double getHeight() {
    return 1;
  }

  public double getWidth() {
    double width = 0;
    for (CharView c : chars) {
      width += c.getWidth();
    }
    return width;
  }

  @Override
  public void innerDraw(GL2 gl) {
    for (CharView c : chars) {
      c.draw(gl);
    }
    List<CharView> removed = new ArrayList<>();
    for (CharView c : removedChars) {
      c.draw(gl);
      if (!c.isAnimated()) {
        removed.add(c);
      }
    }
    removedChars = removed;
  }

  private void updatePositions() {
    getColor().getBlue().setValue(1);
    Collections.sort(chars);
    double width = 0;
    for (CharView c : chars) {
      double w = c.getWidth() / 2;
      width += w;
      c.getPosition().update(width, 0, 0);
      width += w;
    }
  }

  @Override
  public void update(BufferLine bl) {
    updatePositions();
  }

  @Override
  public void addChar(BufferChar bufferChar) {
    CharView cv = new CharView(bufferChar);
    chars.add(cv);

    Collections.sort(chars);
    double width = 0;
    for (CharView c : chars) {
      double w = c.getWidth() / 2;
      width += w;
      if (c == cv) {
        c.getPosition().getX().setValueWithoutSmooth(width);
      } else {
        c.getPosition().getX().setValue(width);
      }
      width += w;
    }

    cv.getScale().updateWithoutSmooth(0, 0, 0);
    cv.getScale().update(1, 1, 1);
  }

  @Override
  public void removeChar(BufferChar bufferChar) {
    Iterator<CharView> ci = chars.iterator();
    while (ci.hasNext()) {
      CharView cv = ci.next();
      if (cv.getBufferChar() == bufferChar) {
        cv.getAngle().update(0, 360, 0);
        cv.getScale().update(0.5, 0.5, 0.5);
        cv.getPosition().getY().addValue(-0.5);
        removedChars.add(cv);
        ci.remove();
        break;
      }
    }
    updatePositions();
  }

  public BufferLine getBufferLine() {
    return bufferLine;
  }

  public List<CharView> getChars() {
    return chars;
  }

  @Override
  public int compareTo(LineView o) {
    return bufferLine.compareTo(o.bufferLine);
  }

  public CharView leaveChar(BufferChar bc) {
    CharView leave = chars.stream().filter(c -> c.getBufferChar() == bc).findFirst().orElse(null);
    chars.remove(leave);
    updatePositions();
    return leave;
  }

  public void visitChar(CharView cv) {
    chars.add(cv);
    updatePositions();
  }
}

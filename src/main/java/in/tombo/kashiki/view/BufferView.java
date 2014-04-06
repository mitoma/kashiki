package in.tombo.kashiki.view;

import in.tombo.kashiki.buffer.Buffer;
import in.tombo.kashiki.buffer.BufferChar;
import in.tombo.kashiki.buffer.BufferLine;
import in.tombo.kashiki.buffer.BufferListener;
import in.tombo.kashiki.buffer.Caret;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.media.opengl.GL2;

public class BufferView extends Base implements BufferListener {

  private Buffer document;
  private CaretView caret;
  private List<LineView> lines = new ArrayList<>();
  private SmoothValue width = new SmoothValue();
  private SmoothValue height = new SmoothValue();

  public BufferView(Buffer buffer) {
    this.document = buffer;
    this.caret = new CaretView(buffer.getCaret());
    getColor().update(0.5, 0.5, 0.5, 0.5);
    buffer.addListener(this);
    buildLines();
  }

  public void preDraw(GL2 gl) {
    gl.glPushMatrix();
    getAngle().updateRotate(gl);
    getScale().updateScale(gl);
    getPosition().updateTranslate(gl);
    getColor().updateColor(gl);
  }

  private void buildLines() {
    LinkedList<BufferLine> lines = document.getLines();
    for (BufferLine bufferLine : lines) {
      addLine(bufferLine);
    }
  }

  public double documentHeight() {
    double height = 0;
    for (LineView line : lines) {
      height += line.getHeight();
    }
    return height;
  }

  public double documentWidth() {
    double maxWidth = 0;
    for (LineView line : lines) {
      double width = line.getWidth();
      maxWidth = (maxWidth < width) ? width : maxWidth;
    }
    return maxWidth;
  }

  @Override
  public void innerDraw(GL2 gl) {
    double w = documentWidth() + 2;
    double h = documentHeight();

    Position position = getPosition();
    Position cPos = caret.getPosition();
    position.update(-cPos.getX().getValue(false), -cPos.getY().getValue(false), 0);

    width.setValue(w);
    height.setValue(-h);

    gl.glDisable(GL2.GL_TEXTURE_2D);

    gl.glRectd(-1, 1, width.getValue(), height.getValue());
    for (LineView line : lines) {
      line.draw(gl);
    }

    updateCaret(document.getCaret());
    caret.draw(gl);
  }

  @Override
  public void updateCaret(Caret c) {
    LineView lv = lines.get(c.getRow());
    List<CharView> cvl = lv.getChars();

    double x;
    if (document.isLineHead()) {
      x =
          lv.getChars().stream().mapToDouble(cv -> cv.getWidth() / 2).findFirst()
              .orElse(caret.getWidth() / 2);
    } else if (document.isLineLast()) {
      x = lv.getWidth() + (caret.getWidth() / 2);
    } else {
      x = cvl.get(c.getCol()).getPosition().getX().getLastValue();
    }
    double y = -lv.getPosition().getY().getLastValue();

    caret.updatePosition(x, y);
  }

  @Override
  public void update(Buffer buffer) {
    updatePositions();
  }

  @Override
  public void addLine(BufferLine bufferLine) {
    LineView lv1 = new LineView(bufferLine);
    lines.add(lv1);
    Collections.sort(lines);
    double h = 0;
    for (LineView lv2 : lines) {
      if (bufferLine == lv2.getBufferLine()) {
        lv2.getPosition().getY().setValueWithoutSmooth(h);
      } else {
        lv2.getPosition().getY().setValue(h);
      }
      h -= lv2.getHeight();
    }
  }

  @Override
  public void removeLine(BufferLine bufferLine) {
    Iterator<LineView> li = lines.iterator();
    while (li.hasNext()) {
      if (li.next().getBufferLine() == bufferLine) {
        li.remove();
      }
    }
    for (LineView lv : lines) {
      if (lv.getBufferLine() == bufferLine) {
        lines.remove(lv);
      }
    }
    updatePositions();
  }

  @Override
  public void moveChar(BufferLine fromLine, BufferLine toLine, BufferChar c) {
    lines.stream().filter(l -> l.getBufferLine() == fromLine).findFirst().ifPresent((from) -> {
      lines.stream().filter(l -> l.getBufferLine() == toLine).findFirst().ifPresent((to) -> {
        double fromY = from.getPosition().getY().getValue(false);
        double toY = to.getPosition().getY().getLastValue();
        CharView leaveChar = from.leaveChar(c);
        leaveChar.getPosition().getY().setValueWithoutSmooth(-(toY - fromY));
        to.visitChar(leaveChar);
      });
    });
  }

  private void updatePositions() {
    Collections.sort(lines);
    double h = 0;
    for (LineView lv : lines) {
      lv.getPosition().getY().setValue(h);
      h -= lv.getHeight();
    }
  }

}

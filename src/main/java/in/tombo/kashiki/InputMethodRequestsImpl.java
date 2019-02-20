package in.tombo.kashiki;

import java.awt.Rectangle;
import java.awt.font.TextHitInfo;
import java.awt.im.InputMethodRequests;
import java.text.AttributedCharacterIterator;
import java.text.AttributedCharacterIterator.Attribute;
import java.text.AttributedString;

import in.tombo.kashiki.buffer.Buffer;

public class InputMethodRequestsImpl implements InputMethodRequests {

  private final Buffer buffer;

  public InputMethodRequestsImpl(Buffer buffer) {
    this.buffer = buffer;
  }

  /** don't support */
  @Override
  public AttributedCharacterIterator cancelLatestCommittedText(Attribute[] attributes) {
    return null;
  }

  /** don't support */
  @Override
  public TextHitInfo getLocationOffset(int x, int y) {
    return null;
  }

  @Override
  public AttributedCharacterIterator getCommittedText(int beginIndex, int endIndex,
      Attribute[] attributes) {
    // buffer line の文字列だけでも良さそう
    AttributedString attributedString = new AttributedString(buffer.toBufferString());
    return attributedString.getIterator();
  }

  @Override
  public int getCommittedTextLength() {
    return (int) buffer.toBufferString().codePoints().count();
  }

  @Override
  public int getInsertPositionOffset() {
    return buffer.getCaret().getCol();    
  }

  @Override
  public AttributedCharacterIterator getSelectedText(Attribute[] attributes) {
    // そのまま実装すればいいかな
    return null;
  }

  @Override
  public Rectangle getTextLocation(TextHitInfo offset) {
    // まぁ結構適当な値をかえせばいいのでは
    return null;
  }

}

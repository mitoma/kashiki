package in.tombo.kashiki.keybind.basic;

import in.tombo.kashiki.Editor;
import in.tombo.kashiki.buffer.FileOpenBuffer;
import in.tombo.kashiki.keybind.Action;

/*
 * 暫定実装。将来捨てるべき。
 */
public class OpenFileAction implements Action {

  @Override
  public String name() {
    return "open-file";
  }

  @Override
  public void execute(String... args) {
    Editor.getInstance().setBuffer(new FileOpenBuffer());
  }
//  @Override
//  public void execute(String... args) {
//    JFileChooser fileChooser = new JFileChooser();
//    int selected = fileChooser.showOpenDialog(null);
//
//    if (selected == JFileChooser.APPROVE_OPTION) {
//      try {
//        String textString = Files.toString(fileChooser.getSelectedFile(), Charsets.UTF_8);
//        Editor.getInstance().createNewBuffer();
//        Editor.getInstance().getCurrentBuffer().insertString(textString);
//      } catch (IOException e) {
//        e.printStackTrace();
//      }
//    }
//  }
}

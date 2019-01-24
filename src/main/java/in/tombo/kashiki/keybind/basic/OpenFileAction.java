package in.tombo.kashiki.keybind.basic;

import java.io.IOException;

import javax.swing.JFileChooser;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import in.tombo.kashiki.Editor;
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
  public void execute(Editor editor, String... args) {
    JFileChooser fileChooser = new JFileChooser();
    int selected = fileChooser.showOpenDialog(null);

    if (selected == JFileChooser.APPROVE_OPTION) {
      try {
        String textString =
            Files.asCharSource(fileChooser.getSelectedFile(), Charsets.UTF_8).read();
        editor.createNewBuffer();
        editor.getCurrentBuffer().insertString(textString);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}

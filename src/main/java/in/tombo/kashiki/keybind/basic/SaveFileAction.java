package in.tombo.kashiki.keybind.basic;

import in.tombo.kashiki.Editor;
import in.tombo.kashiki.keybind.Action;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.google.common.base.Charsets;

public class SaveFileAction implements Action {

  @Override
  public String name() {
    return "save-file";
  }

  @Override
  public void execute(Editor editor, String... args) {
    JFileChooser fileChooser = new JFileChooser();
    int selected = fileChooser.showSaveDialog(null);
    if (selected == JFileChooser.APPROVE_OPTION) {
      try (OutputStreamWriter writer = new OutputStreamWriter(
          new FileOutputStream(fileChooser.getSelectedFile()), Charsets.UTF_8);) {
        editor.getCurrentBuffer().getLines().forEach(l -> {
          try {
            writer.append(l.toLineString());
            writer.append("\n");
          } catch (Exception e) {
            throw new RuntimeException("ファイルの書込みに失敗しました。");
          }
        });
      } catch (IOException e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
      }
    }
  }
}

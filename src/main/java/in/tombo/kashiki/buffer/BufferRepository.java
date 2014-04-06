package in.tombo.kashiki.buffer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class BufferRepository {

  private String repositoryPath = System.getProperty("user.home") + "/kashiki-buffers";
  private Git git;

  public BufferRepository() {
    try {
      File repoPath = new File(repositoryPath);
      if (!repoPath.exists()) {
        repoPath.mkdir();
        Git.init().setDirectory(repoPath).call();
      }
      git = Git.open(repoPath);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (GitAPIException e) {
      e.printStackTrace();
    }
  }

  public Buffer loadBuffer(String name) {
    Buffer buffer = new Buffer(name, "");
    File bufferFile = new File(repositoryPath + "/" + name);
    if (!bufferFile.exists()) {
      if (buffer.bufferName.equals("scratch")) {
        InputStream helpSource =
            Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("in/tombo/kashiki/help.text");
        BufferedReader reader =
            new BufferedReader(new InputStreamReader(helpSource, Charsets.UTF_8));
        reader.lines().forEach(l -> {
          buffer.insertString(l);
          buffer.insertEnter();
        });
        buffer.bufferHead();
      }
      return buffer;
    }
    try {
      List<String> lines = Files.readLines(bufferFile, Charset.forName("UTF-8"));
      lines.forEach((l) -> {
        buffer.insertString(l);
        buffer.insertEnter();
      });
      buffer.backspace();
      buffer.bufferHead();
    } catch (IOException e) {
      return buffer;
    }
    return buffer;
  }

  public void saveBuffer(Buffer buffer) {
    try {
      saveToFile(buffer);
      List<DiffEntry> diffs = git.diff().call();
      if (diffs.size() == 0) {
        return;
      }
      git.add().addFilepattern(buffer.bufferName).call();
      git.commit().setMessage("udpate " + buffer.bufferName).call();
    } catch (GitAPIException e) {
      e.printStackTrace();
    }
  }

  private File saveToFile(Buffer buffer) {
    File file = new File(repositoryPath + "/" + buffer.bufferName);
    if (!file.exists()) {
      try {
        file.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    try (BufferedWriter writer = Files.newWriter(file, Charset.forName("UTF-8"));) {
      buffer.lines.forEach((l) -> {
        try {
          writer.append(l.toLineString());
          writer.newLine();
        } catch (Exception e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      });
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return file;
  }

}

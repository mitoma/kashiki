package in.tombo.kashiki.keybind;

import static in.tombo.kashiki.keybind.SupportKey.*;
import static java.awt.event.KeyEvent.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import in.tombo.kashiki.Editor;

public class EmacsKeyListener implements KashikiKeyListener {
  private static Pattern ACTION_PATTERN = Pattern.compile("-?([^\\-]+)\\z");
  private static Map<String, Integer> CODE_MAPPING = new HashMap<String, Integer>();

  private static long delta = 100;

  static {
    // alpha num
    CODE_MAPPING.put("a", VK_A);
    CODE_MAPPING.put("b", VK_B);
    CODE_MAPPING.put("c", VK_C);
    CODE_MAPPING.put("d", VK_D);
    CODE_MAPPING.put("e", VK_E);
    CODE_MAPPING.put("f", VK_F);
    CODE_MAPPING.put("g", VK_G);
    CODE_MAPPING.put("h", VK_H);
    CODE_MAPPING.put("i", VK_I);
    CODE_MAPPING.put("j", VK_J);
    CODE_MAPPING.put("k", VK_K);
    CODE_MAPPING.put("l", VK_L);
    CODE_MAPPING.put("m", VK_M);
    CODE_MAPPING.put("n", VK_N);
    CODE_MAPPING.put("o", VK_O);
    CODE_MAPPING.put("p", VK_P);
    CODE_MAPPING.put("q", VK_Q);
    CODE_MAPPING.put("r", VK_R);
    CODE_MAPPING.put("s", VK_S);
    CODE_MAPPING.put("t", VK_T);
    CODE_MAPPING.put("u", VK_U);
    CODE_MAPPING.put("v", VK_V);
    CODE_MAPPING.put("w", VK_W);
    CODE_MAPPING.put("x", VK_X);
    CODE_MAPPING.put("y", VK_Y);
    CODE_MAPPING.put("z", VK_Z);
    CODE_MAPPING.put("0", VK_0);
    CODE_MAPPING.put("1", VK_1);
    CODE_MAPPING.put("2", VK_2);
    CODE_MAPPING.put("3", VK_3);
    CODE_MAPPING.put("4", VK_4);
    CODE_MAPPING.put("5", VK_5);
    CODE_MAPPING.put("6", VK_6);
    CODE_MAPPING.put("7", VK_7);
    CODE_MAPPING.put("8", VK_8);
    CODE_MAPPING.put("9", VK_9);

    CODE_MAPPING.put(";", VK_SEMICOLON);
    CODE_MAPPING.put(":", VK_COLON);
    CODE_MAPPING.put("{", VK_BRACELEFT);
    CODE_MAPPING.put("}", VK_BRACERIGHT);
    CODE_MAPPING.put("[", VK_OPEN_BRACKET);
    CODE_MAPPING.put("]", VK_CLOSE_BRACKET);
    CODE_MAPPING.put("minus", VK_MINUS);
    CODE_MAPPING.put("+", VK_PLUS);
    CODE_MAPPING.put("@", VK_AT);
    CODE_MAPPING.put("(", VK_LEFT_PARENTHESIS);
    CODE_MAPPING.put(")", VK_RIGHT_PARENTHESIS);
    CODE_MAPPING.put("#", VK_NUMBER_SIGN);
    CODE_MAPPING.put("!", VK_EXCLAMATION_MARK);
    CODE_MAPPING.put("^", VK_CIRCUMFLEX);
    CODE_MAPPING.put("$", VK_DOLLAR);
    CODE_MAPPING.put("=", VK_EQUALS);
    CODE_MAPPING.put(",", VK_COMMA);
    CODE_MAPPING.put(".", VK_PERIOD);
    CODE_MAPPING.put("/", VK_SLASH);
    CODE_MAPPING.put("\\", VK_BACK_SLASH);
    CODE_MAPPING.put("'", VK_QUOTE);
    CODE_MAPPING.put("\"", VK_QUOTEDBL);
    CODE_MAPPING.put("\"", VK_BACK_QUOTE);

    // key
    CODE_MAPPING.put("backspace", VK_BACK_SPACE);
    CODE_MAPPING.put("insert", VK_INSERT);
    CODE_MAPPING.put("delete", VK_DELETE);
    CODE_MAPPING.put("home", VK_HOME);
    CODE_MAPPING.put("end", VK_END);
    CODE_MAPPING.put("pageup", VK_PAGE_UP);
    CODE_MAPPING.put("pagedown", VK_DELETE);
    CODE_MAPPING.put("space", VK_SPACE);
    CODE_MAPPING.put("tab", VK_TAB);
    CODE_MAPPING.put("enter", VK_ENTER);

    CODE_MAPPING.put("up", VK_UP);
    CODE_MAPPING.put("down", VK_DOWN);
    CODE_MAPPING.put("left", VK_LEFT);
    CODE_MAPPING.put("right", VK_RIGHT);

    // function keys
    CODE_MAPPING.put("f1", VK_F1);
    CODE_MAPPING.put("f2", VK_F2);
    CODE_MAPPING.put("f3", VK_F3);
    CODE_MAPPING.put("f4", VK_F4);
    CODE_MAPPING.put("f5", VK_F5);
    CODE_MAPPING.put("f6", VK_F6);
    CODE_MAPPING.put("f7", VK_F7);
    CODE_MAPPING.put("f8", VK_F8);
    CODE_MAPPING.put("f9", VK_F9);
    CODE_MAPPING.put("f10", VK_F10);
    CODE_MAPPING.put("f11", VK_F11);
    CODE_MAPPING.put("f12", VK_F12);
  }

  private final Editor editor;
  private final ActionRepository actionRepository;

  public EmacsKeyListener(Editor editor, ActionRepository actionRepository) {
    this.editor = editor;
    this.actionRepository = actionRepository;
    try {
      setup();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private long when;
  private boolean executed;
  private boolean inStroke;
  Map<List<Stroke>, String> keybinds = new HashMap<List<Stroke>, String>();
  List<Stroke> currentStrokes = new ArrayList<Stroke>();

  public void setup() throws IOException {
    URL url = Resources.getResource("in/tombo/kashiki/emacs.setting");
    List<String> lines = Resources.readLines(url, Charsets.UTF_8);
    lines.forEach((l) -> parseSetting(l));
  }

  private void parseSetting(String line) {
    if (line.isEmpty() || line.startsWith("#")) {
      return;
    }
    List<Stroke> strokes = new ArrayList<Stroke>();
    String[] split = line.split(" ");
    String[] keys = Arrays.copyOfRange(split, 0, split.length - 1);
    String action = split[split.length - 1];

    Arrays.stream(keys).forEach((key) -> strokes.add(getStroke(key)));
    keybinds.put(strokes, action);
  }

  private Stroke getStroke(String key) {
    Matcher m = ACTION_PATTERN.matcher(key);
    if (!m.find()) {
      throw new RuntimeException("invalid config.");
    }
    String actionString = m.group(1);
    int code = CODE_MAPPING.get(actionString);
    if (key.startsWith("C-A-S-")) {
      return new Stroke(CTRL_ALT_SHIFT, code);
    } else if (key.startsWith("C-A-")) {
      return new Stroke(CTRL_ALT, code);
    } else if (key.startsWith("C-S-")) {
      return new Stroke(CTRL_SHIFT, code);
    } else if (key.startsWith("A-S-")) {
      return new Stroke(ALT_SHIFT, code);
    } else if (key.startsWith("C-")) {
      return new Stroke(CTRL, code);
    } else if (key.startsWith("A-")) {
      return new Stroke(ALT, code);
    } else if (key.startsWith("S-")) {
      return new Stroke(SHIFT, code);
    } else {
      return new Stroke(NONE, code);
    }
  }

  @Override
  public void keyPressed(SupportKey supportKey, int keyCode, long when) {
    this.when = when;

    if (keyCode == VK_SHIFT || keyCode == VK_ALT || keyCode == VK_CONTROL) {
      this.executed = true;
      return;
    }

    Stroke stroke = new Stroke(supportKey, keyCode);

    currentStrokes.add(stroke);
    String actionName = getActionName();
    if (actionName != null) {
      actionRepository.getAction(actionName).ifPresent(action -> {
        action.execute(editor);
      });

      this.executed = true;
    } else {
      if (inStroke) {
        this.executed = true;
      } else {
        this.executed = false;
      }
    }
  }

  private String getActionName() {
    inStroke = false;
    for (Entry<List<Stroke>, String> keybind : keybinds.entrySet()) {
      List<Stroke> keybindStrokes = keybind.getKey();
      if (keybindStrokes.equals(currentStrokes)) {
        currentStrokes.clear();
        return keybind.getValue();
      }
      if (containStroke(keybindStrokes, currentStrokes)) {
        inStroke = true;
      }
    }
    if (!inStroke) {
      currentStrokes.clear();
    }
    return null;
  }

  private boolean containStroke(List<Stroke> keybinding, List<Stroke> current) {
    if (current.size() > keybinding.size()) {
      return false;
    }
    for (int i = 0; i < current.size(); i++) {
      Stroke s = current.get(i);
      if (!s.equals(keybinding.get(i))) {
        return false;
      }
    }
    return true;
  }

  @Override
  public void keyTyped(char typedString, long when) {
    if (this.when + delta > when && executed) {
      return;
    }
    actionRepository.getAction("type").ifPresent((action) -> {
      action.execute(editor, String.valueOf(typedString));
    });
  }
}

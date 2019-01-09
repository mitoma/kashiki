package in.tombo.kashiki.keybind;

import java.util.HashMap;
import java.util.Map;

import in.tombo.kashiki.Editor;
import in.tombo.kashiki.keybind.basic.BackAction;
import in.tombo.kashiki.keybind.basic.BackspaceAction;
import in.tombo.kashiki.keybind.basic.BufferHeadAction;
import in.tombo.kashiki.keybind.basic.BufferLastAction;
import in.tombo.kashiki.keybind.basic.CopyAction;
import in.tombo.kashiki.keybind.basic.CutAction;
import in.tombo.kashiki.keybind.basic.DeleteAction;
import in.tombo.kashiki.keybind.basic.ExitNavyAction;
import in.tombo.kashiki.keybind.basic.ForwardAction;
import in.tombo.kashiki.keybind.basic.FullScreenAction;
import in.tombo.kashiki.keybind.basic.HeadAction;
import in.tombo.kashiki.keybind.basic.KillRingAction;
import in.tombo.kashiki.keybind.basic.LastAction;
import in.tombo.kashiki.keybind.basic.MarkAction;
import in.tombo.kashiki.keybind.basic.NewBufferAction;
import in.tombo.kashiki.keybind.basic.NextAction;
import in.tombo.kashiki.keybind.basic.OpenFileAction;
import in.tombo.kashiki.keybind.basic.PasteAction;
import in.tombo.kashiki.keybind.basic.PreviousAction;
import in.tombo.kashiki.keybind.basic.ReturnAction;
import in.tombo.kashiki.keybind.basic.SaveAction;
import in.tombo.kashiki.keybind.basic.SaveFileAction;
import in.tombo.kashiki.keybind.basic.TypeAction;
import in.tombo.kashiki.keybind.basic.ViewRefleshAction;
import in.tombo.kashiki.keybind.basic.ViewScaleDownAction;
import in.tombo.kashiki.keybind.basic.ViewScaleUpAction;
import in.tombo.kashiki.keybind.demo.XRollMinusAction;
import in.tombo.kashiki.keybind.demo.XRollPlusAction;
import in.tombo.kashiki.keybind.demo.YRollMinusAction;
import in.tombo.kashiki.keybind.demo.YRollPlusAction;
import in.tombo.kashiki.keybind.demo.ZRollMinusAction;
import in.tombo.kashiki.keybind.demo.ZRollPlusAction;

public class ActionRepository {

  private Map<String, Action> actionMap = new HashMap<>();

  public ActionRepository() {
    addAction(new ExitNavyAction());

    addAction(new NoopAction());
    addAction(new TypeAction());
    addAction(new ForwardAction());
    addAction(new BackAction());
    addAction(new PreviousAction());
    addAction(new NextAction());
    addAction(new BufferHeadAction());
    addAction(new BufferLastAction());

    addAction(new HeadAction());
    addAction(new LastAction());
    addAction(new BackspaceAction());
    addAction(new DeleteAction());
    addAction(new ReturnAction());
    addAction(new MarkAction());
    addAction(new PasteAction());
    addAction(new CopyAction());
    addAction(new CutAction());
    addAction(new KillRingAction());

    addAction(new NewBufferAction());
    addAction(new SaveAction());
    addAction(new OpenFileAction());
    addAction(new SaveFileAction());

    addAction(new ViewRefleshAction());
    addAction(new ViewScaleUpAction());
    addAction(new ViewScaleDownAction());

    // demo
    addAction(new XRollPlusAction());
    addAction(new XRollMinusAction());
    addAction(new YRollPlusAction());
    addAction(new YRollMinusAction());
    addAction(new ZRollPlusAction());
    addAction(new ZRollMinusAction());

    addAction(new FullScreenAction());
  }

  public void addAction(Action action) {
    actionMap.put(action.name(), action);
  }

  public void executeAction(Editor editor, String name, String... args) {
    if (!actionMap.containsKey(name)) {
      System.err.println("Unregistered action [" + name + "]");
      return;
    }
    actionMap.get(name).execute(editor, args);
  }
}

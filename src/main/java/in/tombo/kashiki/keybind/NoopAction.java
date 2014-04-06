package in.tombo.kashiki.keybind;

public class NoopAction implements Action {

  @Override
  public String name() {
    return "noop";
  }

  @Override
  public void execute(String... args) {
    // noop
  }

}

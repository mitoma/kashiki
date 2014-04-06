package in.tombo.kashiki.keybind;

public interface Action {
  public String name();

  public void execute(String... args);
}

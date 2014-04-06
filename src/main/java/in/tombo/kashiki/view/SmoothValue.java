package in.tombo.kashiki.view;

import java.util.LinkedList;
import java.util.Queue;

public class SmoothValue {

  private static final int NUM_OF_DIV = 15;
  private Queue<Double> queue = new LinkedList<>();
  private int numOfDiv;
  private double value;
  private double currentValue;
  private MovingType movingType;

  public SmoothValue() {
    this(0, NUM_OF_DIV, MovingType.SMOOTH);
  }

  public SmoothValue(double initialValue) {
    this(initialValue, NUM_OF_DIV, MovingType.SMOOTH);
  }

  public SmoothValue(double initialValue, MovingType movingType) {
    this(initialValue, NUM_OF_DIV, movingType);
  }

  public SmoothValue(double initialValue, int delayTime) {
    this(initialValue, NUM_OF_DIV, MovingType.SMOOTH);
  }

  public SmoothValue(double initialValue, int numOfDiv, MovingType movingType) {
    this.value = initialValue;
    this.currentValue = initialValue;
    this.numOfDiv = numOfDiv;
    this.movingType = movingType;
  }

  public void setValue(double value) {
    setValue(value, 0, movingType);
  }

  public void setValue(double value, int delayTime) {
    setValue(value, delayTime, movingType);
  }

  public void setValue(double value, MovingType movingType) {
    setValue(value, 0, movingType);
  }

  public void setValue(double value, int delayTime, MovingType movingType) {
    setValue(value, 0, movingType, numOfDiv);
  }

  public void setValueWithoutSmooth(double value) {
    this.currentValue += value - this.value;
    this.value = value;
  }

  public void setValue(double value, int delayTime, MovingType movingType, int numOfDiv) {
    this.movingType = movingType;

    double delta = value - this.value;
    if (this.value == value) {
      return;
    }

    this.value = value;
    double[] gains = movingType.gains(numOfDiv);
    Queue<Double> newQueue = new LinkedList<>();
    for (int i = 0; i < delayTime; i++) {
      newQueue.offer(0.0);
    }
    for (int i = 0; i < numOfDiv; i++) {
      if (queue.isEmpty()) {
        newQueue.offer(gains[i] * delta);
      } else {
        newQueue.offer(gains[i] * delta + queue.poll());
      }
    }
    queue = newQueue;
  }

  public void addValue(double value) {
    setValue(this.value + value);
  }

  public double getValue() {
    return getValue(true);
  }

  public double getValue(boolean withUpdate) {
    if (withUpdate) {
      update();
    }
    return currentValue;
  }

  public void update() {
    if (queue.isEmpty()) {
      currentValue = value;
    } else {
      currentValue += queue.poll();
    }
  }

  public boolean isAnimated() {
    return currentValue == value;
  }

  public double getLastValue() {
    return value;
  }

  public MovingType getMovingType() {
    return movingType;
  }

  public void setMovingType(MovingType movingType) {
    this.movingType = movingType;
  }

}

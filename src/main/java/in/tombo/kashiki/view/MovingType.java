package in.tombo.kashiki.view;

import java.util.concurrent.ExecutionException;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public enum MovingType {

  LINER {
    @Override
    protected double[] innerGains(int divOfNum) {
      double[] result = new double[divOfNum];
      double gain = 1.0 / divOfNum;
      for (int i = 0; i < divOfNum; i++) {
        result[i] = gain;
      }
      return result;
    }
  },
  SMOOTH {
    @Override
    protected double[] innerGains(int divOfNum) {
      double[] result = new double[divOfNum];
      double gain = 1.0 / divOfNum;
      double g = Math.PI * gain;
      for (int i = 0; i < divOfNum; i++) {
        double divGain = Math.cos(g * i) - Math.cos(g * (i + 1));
        result[i] = (divGain / 2);
      }
      return result;
    }
  },
  BOUND {
    @Override
    protected double[] innerGains(int divOfNum) {
      double[] result = new double[divOfNum];
      double gain = 1.0 / divOfNum;
      double g = Math.PI * 1.5 * gain;
      double qg = Math.PI / 4.0;
      double dd = Math.sin(qg) * 2;
      for (int i = 0; i < divOfNum; i++) {
        double divGain = Math.sin(g * i + qg) - Math.sin(g * (i + 1) + qg);
        result[i] = (divGain / dd);
      }
      return result;
    }
  };

  LoadingCache<Integer, double[]> cache = CacheBuilder.newBuilder().maximumSize(1000)
      .build(new CacheLoader<Integer, double[]>() {
        @Override
        public double[] load(Integer divOfNum) throws Exception {
          return innerGains(divOfNum);
        }
      });

  protected abstract double[] innerGains(int divOfNum);

  public double[] gains(int divOfNum) {
    try {
      return cache.get(divOfNum);
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }
  }
}

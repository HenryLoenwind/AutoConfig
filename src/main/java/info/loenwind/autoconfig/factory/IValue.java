package info.loenwind.autoconfig.factory;

public interface IValue<T> {
  T get();

  /**
   * Marks this config value as one that needs to be synced from the server to the client. Returns the object itself for chaining.
   * 
   * Note: Not all config values support this.
   */
  default IValue<T> sync() {
    return this;
  }

  /**
   * Marks this config value as one that needs to be in sync between the server and the client but cannot be changed at runtime. Returns the object itself for
   * chaining.
   * 
   * Note: Not all config values support this.
   */
  default IValue<T> startup() {
    return this;
  }

  default IValue<T> setRange(double min, double max) {
    setMin(min);
    setMax(max);
    return this;
  }

  default IValue<T> setMin(double min) {
    return this;
  }

  default IValue<T> setMax(double max) {
    return this;
  }

}
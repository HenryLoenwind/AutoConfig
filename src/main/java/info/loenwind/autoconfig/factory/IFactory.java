package info.loenwind.autoconfig.factory;

public interface IFactory {

  IValueFactory section(String section);

  String getModid();

  String getSection();

  boolean isInInit();

  void addValue(AbstractValue<?> value);

  int getGeneration();

  Configuration getConfig();

}

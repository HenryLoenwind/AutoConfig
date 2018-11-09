package info.loenwind.autoconfig.factory;

import javax.annotation.Nullable;

import net.minecraftforge.common.config.Property;

class DoubleValue extends AbstractValue<Double> {

  protected DoubleValue(IValueFactory owner, String section, String keyname, Double defaultValue, String text) {
    super(owner, section, keyname, defaultValue, text);
  }

  @Override
  protected @Nullable Double makeValue() {
    String comment = getText() + " [range: " + (minValue != null ? minValue : Double.NEGATIVE_INFINITY) + " ~ "
        + (maxValue != null ? maxValue : Double.MAX_VALUE) + ", default: " + defaultValue + "]";
    final Property property = owner.getConfig().get(section, keyname, defaultValue, comment);
    if (minValue != null) {
      property.setMinValue(minValue);
    }
    if (maxValue != null) {
      property.setMaxValue(maxValue);
    }
    property.setRequiresMcRestart(isStartup);
    return property.getDouble(defaultValue);
  }

  @Override
  protected IByteBufAdapter<Double> getDataType() {
    return ByteBufAdapters.DOUBLE;
  }

}
package info.loenwind.autoconfig.factory;

import info.loenwind.autoconfig.util.ConfigProperty;

import javax.annotation.Nullable;


class LimitedStringValue extends AbstractValue<String> {

  private final String[] limit;

  protected LimitedStringValue(IValueFactory owner, String section, String keyname, String defaultValue, String[] limit, String text) {
    super(owner, section, keyname, defaultValue, text);
    this.limit = limit;
  }

  @Override
  protected @Nullable String makeValue() {
    ConfigProperty prop = owner.getConfig().get(section, keyname, defaultValue);
    prop.setValidValues(limit);
    prop.setLanguageKey(keyname);
    prop.setComment(getText() + " [default: " + defaultValue + ", possible values: " + String.join(", ", limit) + "]");
    prop.setRequiresMcRestart(isStartup);
    return prop.getString();
  }

  @Override
  protected IByteBufAdapter<String> getDataType() {
    return ByteBufAdapters.STRING127;
  }

}
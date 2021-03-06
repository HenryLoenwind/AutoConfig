package info.loenwind.autoconfig.util;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public enum Lang {
  NETWORK_BAD_CONFIG("autoconfig.network.bad_config",
      "ERROR: The config value \"%s.%s\" has different values on the server you are connecting to than in your local installation. "
          + "As this value influences how the game is started up, it is now too late to use the server-side value. "
          + "Continuing is not possible. To fix this, manually synchronize the server and client configuration files, or set this value to \"%s\" manually."),
  NETWORK_CONFIG_CONNECTED("autoconfig.network.autosync.connected", "§4§lThis value is determined by the server!\n§e%s"),
  NETWORK_CONFIG_OFFLINE("autoconfig.network.autosync.offline", "%s\n§oThis value must be kept in sync with the server!"),
  NETWORK_CONFIG_SYNC("autoconfig.network.manusync", "%s\n§oThis value will be determined by the server."),

  ;

  private final String key, fallback;

  private Lang(String key, String fallback) {
    this.key = key;
    this.fallback = fallback;
  }

  public String get() {
    return I18n.hasKey(key) ? I18n.format(key) : fallback;
  }

  @SuppressWarnings("null")
  public String get(Object... params) {
    return I18n.hasKey(key) ? I18n.format(key, params) : String.format(fallback, params);
  }

  @SuppressWarnings("null")
  public ITextComponent toChat(Object... params) {
    return I18n.hasKey(key) ? new TranslationTextComponent(key, params) : new TranslationTextComponent(String.format(fallback, params));
  }

}

package info.loenwind.autoconfig.gui;

import java.util.ArrayList;
import java.util.HashMap;
import javax.annotation.Nullable;

import info.loenwind.autoconfig.factory.IRootFactory;
import net.minecraft.client.Minecraft;

//TODO Config GUI
/*public abstract class ConfigFactory implements IModGuiFactory {

  @Override
  public void initialize(@Nullable Minecraft minecraftInstance) {
  }

  @Override
  public @Nullable Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
    return null;
  }

  @Override
  public boolean hasConfigGui() {
    return true;
  }

  @Override
  public @Nullable GuiScreen createConfigGui(@Nullable GuiScreen parentScreen) {
    return parentScreen == null ? null : new GuiConfig(parentScreen, getConfigElements(parentScreen), getModID(), false, false, getTitle(), getTitle2());
  }

  protected abstract String getModID();

  protected abstract String getTitle();

  protected abstract String getTitle2();

  protected abstract Map<String, Configuration> getConfigurations();

  protected Map<String, Configuration> singleConfig(IRootFactory rootFactory) {
    Map<String, Configuration> result = new HashMap<>();
    result.put("", rootFactory.getConfig());
    return result;
  }

  protected List<IConfigElement> getConfigElements(GuiScreen parent) {
    List<IConfigElement> result = new ArrayList<>();

    Map<String, Configuration> configurations = getConfigurations();
    if (configurations.size() == 1) {
      Configuration configuration = configurations.values().iterator().next();

      if (configuration != null) {
        for (String section : configuration.getCategoryNames()) {
          final ConfigCategory category = configuration.getCategory(section);
          category.setLanguageKey(getModID() + ".config." + category.getQualifiedName());
          if (!category.isChild()) {
            result.add(new ConfigElementCategory(category));
          }
        }
      }

    } else {

      for (Entry<String, Configuration> entry : configurations.entrySet()) {
        if (entry != null) {
          String name = entry.getKey();
          Configuration configuration = entry.getValue();
          if (name != null && configuration != null) {
            List<IConfigElement> list = new ArrayList<>();
            for (String section : configuration.getCategoryNames()) {
              final ConfigCategory category = configuration.getCategory(section);
              category.setLanguageKey(getModID() + ".config." + category.getQualifiedName());
              if (!category.isChild()) {
                list.add(new ConfigElementCategory(category));
              }
            }
            result.add(new DummyCategoryElement(name, getModID() + ".config.title." + name, list));
          }
        }
      }
    }

    return result;
  }

}*/

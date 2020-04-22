package cool.dustin.config;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import cool.dustin.model.PluginSetting;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 代码模板配置文件路径
 *
 * @AUTHOR Dustin
 * @DATE 2020/04/14 12:08
 */
@State(
        name = "CodeTemplateSetting",
        storages = @Storage("CodeTemplateSetting.xml")
)
public class CodeTemplateState implements PersistentStateComponent<PluginSetting> {
    private PluginSetting setting = new PluginSetting();

    private CodeTemplateState() {

    }

    public static CodeTemplateState getInstance() {
        return Instance.instance;
    }

    @Nullable
    @Override
    public PluginSetting getState() {
        return setting;
    }

    @Override
    public void loadState(@NotNull PluginSetting setting) {
        this.setting = setting;
    }

    public PluginSetting getSetting() {
        return setting;
    }

    public void setSetting(PluginSetting setting) {
        this.setting = setting;
    }

    private static class Instance {
        private static CodeTemplateState instance = new CodeTemplateState();
    }
}

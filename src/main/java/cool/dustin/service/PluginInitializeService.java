package cool.dustin.service;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import cool.dustin.config.CodeTemplateState;
import cool.dustin.constant.MessageDefine;
import cool.dustin.datas.PluginRuntimeData;
import cool.dustin.model.PluginSetting;
import cool.dustin.util.MessageUtils;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

/**
 * 插件初始化服务实现类
 * @AUTHOR Dustin
 * @DATE 2020/04/13 21:03
 */
public class PluginInitializeService implements StartupActivity {

    @Override
    public void runActivity(@NotNull Project project) {
        CodeTemplateState instance = CodeTemplateState.getInstance();
        PluginSetting setting = instance.getState();
        if (setting == null) {
            MessageUtils.showMessage(MessageDefine.NO_SETTING);
            return;
        }

        if (StringUtils.isEmpty(setting.getTemplateXmlPath())) {
            return;
        }

        TemplateService.getInstance().loadTemplates(setting.getTemplateXmlPath());

        PluginRuntimeData.getInstance().setProject(project);
    }


}

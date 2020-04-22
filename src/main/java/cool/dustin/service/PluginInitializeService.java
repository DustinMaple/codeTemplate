package cool.dustin.service;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import cool.dustin.config.CodeTemplateState;
import cool.dustin.constant.MessageDefine;
import cool.dustin.mock.PluginMock;
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

    private void loadTemplates(String templateXmlPath) {
        if (StringUtils.isEmpty(templateXmlPath)) {
            // 没有指定模板文件
            return;
        }

        // 从模板文件中读取所有模板
        readTemplates(templateXmlPath);
    }

    private void readTemplates(String templateXmlPath) {
        // readFile
        // parseXML
        // templateMap.put(template.name, template)

        PluginMock.mockTemplate();
    }

    @Override
    public void runActivity(@NotNull Project project) {
        CodeTemplateState instance = CodeTemplateState.getInstance();
        System.out.println("插件初始化");
        PluginSetting setting = instance.getState();
        if (setting == null) {
            MessageUtils.showMessage(project, MessageDefine.NO_SETTING);
            return;
        }
        loadTemplates(setting.getTemplateXmlPath());
    }
}

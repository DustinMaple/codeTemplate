package cool.dustin.service.impl;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.file.PsiJavaDirectoryImpl;
import cool.dustin.config.CodeTemplateState;
import cool.dustin.constant.MessageDefine;
import cool.dustin.datas.PluginRuntimeData;
import cool.dustin.model.PluginContext;
import cool.dustin.model.Template;
import cool.dustin.service.TemplateService;
import cool.dustin.util.MessageUtils;
import cool.dustin.xml.XmlUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Collection;
import java.util.List;

/**
 *
 * @AUTHOR Dustin
 * @DATE 2020/04/15 20:29
 */
public class TemplateServiceImpl implements TemplateService {

    @Override
    public void loadTemplates(String configFilePath) {
        if (StringUtils.isEmpty(configFilePath)) {
            // 没有指定模板文件
            return;
        }

        // 从模板文件中读取所有模板
        readTemplates(configFilePath);
    }

    @Override
    public void createSelectTemplate(Project project, PsiElement selectElement, PluginContext context) {
        // 找到目录元素，因为用户选中的元素可能是一个类
        PsiElement rootElement = selectElement;

        while (!(selectElement instanceof PsiJavaDirectoryImpl) && rootElement != null) {
            rootElement = rootElement.getParent();
        }

        if (rootElement == null) {
            MessageUtils.showMessage(MessageDefine.DIRECTORY_ERROR);
            return;
        }

        Template template = PluginRuntimeData.getInstance().getTemplate(context.getSelectTemplate());
        if (template == null) {
            MessageUtils.showMessage(MessageDefine.TEMPLATE_NOT_EXIST);
            return;
        }

        template.generatePsi(project, context, rootElement);
    }

    @Override
    public void saveTemplates() {
        Collection<Template> allTemplate = PluginRuntimeData.getInstance().getAllTemplate();
        String path = CodeTemplateState.getInstance().getSetting().getTemplateXmlPath();
        XmlUtils.writeToXml(allTemplate, path);
    }

    private void readTemplates(String templateXmlPath) {
        List<Template> templates = XmlUtils.readTemplatesWithXml(templateXmlPath);
        PluginRuntimeData.getInstance().addTemplates(templates);
    }
}

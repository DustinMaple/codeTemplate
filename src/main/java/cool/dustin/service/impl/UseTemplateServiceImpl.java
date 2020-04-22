package cool.dustin.service.impl;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.file.PsiJavaDirectoryImpl;
import cool.dustin.constant.MessageDefine;
import cool.dustin.datas.PluginRuntimeData;
import cool.dustin.model.PluginContext;
import cool.dustin.model.Template;
import cool.dustin.service.UseTemplateService;
import cool.dustin.util.MessageUtils;

/**
 *
 * @AUTHOR Dustin
 * @DATE 2020/04/15 20:29
 */
public class UseTemplateServiceImpl implements UseTemplateService {

    @Override
    public void createSelectTemplate(Project project, PsiElement selectElement, String selectTemplate, String templateName) {
        PluginContext context = new PluginContext(selectTemplate, templateName);

        // 找到目录元素，因为用户选中的元素可能是一个类
        PsiElement rootElement = selectElement;

        while (!(selectElement instanceof PsiJavaDirectoryImpl) && rootElement != null) {
            rootElement = rootElement.getParent();
        }

        if (rootElement == null) {
            MessageUtils.showMessage(project, MessageDefine.DIRECTORY_ERROR);
            return;
        }

        Template template = PluginRuntimeData.getInstance().getTemplate(selectTemplate);
        if (template == null) {
            MessageUtils.showMessage(project, MessageDefine.TEMPLATE_NOT_EXIST);
            return;
        }

        template.generatePsi(project, context, rootElement);
    }
}

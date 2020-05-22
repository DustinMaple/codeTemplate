package cool.dustin.service.impl;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.file.PsiJavaDirectoryImpl;
import cool.dustin.config.CodeTemplateState;
import cool.dustin.constant.MessageDefine;
import cool.dustin.datas.PluginRuntimeData;
import cool.dustin.model.AbstractTemplateNode;
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


        String selectPackage = analysisSelectPackage(((PsiJavaDirectoryImpl) rootElement).getVirtualFile().getPath(), context.getProjectRootPath());
        if (StringUtils.isEmpty(selectPackage)) {
            return;
        }

        context.setSelectPackage(selectPackage);
        Template template = PluginRuntimeData.getInstance().getTemplate(context.getSelectTemplate());
        if (template == null) {
            MessageUtils.showMessage(MessageDefine.TEMPLATE_NOT_EXIST);
            return;
        }

        generatePsi(project, context, rootElement, template, template);
    }

    /**
     * 生成PSI元素
     * @param project
     * @param context
     * @param parentElement
     * @param template
     * @param node
     */
    private void generatePsi(Project project, PluginContext context, PsiElement parentElement, Template template, AbstractTemplateNode node) {
        // 自己在父元素下生成自身的psiElement
        PsiElement psiElement = node.createSelfPsiElement(project, context, parentElement, template);
        if (psiElement == null) {
            return;
        }

        // 遍历所有子节点，将自己作为父节点，让子节点创建psiElement
        node.getChildren().forEach(child -> generatePsi(project, context, psiElement, template, child));
    }

    private String analysisSelectPackage(String selectPath, List<String> rootPathList) {
        if (StringUtils.isEmpty(selectPath)) {
            return null;
        }

        for (String rootPath : rootPathList) {
            selectPath = selectPath.replaceAll(rootPath, "");
        }

        return selectPath.replaceFirst("/", "").replaceAll("/", ".");
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
//        PluginMock.mockTemplate();
    }
}

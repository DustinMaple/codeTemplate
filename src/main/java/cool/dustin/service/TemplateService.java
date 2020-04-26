package cool.dustin.service;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;

/**
 * 应用模板的服务接口
 * @AUTHOR Dustin
 * @DATE 2020/04/15 20:29
 */
public interface TemplateService {
    /**
     * 获取单例
     * @return
     */
    static TemplateService getInstance() {
        return ServiceManager.getService(TemplateService.class);
    }

    /**
     * 从配置文件中加载模板
     * @param configFilePath 配置文件路径
     */
    void loadTemplates(String configFilePath);

    /**
     * 使用模板创建所有元素
     * @param project 项目实例
     * @param selectElement 选中的元素
     * @param selectTemplate 选择的模板
     * @param templateName 模块名称
     */
    void createSelectTemplate(Project project, PsiElement selectElement, String selectTemplate, String templateName);

    /**
     * 删除模板
     * @param identify
     */
    void removeTemplate(String identify);
}

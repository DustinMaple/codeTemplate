package cool.dustin.service;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import cool.dustin.model.PluginContext;

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
     * @param context 使用模板上下文
     */
    void createSelectTemplate(Project project, PsiElement selectElement, PluginContext context);

    /**
     * 保存模板到配置文件
     */
    void saveTemplates();
}

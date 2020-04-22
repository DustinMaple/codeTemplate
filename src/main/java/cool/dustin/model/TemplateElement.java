package cool.dustin.model;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;

/**
 * 代码模板的元素
 * @AUTHOR Dustin
 * @DATE 2020/04/14 17:33
 */
public interface TemplateElement {
    /**
     * 获取元素名称
     * @return
     */
    String getName();

    /**
     * 写入xml
     */
    void writeToXml();

    /**
     * 创建PSI文件
     * @param project
     * @param context
     * @param parentElement
     */
    void generatePsi(Project project, PluginContext context, PsiElement parentElement);
}

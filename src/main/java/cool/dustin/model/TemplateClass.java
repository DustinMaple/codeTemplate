package cool.dustin.model;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Computable;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import cool.dustin.constant.TemplateParam;
import cool.dustin.util.JavaLanguageUtil;
import cool.dustin.util.PsiUtils;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 类模板
 * @AUTHOR Dustin
 * @DATE 2020/4/14 14:46
 */
public class TemplateClass extends AbstractTemplateNode {
    /**
     * 类名
     */
    private String className;

    /**
     * 内容
     */
    private String content;

    public TemplateClass() {

    }

    public TemplateClass(TemplateClass templateClass) {
        this.setName(templateClass.getName());
        this.setClassName(templateClass.getClassName());
        this.setContent(templateClass.getContent());
    }


    @Override
    protected PsiElement createSelfPsiElement(Project project, PluginContext context, PsiElement parentElement) {
        String classContent, className;
        classContent = putInParam(context, this.content);
        classContent = insertImport(project, context, classContent);
        className = putInParam(context, this.className);

        PsiFile file = PsiFileFactory.getInstance(project).createFileFromText(className + "." + JavaFileType.INSTANCE.getDefaultExtension(), JavaFileType.INSTANCE, classContent);
        return WriteCommandAction.runWriteCommandAction(project, (Computable<PsiElement>) () -> parentElement.add(file));
    }

    /**
     * 插入import内容
     * @param project
     * @param context
     * @param classContent
     * @return
     */
    private String insertImport(Project project, PluginContext context, String classContent) {
        StringBuilder importInfo = new StringBuilder();
        Collection<PsiClass> importClasses = findImportClasses(project, context, classContent);

        if (importClasses != null) {
            importClasses.forEach(psiClazz -> importInfo.append("import ").append(psiClazz.getQualifiedName()).append(";\n"));
        }

        classContent = importInfo.toString() + "\n\n" + classContent;
        return classContent;
    }

    /**
     * 放入参数
     * @param context
     * @param str
     * @return
     */
    private String putInParam(PluginContext context, String str) {
        str = TemplateParam.TEMPLATE_PARAM.putInParam(str, context.getSystemName());
        str = TemplateParam.HUMP_NAME.putInParam(str, context.getHumpName());
        str = TemplateParam.LINE_NAME.putInParam(str, context.getLineName());

        return str;
    }

    private Collection<PsiClass> findImportClasses(Project project, PluginContext context, String classContent) {
        List<String> importClassNameList = JavaLanguageUtil.analysisImports(classContent);

        if (CollectionUtils.isEmpty(importClassNameList)) {
            return null;
        }

        Set<PsiClass> importClasses = new HashSet<>();
        PsiClass[] classesByName;
        for (String clazzName : importClassNameList) {
            clazzName = putInParam(context, clazzName);

            classesByName = PsiUtils.findClass(clazzName, project);
            if (classesByName.length > 0) {
                importClasses.add(classesByName[0]);
            }
        }
        return importClasses;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public AbstractTemplateNode copy() {
        return new TemplateClass(this);
    }
}

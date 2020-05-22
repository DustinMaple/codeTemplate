package cool.dustin.model;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Computable;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import cool.dustin.config.CodeTemplateState;
import cool.dustin.constant.TemplateClassType;
import cool.dustin.constant.TemplateParam;
import cool.dustin.util.JavaLanguageUtil;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 类模板
 * @AUTHOR Dustin
 * @DATE 2020/4/14 14:46
 */
public class TemplateClass extends AbstractTemplateNode {
    public static SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    /**
     * 内容
     */
    private String content;
    /**
     * 需要引入的类
     */
    private List<String> importClass = Collections.emptyList();
    /**
     * java类型
     */
    private TemplateClassType type = TemplateClassType.CLASS;

    public TemplateClass() {

    }

    public TemplateClass(TemplateClass templateClass) {
        super(templateClass);
        this.type = templateClass.type;
        this.content = templateClass.content;
        this.importClass = new ArrayList<>(templateClass.importClass);
    }

    @Override
    public AbstractTemplateNode copy() {
        return new TemplateClass(this);
    }

    @Override
    public int compareTo(@NotNull AbstractTemplateNode o) {
        if (o instanceof TemplatePackage) {
            return 1;
        }

        if (o instanceof TemplateClass) {
            return Integer.compare(this.type.ordinal(), ((TemplateClass) o).type.ordinal());
        }

        return 0;
    }

    @Override
    public PsiElement createSelfPsiElement(Project project, PluginContext context, PsiElement parentElement, Template template) {
        String classContent, className;
        classContent = insertImport(this.content, template, context.getSelectPackage());
        classContent = putInParam(context, classContent);
        className = putInParam(context, this.getName());

        PsiFile file = PsiFileFactory.getInstance(project).createFileFromText(className + "." + JavaFileType.INSTANCE.getDefaultExtension(), JavaFileType.INSTANCE, classContent);
        return WriteCommandAction.runWriteCommandAction(project, (Computable<PsiElement>) () -> parentElement.add(file));
    }

    /**
     * 插入import内容
     * @param classContent
     * @param template
     * @param selectPackage
     * @return
     */
    private String insertImport(String classContent, Template template, String selectPackage) {
        StringBuilder importInfo = new StringBuilder();
        TemplateClass importClass;

        for (String className : this.importClass) {
            importClass = template.findTemplateClassByName(className.trim());
            if (importClass == null) {
                continue;
            }

            importInfo.append(JavaLanguageUtil.JAVA_IMPORT).append(" ").append(selectPackage).append(importClass.getReferencePath()).append(";\n");
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
        str = TemplateParam.MODULE_NAME.putInParam(str, context.getSystemName());
        str = TemplateParam.HUMP_NAME.putInParam(str, context.getHumpName());
        str = TemplateParam.LINE_NAME.putInParam(str, context.getLineName());
        str = TemplateParam.DATE.putInParam(str, format.format(new Date()));
        str = TemplateParam.AUTHOR.putInParam(str, CodeTemplateState.getInstance().getSetting().getAuthor());

        return str;
    }

    /**
     * 分析java代码时接口还是类
     */
    private void analysisType() {
        if (this.content.contains(JavaLanguageUtil.JAVA_INTERFACE)) {
            this.type = TemplateClassType.INTERFACE;
        }
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        analysisType();
    }

    public TemplateClassType getType() {
        return type;
    }

    public void setType(TemplateClassType type) {
        this.type = type;
    }

    public List<String> getImportClass() {
        return importClass;
    }

    public void setImportClass(List<String> importClass) {
        this.importClass = importClass;
    }

}

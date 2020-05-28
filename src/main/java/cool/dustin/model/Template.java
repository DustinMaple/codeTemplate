package cool.dustin.model;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Computable;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.file.PsiDirectoryFactory;
import com.intellij.psi.impl.file.PsiJavaDirectoryImpl;
import cool.dustin.constant.TemplateParam;
import cool.dustin.util.JavaLanguageUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 代码模板，可以直接用于创建一批包和代码
 * @AUTHOR Dustin
 * @DATE 2020/4/13 20:47
 */
public class Template extends AbstractTemplateNode {
    /**
     * 描述
     */
    private String description = "";
    /**
     * 类名映射模板类
     */
    private Map<String, TemplateClass> classMap = new HashMap<>();

    public Template() {

    }

    public Template(Template template) {
        super(template);
        this.description = template.description;
        for (Map.Entry<String, TemplateClass> entry : template.classMap.entrySet()) {
            this.classMap.put(entry.getKey(), (TemplateClass) entry.getValue().copy());
        }
    }

    @Override
    public PsiElement createSelfPsiElement(Project project, PluginContext context, PsiElement parentElement, Template template) {
        if (!(parentElement instanceof PsiJavaDirectoryImpl)) {
            throw new RuntimeException("创建模板错误，parentElement参数不是一个目录");
        }

        if (StringUtils.isEmpty(context.getSystemName())) {
            return null;
        }

        PsiDirectoryFactory dirFactory = PsiDirectoryFactory.getInstance(project);
        PsiDirectory directory = dirFactory.createDirectory(((PsiJavaDirectoryImpl) parentElement).getVirtualFile());

        return WriteCommandAction.runWriteCommandAction(project, (Computable<PsiDirectory>) () -> directory.createSubdirectory(context.getSystemName()));
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public AbstractTemplateNode copy() {
        return new Template(this);
    }

    @Override
    public int compareTo(@NotNull AbstractTemplateNode o) {
        return 0;
    }

    public void prepareCache() {
        cachedTemplateClass(this.getChildren());
    }

    /**
     * 使用类名映射模板类，将模板类缓存起来
     * @param children
     */
    private void cachedTemplateClass(Set<AbstractTemplateNode> children) {
        for (AbstractTemplateNode node : children) {
            if (node instanceof TemplateClass) {
                classMap.put(node.getName(), (TemplateClass) node);
            } else if (node instanceof TemplatePackage) {
                cachedTemplateClass(node.getChildren());
            }
        }
    }

    public TemplateClass findTemplateClassByName(String name) {
        return classMap.get(name);
    }

    @Override
    public void refreshReference(String parentReference){
        this.setReferencePath(JavaLanguageUtil.JAVA_DOT + TemplateParam.MODULE_NAME.getExpression());
        refreshChildren(this);
    }

    private void refreshChildren(AbstractTemplateNode parent) {
        for(AbstractTemplateNode node : parent.getChildren()){
            node.refreshReference(parent.getReferencePath());
            if(CollectionUtils.isNotEmpty(node.getChildren())){
                refreshChildren(node);
            }
        }
    }
}

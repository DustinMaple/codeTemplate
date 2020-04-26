package cool.dustin.model;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Computable;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.file.PsiDirectoryFactory;
import com.intellij.psi.impl.file.PsiJavaDirectoryImpl;

/**
 * 代码模板，可以直接用于创建一批包和代码
 * @AUTHOR Dustin
 * @DATE 2020/4/13 20:47
 */
public class Template extends AbstractTemplateNode {
    private String description = "";

    public Template() {

    }

    public Template(Template template) {
        this.setName(template.getName());
        this.setDescription(template.getDescription());
        for (AbstractTemplateNode child : template.getChildren()) {
            this.getChildren().add(child.copy());
        }
    }

    @Override
    protected PsiElement createSelfPsiElement(Project project, PluginContext context, PsiElement parentElement) {
        if (!(parentElement instanceof PsiJavaDirectoryImpl)) {
            throw new RuntimeException("创建模板错误，parentElement参数不是一个目录");
        }

        PsiDirectoryFactory dirFactory = PsiDirectoryFactory.getInstance(project);
        PsiDirectory directory = dirFactory.createDirectory(((PsiJavaDirectoryImpl) parentElement).getVirtualFile());

        return WriteCommandAction.runWriteCommandAction(project, (Computable<PsiDirectory>) () -> directory.createSubdirectory(context.getTemplateName()));
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
}

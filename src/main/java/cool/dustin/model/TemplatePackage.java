package cool.dustin.model;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Computable;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.file.PsiDirectoryFactory;
import com.intellij.psi.impl.file.PsiJavaDirectoryImpl;
import org.jetbrains.annotations.NotNull;

/**
 * 模板的包
 * @AUTHOR Dustin
 * @DATE 2020/4/13 21:07
 */
public class TemplatePackage extends AbstractTemplateNode {

    public TemplatePackage() {

    }

    public TemplatePackage(TemplatePackage templatePackage) {
        super(templatePackage);
    }

    @Override
    public PsiElement createSelfPsiElement(Project project, PluginContext context, PsiElement parentElement, Template template) {
        if (!(parentElement instanceof PsiJavaDirectoryImpl)) {
            throw new RuntimeException("创建模板错误，parentElement参数不是一个目录");
        }

        PsiDirectoryFactory dirFactory = PsiDirectoryFactory.getInstance(project);
        PsiDirectory directory = dirFactory.createDirectory(((PsiJavaDirectoryImpl) parentElement).getVirtualFile());
        return WriteCommandAction.runWriteCommandAction(project, (Computable<PsiDirectory>) () -> directory.createSubdirectory(this.getName()));
    }

    @Override
    public AbstractTemplateNode copy() {
        return new TemplatePackage(this);
    }

    @Override
    public int compareTo(@NotNull AbstractTemplateNode o) {
        if (o instanceof TemplatePackage) {
            return 0;
        }
        return -1;
    }
}

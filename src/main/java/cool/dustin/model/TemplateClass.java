package cool.dustin.model;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Computable;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.impl.file.PsiJavaDirectoryImpl;
import cool.dustin.util.PsiUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 类模板
 * @AUTHOR Dustin
 * @DATE 2020/4/14 14:46
 */
public class TemplateClass extends AbstractTemplateNode {
    /**
     * 内容
     */
    private String content;

    /**
     * 导入的类
     */
    private String[] importClass;

    @Override
    public void writeToXml() {

    }

    @Override
    protected PsiElement createSelfPsiElement(Project project, PluginContext context, PsiElement parentElement) {
        PsiJavaDirectoryImpl directory = (PsiJavaDirectoryImpl) parentElement;
        StringBuilder importInfo = new StringBuilder();

        Collection<PsiClass> importClasses = findImportClasses(project);

        String classContent = content;

        if (importClasses != null) {
            importClasses.forEach(psiClazz -> importInfo.append("import ").append(psiClazz.getQualifiedName()).append(";\n"));
        }

        classContent = importInfo.toString() + "\n\n" + content;

        PsiFile file = PsiFileFactory.getInstance(project).createFileFromText(this.getName() + "." + JavaFileType.INSTANCE.getDefaultExtension(), JavaFileType.INSTANCE, classContent);
        return WriteCommandAction.runWriteCommandAction(project, (Computable<PsiElement>) () -> parentElement.add(file));
    }

    private Collection<PsiClass> findImportClasses(Project project) {
        if (importClass == null || importClass.length <= 0) {
            return null;
        }

        Set<PsiClass> importClasses = new HashSet<>();
        PsiClass[] classesByName = null;
        for (String clazzName : importClass) {
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

    public String[] getImportClass() {
        return importClass;
    }

    public void setImportClass(String[] importClass) {
        this.importClass = importClass;
    }
}

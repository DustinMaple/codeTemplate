package cool.dustin.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import cool.dustin.ui.CreateModuleDialog;
import org.jetbrains.annotations.NotNull;

/**
 * 打开应用模板菜单
 * @AUTHOR Dustin
 * @DATE 2020/04/14 17:23
 */
public class NewTemplateAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        // popup use template
        Project project = e.getProject();
        PsiElement selectElement = e.getData(CommonDataKeys.PSI_ELEMENT);
        new CreateModuleDialog(project, selectElement).showAndGet();
    }
}

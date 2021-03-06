package cool.dustin.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import cool.dustin.model.PluginContext;
import cool.dustin.service.TemplateService;
import cool.dustin.ui.forms.CreateModuleForm;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 *
 * @AUTHOR Dustin
 * @DATE 2020/04/20 15:25
 */
public class CreateModuleDialog extends DialogWrapper {
    /**
     * 调用的项目
     */
    private Project project;
    /**
     * 选中的PSI元素
     */
    private PsiElement selectElement;
    private CreateModuleForm form = new CreateModuleForm();

    public CreateModuleDialog(@Nullable Project project, PsiElement selectElement) {
        super(project, true);
        this.project = project;
        this.selectElement = selectElement;
        init();
        setTitle("New Template");
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return form.getRoot();
    }

    @Override
    public void doCancelAction() {
        super.doCancelAction();
    }

    @Override
    protected void doOKAction() {
        PluginContext context = new PluginContext(form.getSelectTemplate(), form.getSystemName(), form.getHumpName(), form.getLineName());
        for (VirtualFile vf : ProjectRootManager.getInstance(project).getContentSourceRoots()) {
            context.getProjectRootPath().add(vf.getPath());
        }
        TemplateService.getInstance().createSelectTemplate(project, selectElement, context);
        super.doOKAction();
    }
}

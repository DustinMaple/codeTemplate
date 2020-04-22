package cool.dustin.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.psi.PsiElement;
import cool.dustin.constant.MessageType;
import cool.dustin.service.UseTemplateService;
import cool.dustin.ui.forms.NewTemplateForm;
import cool.dustin.util.MessageUtils;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 *
 * @AUTHOR Dustin
 * @DATE 2020/04/20 15:25
 */
public class NewTemplateDialog extends DialogWrapper {
    /**
     * 调用的项目
     */
    private Project project;
    /**
     * 选中的PSI元素
     */
    private PsiElement selectElement;
    private NewTemplateForm form = new NewTemplateForm();

    public NewTemplateDialog(@Nullable Project project, PsiElement selectElement) {
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
        super.doOKAction();
        MessageUtils.showMessageLog(project, MessageType.INFO, "name:{},template:{}", form.getName(), form.getSelectTemplate());
        UseTemplateService.getInstance().createSelectTemplate(project, selectElement, form.getSelectTemplate(), form.getName());
    }
}

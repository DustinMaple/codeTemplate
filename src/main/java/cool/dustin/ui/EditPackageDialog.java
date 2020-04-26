package cool.dustin.ui;

import com.intellij.openapi.ui.DialogWrapper;
import cool.dustin.model.AbstractTemplateNode;
import cool.dustin.model.TemplatePackage;
import cool.dustin.ui.forms.EditPackageForm;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 *
 * @AUTHOR Dustin
 * @DATE 2020/04/22 11:28
 */
public class EditPackageDialog extends DialogWrapper {
    /**
     * 界面
     */
    private EditPackageForm editPackageForm = null;
    /**
     * 父对话框
     */
    private final EditTemplateDialog templateDialog;
    /**
     * 选中的包
     */
    private final TemplatePackage selectPackage;
    /**
     * 父节点
     */
    private final AbstractTemplateNode parent;


    public EditPackageDialog(EditTemplateDialog templateDialog, TemplatePackage templateNode, AbstractTemplateNode parent) {
        super(true);
        this.templateDialog = templateDialog;
        this.selectPackage = templateNode;
        this.parent = parent;
        init();
        setTitle("Edit Package");
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        if (editPackageForm == null) {
            editPackageForm = new EditPackageForm(parent.getName());
        }
        return editPackageForm.getRoot();
    }

    @Override
    protected void doOKAction() {
        super.doOKAction();
        // 根据form构建TemplatePackage，添加到parent中
        // 或者直接修改选中的package
        templateDialog.changed();
    }


}

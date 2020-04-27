package cool.dustin.ui;

import com.intellij.openapi.ui.DialogWrapper;
import cool.dustin.constant.MessageType;
import cool.dustin.model.AbstractTemplateNode;
import cool.dustin.model.TemplatePackage;
import cool.dustin.ui.forms.EditPackageForm;
import cool.dustin.util.MessageUtils;
import org.apache.commons.lang.StringUtils;
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
            editPackageForm = new EditPackageForm(parent.getName(), selectPackage);
        }
        return editPackageForm.getRoot();
    }

    @Override
    protected void doOKAction() {
        String packageName = editPackageForm.getPackageName();
        if (StringUtils.isEmpty(packageName)) {
            MessageUtils.showMessageLog(MessageType.ERROR, "请输入包名");
            return;
        }

        TemplatePackage templatePackage = new TemplatePackage();
        templatePackage.setName(packageName);
        parent.addChild(templatePackage);

        templateDialog.changed();
        super.doOKAction();
    }


}

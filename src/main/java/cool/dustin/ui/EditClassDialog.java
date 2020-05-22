package cool.dustin.ui;

import com.intellij.openapi.ui.DialogWrapper;
import cool.dustin.constant.MessageType;
import cool.dustin.model.AbstractTemplateNode;
import cool.dustin.model.Template;
import cool.dustin.model.TemplateClass;
import cool.dustin.ui.forms.EditClassForm;
import cool.dustin.util.MessageUtils;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 *
 * @AUTHOR Dustin
 * @DATE 2020/04/22 11:28
 */
public class EditClassDialog extends DialogWrapper {
    private final Template template;
    /**
     * 界面
     */
    private EditClassForm editClassForm = null;
    /**
     * 父对话框
     */
    private EditTemplateDialog templateDialog;
    /**
     * 选中的class
     */
    private TemplateClass selectClass;
    /**
     * 父节点
     */
    private AbstractTemplateNode parent;

    public EditClassDialog(EditTemplateDialog templateDialog, TemplateClass selectClass, AbstractTemplateNode parent, Template template) {
        super(true);
        this.templateDialog = templateDialog;
        this.selectClass = selectClass;
        this.parent = parent;
        this.template = template;
        init();
        setTitle("Edit Class");
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        if (editClassForm == null) {
            editClassForm = new EditClassForm(parent.getName(), this.selectClass);
        }
        return editClassForm.getRoot();
    }

    @Override
    protected void doOKAction() {
        // 根据form构建TemplatePackage，添加到parent中
        String className = editClassForm.getClassName();

        if (StringUtils.isEmpty(className)) {
            MessageUtils.showMessageLog(MessageType.ERROR, "类名不能为空");
            return;
        }

        String classContent = editClassForm.getClassContent();
        if (StringUtils.isEmpty(classContent)) {
            MessageUtils.showMessageLog(MessageType.ERROR, "类内容不能为空");
            return;
        }

        if (this.selectClass == null) {
            TemplateClass templateClassByName = template.findTemplateClassByName(className);
            if (templateClassByName != null) {
                MessageUtils.showMessageLog(MessageType.ERROR, "类名不能重复");
                return;
            }

            this.selectClass = new TemplateClass();
            this.selectClass.setName(className);
            this.selectClass.setReferencePath(parent.getReferencePath() + "." + className);
            parent.addChild(this.selectClass);
        } else {
            this.selectClass.setName(className);
        }

        this.selectClass.setContent(classContent);
        this.selectClass.setImportClass(editClassForm.getImportClass());

        templateDialog.changed();
        super.doOKAction();
    }
}

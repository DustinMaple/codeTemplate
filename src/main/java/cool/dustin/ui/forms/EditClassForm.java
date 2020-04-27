package cool.dustin.ui.forms;

import cool.dustin.model.TemplateClass;

import javax.swing.*;

/**
 *
 * @AUTHOR Dustin
 * @DATE 2020/04/23 16:41
 */
public class EditClassForm {
    private final String parentName;
    private TemplateClass selectClass;
    private JPanel root;
    private JTextField parentNameField;
    private JTextField classNameField;
    private JTextArea classContentField;

    private TemplateClass templateClass = new TemplateClass();

    public EditClassForm(String parentName, TemplateClass selectClass) {
        this.parentName = parentName;
        this.selectClass = selectClass;
        init();
    }

    private void init() {
        parentNameField.setText(parentName);
        parentNameField.setEditable(false);

        if (selectClass != null) {
            this.classNameField.setText(selectClass.getName());
            this.classContentField.setText(selectClass.getContent());
        }
    }

    public JPanel getRoot() {
        return root;
    }

    public TemplateClass getTemplateClass() {
        return templateClass;
    }

    public String getClassName() {
        return classNameField.getText();
    }

    public String getClassContent() {
        return classContentField.getText();
    }
}

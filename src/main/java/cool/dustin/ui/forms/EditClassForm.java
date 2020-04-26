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
    private JPanel root;
    private JTextField parentNameField;
    private JTextField classNameField;
    private JTextArea textArea1;

    private TemplateClass templateClass = new TemplateClass();

    public EditClassForm(String parentName) {
        this.parentName = parentName;
        init();
    }

    private void init() {
        parentNameField.setText(parentName);
        parentNameField.setEditable(false);
    }

    public JPanel getRoot() {
        return root;
    }

    public TemplateClass getTemplateClass() {
        return templateClass;
    }
}

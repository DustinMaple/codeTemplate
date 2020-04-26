package cool.dustin.ui.forms;

import cool.dustin.model.TemplatePackage;

import javax.swing.*;

/**
 *
 * @AUTHOR Dustin
 * @DATE 2020/04/23 16:42
 */
public class EditPackageForm {
    private JPanel root;
    private JTextField packageNameField;
    private JTextField parentNameField;

    private TemplatePackage templatePackage = new TemplatePackage();
    private String parentName;

    public EditPackageForm(String parentName) {
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

    public TemplatePackage getTemplatePackage() {
        return templatePackage;
    }
}

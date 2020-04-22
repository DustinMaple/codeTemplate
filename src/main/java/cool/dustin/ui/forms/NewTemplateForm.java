package cool.dustin.ui.forms;

import cool.dustin.datas.PluginRuntimeData;
import cool.dustin.model.Template;

import javax.swing.*;
import java.util.Collection;

/**
 *
 * @AUTHOR Dustin
 * @DATE 2020/04/20 16:19
 */
public class NewTemplateForm {
    private JPanel root;
    private JTextField name;
    private JComboBox<String> selectTemplate;

    public NewTemplateForm() {
        Collection<Template> allTemplate = PluginRuntimeData.getInstance().getAllTemplate();
        int i = 0;
        for (Template template : allTemplate) {
            this.selectTemplate.addItem(template.getName());
        }
    }

    public JPanel getRoot() {
        return root;
    }

    public String getName() {
        return name.getText();
    }

    public String getSelectTemplate() {
        return (String) selectTemplate.getSelectedItem();
    }
}

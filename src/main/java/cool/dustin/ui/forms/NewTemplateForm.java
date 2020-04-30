package cool.dustin.ui.forms;

import com.intellij.ui.DocumentAdapter;
import cool.dustin.datas.PluginRuntimeData;
import cool.dustin.model.Template;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.util.Collection;

/**
 *
 * @AUTHOR Dustin
 * @DATE 2020/04/20 16:19
 */
public class NewTemplateForm {
    private JPanel root;
    private JTextField systemNameField;
    private JComboBox<String> selectTemplate;
    private JTextField humpNameField;
    private JTextField lineNameField;
    private boolean changedLineName = false;
    private boolean changedHumpName = false;
    private String humpName = "";
    private String lineName = "";

    public NewTemplateForm() {
        init();
    }

    private void init() {
        initTemplateList();
        systemNameField.getDocument().addDocumentListener(new DocumentAdapter() {
            @Override
            protected void textChanged(@NotNull DocumentEvent e) {
                transformToOtherName(systemNameField.getText());
            }
        });

        humpNameField.getDocument().addDocumentListener(new DocumentAdapter() {
            @Override
            protected void textChanged(@NotNull DocumentEvent e) {
                changedHumpName = !humpNameField.getText().equals(humpName);
            }
        });

        lineNameField.getDocument().addDocumentListener(new DocumentAdapter() {
            @Override
            protected void textChanged(@NotNull DocumentEvent e) {
                changedLineName = !lineNameField.getText().equals(lineName);
            }
        });
    }

    private void transformToOtherName(String templateName) {
        char[] chars = templateName.toCharArray();
        StringBuilder lineNameBuilder = new StringBuilder();
        String humpName = "", line = "_";
        char pre = 0;

        for (char c : chars) {
            if (chars.length > 1) {
                humpName = transformToHump(chars);
            }

            if (pre != 0 && isUpper(c) && !isUpper(pre)) {
                lineNameBuilder.append(line).append(c);
            } else {
                lineNameBuilder.append(c);
            }

            pre = c;
        }

        this.humpName = humpName;
        this.lineName = StringUtils.lowerCase(lineNameBuilder.toString());

        if (!changedHumpName) {
            humpNameField.setText(this.humpName);
        }

        if (!changedLineName) {
            lineNameField.setText(this.lineName);
        }
    }

    private boolean isUpper(char c) {
        return c >= 'A' && c <= 'Z';
    }

    private String transformToHump(char[] chars) {
        if (chars[0] > 90) {
            chars[0] = (char) (chars[0] - 32);
        }
        return String.valueOf(chars);
    }

    private void initTemplateList() {
        Collection<Template> allTemplate = PluginRuntimeData.getInstance().getAllTemplate();
        for (Template template : allTemplate) {
            this.selectTemplate.addItem(template.getName());
        }
    }

    public JPanel getRoot() {
        return root;
    }

    public String getSystemName() {
        return systemNameField.getText();
    }

    public String getHumpName() {
        return humpNameField.getText();
    }

    public String getLineName() {
        return lineNameField.getText();
    }

    public String getSelectTemplate() {
        return (String) selectTemplate.getSelectedItem();
    }
}

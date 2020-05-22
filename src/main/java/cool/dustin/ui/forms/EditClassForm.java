package cool.dustin.ui.forms;

import cool.dustin.constant.TemplateConstants;
import cool.dustin.constant.TemplateParam;
import cool.dustin.model.TemplateClass;
import cool.dustin.util.JavaLanguageUtil;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @AUTHOR Dustin
 * @DATE 2020/04/23 16:41
 */
public class EditClassForm {

    //---------------------- UI Element start -----------------------
    /**
     * 上级界面
     */
    private JPanel root;
    /**
     * 上级节点名称
     */
    private JTextField parentNameField;
    /**
     * 名称自读那
     */
    private JTextField nameField;
    /**
     * 类内容字段
     */
    private JTextArea classContentField;
    /**
     * 类描述字段
     */
    private JTextPane description;
    /**
     * 引入字段
     */
    private JTextField importsField;
    //---------------------- UI Element end -------------------------
    /**
     * 选中的类
     */
    private TemplateClass selectClass;
    private final String parentName;
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
            this.nameField.setText(selectClass.getName());
            this.importsField.setText(JavaLanguageUtil.noSquareBrackets(selectClass.getImportClass().toString()));
            this.classContentField.setText(selectClass.getContent());
        }

        description.setText("参数只有在类名和类的内容中有效，以下为可用参数\n" + TemplateParam.getDescription());
    }

    public JPanel getRoot() {
        return root;
    }

    public String getClassContent() {
        return classContentField.getText();
    }

    public String getClassName() {
        return nameField.getText();
    }

    public List<String> getImportClass() {
        if (StringUtils.isEmpty(importsField.getText())) {
            return Collections.emptyList();
        }
        return Arrays.asList(importsField.getText().split(TemplateConstants.STRING_LIST_SPLIT));
    }
}

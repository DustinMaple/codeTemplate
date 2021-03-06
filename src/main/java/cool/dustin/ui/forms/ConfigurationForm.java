package cool.dustin.ui.forms;

import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.DocumentAdapter;
import cool.dustin.config.CodeTemplateConfiguration;
import cool.dustin.config.CodeTemplateState;
import cool.dustin.constant.MessageType;
import cool.dustin.datas.PluginRuntimeData;
import cool.dustin.service.TemplateService;
import cool.dustin.ui.EditTemplateDialog;
import cool.dustin.util.MessageUtils;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.util.Enumeration;

/**
 *
 * @AUTHOR Dustin
 * @DATE 2020/04/21 21:15
 */
public class ConfigurationForm {
    //---------------------- UI Element start -----------------------
    /**
     * 界面根元素
     */
    private JPanel root;
    /**
     * 配置文件路径输入框
     */
    private JTextField configFilePathField;
    /**
     * 选择配置文件按钮
     */
    private JButton selectButton;
    /**
     * 创建新模板按钮
     */
    private JButton createButton;
    /**
     * 编辑模板按钮
     */
    private JButton editButton;
    /**
     * 删除模板按钮
     */
    private JButton deleteButton;
    /**
     * 模板表格，展示模板名和模板描述
     */
    private JTable templatesTable;
    /**
     * 作者输入框
     */
    private JTextField authorField;
    /**
     * 表格模型
     */
    private DefaultTableModel tableModel;
    /**
     * 模板配置设置
     */
    private CodeTemplateConfiguration codeTemplateConfiguration;
    //---------------------- UI Element end -----------------------

    public ConfigurationForm(CodeTemplateConfiguration codeTemplateConfiguration) {
        this.codeTemplateConfiguration = codeTemplateConfiguration;
        init();
    }

    public void refreshTableData() {
        clearTable();
        PluginRuntimeData.getInstance().getAllTemplate().forEach(template -> tableModel.addRow(new String[]{template.getName(), template.getDescription()}));
    }

    private void clearTable() {
        for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
            tableModel.removeRow(i);
        }
    }

    private void init() {
        selectButton.addActionListener((event) -> doSelectFile());
        createButton.addActionListener((event) -> doCreate());
        editButton.addActionListener((event) -> doEdit());
        deleteButton.addActionListener((event) -> doDelete());

        initTable();

        if (StringUtils.isEmpty(CodeTemplateState.getInstance().getSetting().getTemplateXmlPath())) {
            return;
        }

        initConfigFilePath();
        initAuthor();
        refreshTableData();
    }

    private void initAuthor() {
        String author = CodeTemplateState.getInstance().getSetting().getAuthor();
        if (StringUtils.isNotEmpty(author)) {
            authorField.setText(author);
        }

        authorField.getDocument().addDocumentListener(new DocumentAdapter() {
            @Override
            protected void textChanged(@NotNull DocumentEvent e) {
                codeTemplateConfiguration.setModified(true);
            }
        });
    }

    private void initConfigFilePath() {
        String path = CodeTemplateState.getInstance().getSetting().getTemplateXmlPath();
        if (StringUtils.isNotEmpty(path)) {
            configFilePathField.setText(path);
        }

        configFilePathField.getDocument().addDocumentListener(new DocumentAdapter() {
            @Override
            protected void textChanged(@NotNull DocumentEvent e) {
                codeTemplateConfiguration.setModified(true);
            }
        });
    }

    private void doSelectFile() {
        VirtualFile selectFile = openFile();

        if (selectFile == null || selectFile.isDirectory()) {
            return;
        }

        String path = selectFile.getPath();
        configFilePathField.setText(path);
        CodeTemplateState.getInstance().getSetting().setTemplateXmlPath(path);
        TemplateService.getInstance().loadTemplates(path);
        refreshTableData();
    }

    private VirtualFile openFile() {
        String path = CodeTemplateState.getInstance().getSetting().getTemplateXmlPath();
        Project project = PluginRuntimeData.getInstance().getProject();
        if (project == null) {
            return null;
        }
        final VirtualFile current = path == null || path.isEmpty() ? project.getProjectFile() : LocalFileSystem.getInstance().findFileByPath(path);
        return FileChooser.chooseFile(FileChooserDescriptorFactory.createSingleFileNoJarsDescriptor(), null, project, current);
    }

    private void initTable() {
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.addColumn("identify");
        tableModel.addColumn("description");
        templatesTable.setModel(tableModel);

        // 修改表格样式
        Enumeration<TableColumn> columns = templatesTable.getColumnModel().getColumns();
        columns.nextElement().setPreferredWidth(100);
        columns.nextElement().setPreferredWidth(300);
        templatesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        templatesTable.removeEditor();
    }

    private void doCreate() {
        new EditTemplateDialog(this, null).showAndGet();
    }

    private void doEdit() {
        int selectedRow = templatesTable.getSelectedRow();
        if (selectedRow < 0 || selectedRow >= templatesTable.getRowCount()) {
            MessageUtils.showMessageLog(MessageType.ERROR, "选择了错误的行号：{}", selectedRow);
            return;
        }

        String identify = (String) templatesTable.getValueAt(selectedRow, 0);

        new EditTemplateDialog(this, identify).showAndGet();
    }

    private void doDelete() {
        int selectedRow = templatesTable.getSelectedRow();
        if (selectedRow < 0 || selectedRow >= templatesTable.getRowCount()) {
            MessageUtils.showMessageLog(MessageType.ERROR, "选择了错误的行号：{}", selectedRow);
            return;
        }

        String identify = (String) tableModel.getValueAt(selectedRow, 0);
        tableModel.removeRow(selectedRow);

        PluginRuntimeData.getInstance().removeTemplate(identify);

        codeTemplateConfiguration.setModified(true);
    }

    public JPanel getRoot() {
        return root;
    }

    public CodeTemplateConfiguration getCodeTemplateConfiguration() {
        return codeTemplateConfiguration;
    }

    public String getConfigFilePath() {
        return configFilePathField.getText();
    }

    public String getAuthor() {
        return authorField.getText();
    }
}

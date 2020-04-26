package cool.dustin.config;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import cool.dustin.ui.forms.ConfigurationForm;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 *
 * @AUTHOR Dustin
 * @DATE 2020/04/21 18:00
 */
public class CodeTemplateConfiguration implements Configurable {
    private ConfigurationForm form;

    private boolean modified = false;

    public CodeTemplateConfiguration(Project project) {
        this.form = new ConfigurationForm(this);
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "CodeTemplates";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        return form.getRoot();
    }

    @Override
    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
        form.refreshTableData();
    }

    @Override
    public void apply() throws ConfigurationException {
        System.out.println("save");
        modified = false;
    }
}

package cool.dustin.config;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurableProvider;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 *
 * @AUTHOR Dustin
 * @DATE 2020/04/21 18:00
 */
public class CodeTemplateConfigurationProvider extends ConfigurableProvider {

    private final Project project;

    public CodeTemplateConfigurationProvider(@NotNull Project project) {
        super();
        this.project = project;
    }

    @Nullable
    @Override
    public Configurable createConfigurable() {
        return new CodeTemplateConfiguration(project);
    }
}

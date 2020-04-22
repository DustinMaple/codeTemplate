package cool.dustin.config;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurableProvider;
import org.jetbrains.annotations.Nullable;

/**
 *
 * @AUTHOR Dustin
 * @DATE 2020/04/21 18:00
 */
public class CodeTemplateConfigurationProvider extends ConfigurableProvider {
    @Nullable
    @Override
    public Configurable createConfigurable() {
        return new CodeTemplateConfiguration();
    }
}

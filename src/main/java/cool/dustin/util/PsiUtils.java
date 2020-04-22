package cool.dustin.util;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiShortNamesCache;

/**
 * PSI相关工具类
 * @AUTHOR Dustin
 * @DATE 2020/04/20 18:38
 */
public class PsiUtils {
    /**
     * 根据类的简短名查找类的PsiClass实例
     * @param simpleName 类名
     * @param project 所在项目
     * @return
     */
    public static PsiClass[] findClass(String simpleName, Project project) {
        return PsiShortNamesCache.getInstance(project).getClassesByName(simpleName, GlobalSearchScope.projectScope(project));
    }
}

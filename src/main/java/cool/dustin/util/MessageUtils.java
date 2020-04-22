package cool.dustin.util;

import com.intellij.openapi.project.Project;
import cool.dustin.constant.MessageDefine;
import cool.dustin.constant.MessageType;

/**
 * 处理各种需要反馈给用户的消息
 * @AUTHOR Dustin
 * @DATE 2020/04/15 20:33
 */
public class MessageUtils {
    private static final String PARAM_SYMBOL = "\\{}";

    /**
     * 提示信息
     * @param messageDefine 固定的消息定义
     */
    public static void showMessage(Project project, MessageDefine messageDefine) {
        messageDefine.getType().notice(project, messageDefine.getMessage());
    }

    /**
     * 自由提示信息
     * @param type 消息类型
     * @param logStr 携带参数占位符的提示信息
     * @param params 参数
     */
    public static void showMessageLog(Project project, MessageType type, String logStr, Object... params) {
        for (Object param : params) {
            logStr = logStr.replaceFirst(PARAM_SYMBOL, param.toString());
        }
        type.notice(project, logStr);
    }
}

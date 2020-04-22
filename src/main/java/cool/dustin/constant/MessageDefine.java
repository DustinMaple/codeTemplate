package cool.dustin.constant;

import static cool.dustin.constant.MessageType.ERROR;
import static cool.dustin.constant.MessageType.INFO;

/**
 * 系统中各种消息
 * @AUTHOR Dustin
 * @DATE 2020/04/15 20:35
 */
public enum MessageDefine {
    /**
     * 成功
     */
    SUCCESS("success", INFO),
    /**
     * 模板不存在
     */
    TEMPLATE_NOT_EXIST("template not exist!!!", ERROR),
    /**
     * 没有选择正确的目录
     */
    DIRECTORY_ERROR("Please select package!!!", ERROR),
    /**
     * 设置不存在
     */
    NO_SETTING("Setting is not exits.", ERROR);

    /**
     * 消息
     */
    private String message;
    /**
     * 类型
     */
    private MessageType type;

    MessageDefine(String message, MessageType type) {
        this.message = message;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }
}

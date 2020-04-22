package cool.dustin.constant;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;

/**
 * 消息类型
 * @AUTHOR Dustin
 * @DATE 2020/04/15 20:37
 */
public enum MessageType {
    /**
     * 信息
     */
    INFO(NotificationType.INFORMATION) {

    },
    /**
     * 错误
     */
    ERROR(NotificationType.ERROR),
    ;

    private NotificationGroup group = new NotificationGroup("message", NotificationDisplayType.BALLOON, true);
    private Notification notification;

    MessageType(NotificationType type) {
        this.notification = group.createNotification(type);
    }

    public void notice(Project project, String message) {
        notification.setContent(message);
        notification.notify(project);
    }
}

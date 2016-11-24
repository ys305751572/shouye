package test;

import com.smallchill.api.common.model.Result;
import com.smallchill.api.function.modal.Message;
import com.smallchill.api.function.service.MessageService;
import com.smallchill.core.toolbox.kit.JsonKit;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息测试
 * Created by yesong on 2016/11/3 0003.
 */
public class MessageServiceTest extends BaseJunit4Test{

    @Autowired
    private MessageService messageService;

    @Test
    public void auditMsg() {
        int groupId = 9;
        int userId = 5;
//        messageService.sendMsgForUserAuditAgree(9, 5); // 申请通过
 //       messageService.sendMsgForUserAuditRefuse(9, 5, null); // 免费群，审核拒绝
        messageService.sendMsgForUserAuditRefuse(groupId, userId, "123456"); // 免费群，审核拒绝
    }

    @Test
    public void groupMsg() {
        int groupId = 9;
        int userId = 5;
//        messageService.sendMsgFromGroupToUser(groupId,userId, "", "请大家准时参加某某时间的会议"); // 组织给单个用户发送消息

        List<Integer> userIds = new ArrayList<>();
        userIds.add(5);
        userIds.add(6);
//        messageService.sendMsgFromGroupToUsers(groupId, userIds, "", "请大家准时参加某某时间的会议");
    }

    @Test
    public void sysMsg() {
        int userId = 5;

        List<Integer> userIds = new ArrayList<>();
        userIds.add(5);
        userIds.add(6);

//        messageService.sendMsgFromSysToUser(userId, "系统维护升级", "出于升级需要，手页将于11-30号停服维护");
        messageService.sendMsgFromSysToUsers(userIds,"系统维护升级", "出于升级需要，手页将于11-30号停服维护");
    }

    @Test
    public void myMsg() {
        int userId = 5;
        List<Message> list = messageService.findByUserId(userId);
        System.out.println(JsonKit.toJson(Result.success(list)));
    }
}

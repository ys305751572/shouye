package test;

import com.smallchill.api.common.exception.UserHasApprovalException;
import com.smallchill.api.common.exception.UserHasFriendException;
import com.smallchill.api.common.exception.UserInBlankException;
import com.smallchill.api.common.exception.UsernotFriendException;
import com.smallchill.web.model.UserApproval;
import com.smallchill.web.service.UserApprovalService;
import com.smallchill.web.service.impl.BothUserHasApprovalException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户审核
 * Created by yesong on 2016/10/31 0031.
 */
public class UserApprovalTest extends BaseJunit4Test{

    @Autowired
    private UserApprovalService userApprovalService;

    /**
     * 结识申请
     */
    @Test
    public void testUseroneway() {
        UserApproval ua = new UserApproval();
        ua.setFromUserId(6);
        ua.setToUserId(5);
        ua.setType(1);
        ua.setValidateInfo("申请加为好友");
        try {
            userApprovalService.toUserOneWay(ua);
        } catch (UserInBlankException e) {
            e.printStackTrace();
        } catch (UserHasApprovalException e) {
            e.printStackTrace();
        } catch (UsernotFriendException e) {
            e.printStackTrace();
        } catch (BothUserHasApprovalException e) {
            e.printStackTrace();
        } catch (UserHasFriendException e) {
            e.printStackTrace();
        }
    }

    /**
     * 审核申请
     */
    @Test
    public void testUserApproval() {
        // 同意
        UserApproval ua = new UserApproval();
        ua.setFromUserId(6);
        ua.setToUserId(5);
        ua.setStatus(1);
        userApprovalService.userApprovalAgree(ua);
    }
}

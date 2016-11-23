package test;

import com.smallchill.api.common.exception.*;
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
        ua.setFromUserId(20);
        ua.setToUserId(21);
        ua.setType(3);
        ua.setValidateInfo("申请加为好友");
        try {
            userApprovalService.toUserOneWay(ua,"21");
        } catch (UserHasApprovalException e) {
            e.printStackTrace();
        } catch (UsernotFriendException e) {
            e.printStackTrace();
        } catch (BothUserHasApprovalException e) {
            e.printStackTrace();
        } catch (UserHasFriendException e) {
            e.printStackTrace();
        } catch (UserInOthersBlankException e) {
            e.printStackTrace();
        } catch (UserInMyBlankException e) {
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

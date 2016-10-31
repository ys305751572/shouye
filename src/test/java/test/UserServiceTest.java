package test;

import com.smallchill.web.model.UserFriend;
import com.smallchill.web.service.UserFriendService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户模块
 * Created by yesong on 2016/10/31 0031.
 */
public class UserServiceTest extends BaseJunit4Test{

    @Autowired
    private UserFriendService userFriendService;

    @Test
    public void testUserFriendDel() {
        UserFriend uf = new UserFriend();
        uf.setUserId(5);
        uf.setFriendId(6);
        userFriendService.delFriend(uf);
    }
}

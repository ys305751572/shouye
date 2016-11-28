package test;

import com.smallchill.api.common.model.Result;
import com.smallchill.api.function.modal.vo.UserVo;
import com.smallchill.api.function.service.ShoupageService;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.kit.JsonKit;
import com.smallchill.web.model.UserFriend;
import com.smallchill.web.service.UserFriendService;
import com.smallchill.web.service.UserInfoService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 用户模块
 * Created by yesong on 2016/10/31 0031.
 */
public class UserServiceTest extends BaseJunit4Test{

    @Autowired
    private UserFriendService userFriendService;

    @Autowired
    private ShoupageService shoupageService;

    @Autowired
    private UserInfoService userInfoService;

    @Test
    public void testUserFriendDel() {
        UserFriend uf = new UserFriend();
        uf.setUserId(5);
        uf.setFriendId(6);
        userFriendService.delFriend(uf);
    }

    @Test
    public void testUserFriends() {
//        List<UserVo> list = shoupageService.friends(5);
//        System.out.println(JsonKit.toJson(Result.success(list)));
    }

    /**
     * 测试新结识列表
     */
    @Test
    public void testNewList() {
        Map<String,List<UserVo>> result = shoupageService.listNew(5);
        System.out.println(JsonKit.toJson(Result.success(result)));
    }

    @Test
    public void testInsterst() {
        Map<String,Object> gu = shoupageService.listIntereste(5);
        System.out.println(JsonKit.toJson(Result.success(gu,"GU")));
    }

    @Test
    public void testInstersted() {
        List<UserVo> list = shoupageService.listInterested(5);
        System.out.println(JsonKit.toJson(Result.success(list)));
    }

    /**
     * 查询我的熟人
     */
    @Test
    public void testAcquaintances() {
        List<UserVo> list = shoupageService.listAcquaintances(5, null);
        System.out.println(JsonKit.toJson(Result.success(list)));
    }

    @Test
    public void testUserList() {
        int userId = 6;
        int groupId = 9;
        List<Record> list = userInfoService.findByParmas(Record.create().set("userId", userId).set("groupId",groupId));
        System.out.println(JsonKit.toJson(Result.success(list)));
    }
}
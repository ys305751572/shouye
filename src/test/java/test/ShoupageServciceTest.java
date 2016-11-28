package test;

import com.smallchill.api.function.modal.vo.Groupvo;
import com.smallchill.api.function.modal.vo.UserVo;
import com.smallchill.api.function.service.ShoupageService;
import com.smallchill.core.toolbox.kit.JsonKit;
import com.smallchill.web.service.AugService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 *
 * Created by yesong on 2016/11/10 0010.
 */
public class ShoupageServciceTest extends BaseJunit4Test{

    @Autowired
    private ShoupageService shoupageService;

    @Autowired
    private AugService augService;

    @Test
    public void myGroups() {
        List<Groupvo> list = shoupageService.listGroup(20);
        System.out.println(JsonKit.toJson(list));
    }

    @Test
    public void myAcquaintances() {
        List<UserVo> list = shoupageService.listAcquaintances(20, null);
        System.out.println(JsonKit.toJson(list));
    }

    @Test
    public void myInterested() {
        List<UserVo> list = shoupageService.listInterested(20);
        System.out.println(JsonKit.toJson(list));
    }

    @Test
    public void myIntereste() {
        Map<String, Object> map = shoupageService.listIntereste(20);
        System.out.println(JsonKit.toJson(map));
    }

    @Test
    public void myNew() {
        Map<String,List<UserVo>> list = shoupageService.listNew(20);
        System.out.println(JsonKit.toJson(list));
    }

}

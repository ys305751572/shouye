package test;

import com.smallchill.web.model.vo.GroupVo;
import com.smallchill.web.service.GroupExtendService;
import com.smallchill.web.service.GroupService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * 组织测试类
 * Created by yesong on 2016/10/26 0026.
 */
public class GroupServiceTest extends BaseJunit4Test{


    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupExtendService groupExtendService;

    @Transactional
    @Test
    public void createGroup() {
        GroupVo vo = new GroupVo();
        vo.setName("武汉六脉神掌科技有限公司");
        vo.setPassword("123456");
        vo.setCode("ZUZHI20161026");
        vo.setLicense("350500400032802-2/3");
        vo.setArtificialPersonName("张三");
        vo.setArtificialPersonIdcard("420822198909166666");
        vo.setArtificialPersonMobile("13476107753");
        vo.setType(1);
        vo.setProvince(1);
        vo.setCity(1);
        vo.setTarget("app|开发");


        vo.setBankUserName("张三");
        vo.setBankAccout("6225 6655 6665 8888");
        vo.setBankId(1);
        vo.setBankName("中国工商银行");
        vo.setBanckProvince(1);
        vo.setBankCity(1);
        vo.setBranchName("中国工商银行光谷支行");

        // ---------------------------------
        vo.setTitle1("基本介绍");
        vo.setContent1("基本介绍内容");
        vo.setIsOpen1(1);

        vo.setTitle2("组织简介");
        vo.setContent2("组织简介内容");
        vo.setIsOpen2(1);

        vo.setTitle3("领域行业");
        vo.setContent3("领域行业内容");
        vo.setIsOpen3(1);

        vo.setTitle4("联系我们");
        vo.setContent4("联系我们内容");
        vo.setIsOpen4(1);

        groupService.saveGroup(vo);
    }

    @Test
    public void testGetCost() {
        System.out.println(groupExtendService.getCost(9));
    }
}

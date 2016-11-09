package test;

import com.smallchill.web.service.UserApprovalService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自荐-群管理审核测试
 * Created by yesong on 2016/11/9 0009.
 */
public class AugServiceTest extends BaseJunit4Test{

    @Autowired
    private UserApprovalService userApprovalService;

    /**
     * 同意
     */
    @Test
    public void agree() {
        userApprovalService.groupApprovalAgree(2);
    }

    /**
     * 拒绝
     */
    @Test
    public void refuse() {
        userApprovalService.groupApprovalRefuse(2);
    }

    /**
     * 拉黑
     */
    @Test
    public void blank() {
        userApprovalService.groupApprovalBlank(2);
    }

    /**
     * 取消啦黑
     */
    @Test
    public void ublank() {
        userApprovalService.groupApprovalUnBlank(2);
    }
}

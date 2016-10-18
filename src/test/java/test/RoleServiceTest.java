package test;

import com.smallchill.system.service.RoleService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * Created by Administrator on 2016/10/17 0017.
 */
public class RoleServiceTest extends BaseJunit4Test{

    @Autowired
    private RoleService roleService;

    @Test
    @Transactional
    public void test() {

        System.out.println("============test=============" + roleService.findAll().size());
    }
}

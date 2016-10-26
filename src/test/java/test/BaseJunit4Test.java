package test;

import org.junit.runner.RunWith;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * Created by yesong on 2016/10/17 0017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/springmvc-servlet.xml")
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class BaseJunit4Test {
}

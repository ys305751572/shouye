//package com.smallchill.test.zk;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.apache.zookeeper.CreateMode;
//import org.apache.zookeeper.KeeperException;
//import org.apache.zookeeper.ZooDefs;
//
//import java.util.List;
//
///**
// * Created by Administrator on 2016/9/20.
// */
//public class ZkSubDemo extends ZkDemo {
//    private static Log log = LogFactory.getLog(ZkSubDemo.class.getName());
//
//    /**
//     * <b>function:</b>创建持久态的znode,比支持多层创建.比如在创建/parent/child的情况下,无/parent.无法通过
//     *
//     * @param path
//     * @param data
//     * @throws InterruptedException
//     * @author cuiran
//     * @createDate 2013-01-16 15:08:38
//     */
//    public void create(String path, byte[] data) throws KeeperException, InterruptedException {
//        /**
//         * 此处采用的是CreateMode是PERSISTENT  表示The znode will not be automatically deleted upon client's disconnect.
//         * EPHEMERAL 表示The znode will be deleted upon the client's disconnect.
//         */
//        this.zooKeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//    }
//
//    /**
//     * <b>function:</b>获取节点信息
//     *
//     * @param path
//     * @throws KeeperException
//     * @throws InterruptedException
//     * @author cuiran
//     * @createDate 2013-01-16 15:17:22
//     */
//    public void getChild(String path) throws KeeperException, InterruptedException {
//        try {
//            List<String> list = this.zooKeeper.getChildren(path, false);
//            if (list.isEmpty()) {
//                log.debug(path + "中没有节点");
//            } else {
//                log.debug(path + "中存在节点");
//                for (String child : list) {
//                    log.debug("节点为：" + child);
//                }
//            }
//        } catch (KeeperException.NoNodeException e) {
//            // TODO: handle exception
//            throw e;
//
//        }
//    }
//
//    public byte[] getData(String path) throws KeeperException, InterruptedException {
//        return this.zooKeeper.getData(path, false, null);
//    }
//
//    public static void main(String[] args) {
//        try {
//            ZkSubDemo zkoperator = new ZkSubDemo();
//            zkoperator.connect("4.16.2.65");
//
//            byte[] data = new byte[]{'a', 'b', 'c', 'd'};
//
////              zkoperator.create("/root",null);
////              System.out.println(Arrays.toString(zkoperator.getData("/root")));
////
////              zkoperator.create("/root/child1",data);
////              System.out.println(Arrays.toString(zkoperator.getData("/root/child1")));
////
////              zkoperator.create("/root/child2",data);
////              System.out.println(Arrays.toString(zkoperator.getData("/root/child2")));
//
//            String zktest = "ZooKeeper的Java API测试";
//            zkoperator.create("/root/child3", zktest.getBytes());
//            log.debug("获取设置的信息：" + new String(zkoperator.getData("/root/child3")));
//
//            System.out.println("节点孩子信息:");
//            zkoperator.getChild("/root");
//
//            zkoperator.close();
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//}

package test.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yesong on 2016/10/12 0012.
 */
public class Database {

    private static final Logger LOGGER = LoggerFactory.getLogger(Database.class);

    public static final String url = "jdbc:mysql://localhost:3306/youzhu";
    public static final String username = "root";
    public static final String password = "root";
    //    public static String[]
    public static String[] filtration = {"id", "content"};
    public static String[] groupScale = {"id", "scale"};
    public static String[] groupType = {"id", "name"};
    public static String[] interests = {"id", "interest"};
    public static String[] labels = {"id", "name"};
    public static String[] years = {"id", "year"};

    public static String[] dict = {};

    public void executeDict() {
        List<String[]> list = init();
        for (String[] strings : list) {

        }
    }

    public List<String[]> init() {
        List<String[]> list = new ArrayList<>();
        list.add(filtration);
        list.add(groupScale);
        list.add(groupType);
        list.add(interests);
        list.add(labels);
        list.add(years);
        return list;
    }

    /**
     * @param table   表名
     * @param colunms 字段
     * @return 结果集合
     */
    public List<Map<String, Object>> findOldResultList(String table, String[] colunms) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        Connection connection = DBUtils.getConnetion(url, username, password);
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from " + table;
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            Map<String, Object> resultMap = null;
            while (rs.next()) {
                resultMap = new HashMap<>();
                for (String col : colunms) {
                    Object colunm = rs.getObject(col);
                    resultMap.put(col, colunm);
                }
                mapList.add(resultMap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(connection, ps, rs);
        }
        return mapList;
    }


    public void insert(String name,String newTable, List<Map<String, Object>> mapList, String[] newColumn) {

        Connection connection = DBUtils.getConnetion(url, username, password);
        PreparedStatement ps = null;
        ResultSet rs = null;
        int lastCode = 0;

        String findLastCode = "select CODE from tfw_dict order by CODE desc limit 1";
        try {
            ps = connection.prepareStatement(findLastCode);
            rs = ps.executeQuery();
            while (rs.next()) {
                lastCode = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        String insertPtype = "insert into tfw_dict (`CODE`,`NUM`,`PID`,`NAME`,`TIPS`,`VERSION`) values (?,?,?,?,?,?)";
        try {
            ps = connection.prepareStatement(insertPtype);
            ps.setInt(1, ++lastCode);
            ps.setInt(2, 0);
            ps.setInt(3, 0);
            ps.setString(4, name);
            ps.setString(5,"");
            ps.setInt(6,0);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String colunms = appendWith(",", newColumn);
        String values = list2Parmas(mapList);
        String sql = "insert into " + newTable + "(" + colunms + ")" + values;

        LOGGER.info("=======sql:" + sql);
        try {
            ps = connection.prepareStatement(sql);
            int rows = ps.executeUpdate();
            LOGGER.info("表" + newTable + "更新了" + rows + "条数据");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(connection, ps, rs);
        }
    }


    /**
     * 查询结果集转换为values
     *
     * @param list
     * @return
     */
    public String list2Parmas(List<Map<String, Object>> list) {
        StringBuffer buffer = new StringBuffer("values ");
        for (Map<String, Object> map : list) {
            buffer.append("(");
            for (Map.Entry<String, Object> colunm : map.entrySet()) {
                buffer.append(colunm.getValue());
            }
            buffer.append("),");
        }
        return buffer.toString().substring(0, buffer.length() - 1);
    }

    /**
     * @param fit
     * @param columns
     * @return
     */
    public String appendWith(String fit, String[] columns) {
        if (columns == null || columns.length == 0) return "";
        StringBuffer buffer = new StringBuffer("");
        for (String col : columns) {
            buffer.append(col).append(fit);
        }
        return buffer.substring(0, buffer.length() - 1);
    }
}

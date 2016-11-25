package test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.smallchill.api.common.model.Result;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.kit.FileKit;
import com.smallchill.core.toolbox.kit.JsonKit;
import org.junit.Test;
import sun.security.provider.Sun;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * 导入省份城市
 * Created by yesong on 2016/11/24 0024.
 */

public class ImportProvinceTest extends BaseJunit4Test{


    public static List<City> join() {
        File file = new File("E:\\project\\shouye\\json.txt");
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(file));
            StringBuffer buffer = new StringBuffer();
            String text;
            while ((text = reader.readLine()) != null) {
                buffer.append(text);
            }
            List<City> cityList = new Gson().fromJson(buffer.toString(), new TypeToken<List<City>>(){}.getType());
            return cityList;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public static void join2() {
        List<City> cityList = join();
        for (City city : cityList) {
            insert(city);
            List<City> subCityLisy = city.getSubAddress();
            if (subCityLisy != null && subCityLisy.size() > 0) {
                for (City city1 : subCityLisy) {
                    insert(city1);
                }
            }
        }
    }

    public static void insert(City city) {
        Db.init().insert("insert into tb_province_city (code,name,parent_id) values (#{code), #{name}, #{parentId}",
                Record.create().set("code", city.getId()).set("name", city.getName()).set("parentId",city.getParentId()));
    }
}

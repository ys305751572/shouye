package test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yesong on 2016/11/24 0024.
 */
public class City implements Serializable {

    private String id;
    private String name;
    private String parentId;
    private List<City> subAddress = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<City> getSubAddress() {
        return subAddress;
    }

    public void setSubAddress(List<City> subAddress) {
        this.subAddress = subAddress;
    }
}

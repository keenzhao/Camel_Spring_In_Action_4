package com.camel.test.demo.misc.jws_cxf.model;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * Created by keen.zhao on 2016/10/5.
 */
public class IntegerUserMapAdapter extends XmlAdapter<IntegerUserMap, Map<Integer, User>> {
    @Override
    public Map<Integer, User> unmarshal(IntegerUserMap v) throws Exception {
        Map<Integer, User> map = new LinkedHashMap<Integer, User>();
        for (IntegerUserMap.IntegerUserEntry e : v.getEntries()) {
            map.put(e.getId(), e.getUser());
        }
        return map;
    }

    @Override
    public IntegerUserMap marshal(Map<Integer, User> v) throws Exception {
        IntegerUserMap map = new IntegerUserMap();
        for (Map.Entry<Integer, User> e : v.entrySet()) {
            IntegerUserMap.IntegerUserEntry iue = new IntegerUserMap.IntegerUserEntry();
            iue.setUser(e.getValue());
            iue.setId(e.getKey());
            map.getEntries().add(iue);
        }
        return map;
    }
}

package com.camel.test.demo.misc.jws_cxf.model;

import javax.xml.bind.annotation.XmlType;

/**
 * Created by keen.zhao on 2016/10/5.
 */

@XmlType(name = "User")
public class UserImpl implements User {
    String name;

    public UserImpl() {
    }

    public UserImpl(String s) {
        name = s;
    }

    public String getName() {
        return name;
    }

    public void setName(String s) {
        name = s;
    }
}

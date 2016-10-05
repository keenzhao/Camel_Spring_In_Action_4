package com.camel.test.demo.misc.jws_cxf.model;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Created by keen.zhao on 2016/10/5.
 */

@XmlJavaTypeAdapter(UserAdapter.class)
public interface User {
    String getName();
}

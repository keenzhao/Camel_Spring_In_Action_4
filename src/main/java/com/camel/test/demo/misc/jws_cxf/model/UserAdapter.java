package com.camel.test.demo.misc.jws_cxf.model;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by keen.zhao on 2016/10/5.
 */
public class UserAdapter extends XmlAdapter<UserImpl, User> {


    @Override
    public User unmarshal(UserImpl v) throws Exception {
        return v;
    }

    @Override
    public UserImpl marshal(User v) throws Exception {
        if (v instanceof UserImpl) {
            return (UserImpl) v;
        }
        return new UserImpl(v.getName());
    }
}

package com.easemob.im.server.api.contact;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.contact.user.ContactUser;

public class ContactApi {

    private Context context;

    public ContactUser user(String username) {
        return new ContactUser(this.context, username);
    }
}

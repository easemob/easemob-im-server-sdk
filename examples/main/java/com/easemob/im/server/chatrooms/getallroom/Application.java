package com.easemob.im.server.chatrooms.getallroom;

import com.easemob.im.server.EMClient;
import com.easemob.im.server.EMClientException;
import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.chatrooms.exception.ChatRoomsException;
import com.easemob.im.server.model.ChatRoom;

public class Application {
    public static void main(String[] args) {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));

        try {
            ChatRoom result = EMClient.getInstance().chatRooms().getAppAllChatRoom();
            System.out.println("result = " + result);
        } catch (EMClientException | ChatRoomsException e) {
            System.out.println("exception = " + e.getMessage());
        }
    }
}

package com.easemob.im.server.chatrooms.getroomdetails;

import com.easemob.im.server.EMClient;
import com.easemob.im.server.EMClientException;
import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.chatrooms.exception.ChatRoomsException;
import com.easemob.im.server.model.ChatRoom;

import java.util.HashSet;
import java.util.Set;

public class Application {
    public static void main(String[] args) {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));

        try {
            // 获取单个聊天室详情示例
            ChatRoom result = EMClient.getInstance().chatRooms().getChatRoomDetails("138135017160705");

            // 获取多个聊天室详情示例
//            Set<String> roomIds = new HashSet<>();
//            roomIds.add("138135017160705");
//            ChatRoom result = EMClient.getInstance().chatRooms().getChatRoomDetails(roomIds);

            System.out.println("result = " + result);
        } catch (EMClientException | ChatRoomsException e) {
            System.out.println("exception = " + e.getMessage());
        }
    }
}

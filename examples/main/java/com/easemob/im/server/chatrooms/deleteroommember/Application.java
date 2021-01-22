package com.easemob.im.server.chatrooms.deleteroommember;

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
            // 删除单个聊天室成员示例
            ChatRoom result = EMClient.getInstance().chatRooms().deleteChatRoomMember("138135017160705", "testuser0003");

            // 删除多个聊天室成员示例
//            Set<String> members = new HashSet<>();
//            members.add("testuser0003");
//            members.add("testuser0005");
//            ChatRoom result = EMClient.getInstance().chatRooms().batchDeleteChatRoomMember("138135017160705", members);

            System.out.println("result = " + result);
        } catch (EMClientException | ChatRoomsException e) {
            System.out.println("exception = " + e.getMessage());
        }
    }
}

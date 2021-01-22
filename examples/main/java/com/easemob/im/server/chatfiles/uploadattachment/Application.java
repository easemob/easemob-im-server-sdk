package com.easemob.im.server.chatfiles.uploadattachment;

import com.easemob.im.server.EMClient;
import com.easemob.im.server.EMClientException;
import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.chatfiles.exception.ChatFilesException;
import com.easemob.im.server.model.ChatFile;

import java.io.File;

public class Application {
    public static void main(String[] args) {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        try {
            // 通过附件文件上传
            File file = new File("/Users/easemob-dn0164/Desktop/9090.jpg");
            ChatFile result = EMClient.getInstance().chatFiles().uploadAttachment(file);

            // 通过附件本地路径上传
//            ChatFile result = EMClient.getInstance().chatFiles().uploadAttachment("/Users/easemob-dn0164/Desktop/9090.jpg");

            System.out.println("result = = " + result);
        } catch (EMClientException | ChatFilesException e) {
            System.out.println("exception = " + e.getMessage());
        }
    }
}

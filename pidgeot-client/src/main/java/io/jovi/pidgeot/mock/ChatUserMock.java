package io.jovi.pidgeot.mock;

import com.google.common.collect.Maps;
import io.jovi.pidgeot.common.codec.bean.ChatUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2019
 * </p>
 *
 * @author Jovi
 * @version 1.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ChatUserMock {
    /**
     * 聊天用户
     */
    private static Map<String, ChatUser> CHAT_USER_MAP =Maps.newHashMap();

    static {
        ChatUser chatUserA = new ChatUser();
        chatUserA.setUid("10000");
        chatUserA.setAccount("pidgeot");
        CHAT_USER_MAP.put("10000",chatUserA);
    }

    /**
     * 根据用户ID查询
     * @param uid
     * @return
     */
    public static ChatUser getByUid(String uid){
        ChatUser chatUser = CHAT_USER_MAP.get(uid);
        return chatUser;
    }
}

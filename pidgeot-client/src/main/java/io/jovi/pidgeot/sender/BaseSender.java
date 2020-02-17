package io.jovi.pidgeot.sender;

/**
 * <p>
 * Title:
 * </p >
 * <p>
 * Description:
 * </p >
 * <p>
 * Copyright: Copyright (c) 2019
 * All rights reserved. 2020-02-12.
 * </p >
 *
 * @author MaoJiaWei
 * @version 1.0
 */
public interface BaseSender {
    /**
     * 发送消息
     * @param uid 用户ID
     * @param content
     */
    void sendChatMsg(String uid, String content);
}

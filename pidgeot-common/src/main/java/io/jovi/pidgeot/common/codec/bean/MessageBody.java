package io.jovi.pidgeot.common.codec.bean;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.IOException;

/**
 * <p>
 * Title: 消息体
 * </p >
 * <p>
 * Description: 互相发送的消息体
 * </p >
 * <p>
 * Copyright: Copyright (c) 2019
 * All rights reserved. 2020-02-12.
 * </p >
 *
 * @author MaoJiaWei
 * @version 1.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class MessageBody {
    /**
     * 头部
     */
    private MessageHeader header;
    /**
     * 内容
     */
    private String content;
    /**
     * 消息类型
     */
    private Integer type;

    public byte[] toByteArray() {
        return JSON.toJSONString(this).getBytes();
    }


}

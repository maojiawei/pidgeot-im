package io.jovi.pidgeot.common.codec.bean;

import lombok.Getter;

/**
 * <p>
 * Title: 消息类型
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
@Getter
public enum  MessageTypeEnum {
    /**
     * 文字
     */
    TEXT(0),
    /**
     * 图片
     */
    IMAGE(1),
    /**
     * 小视频
     */
    VIDEO(2);
    /**
     * 消息类型
     */
    private Integer type;

    /**
     * 消息类型
     * @param type
     */
    MessageTypeEnum(Integer type){
        this.type = type;
    }
}

package io.jovi.pidgeot.common.codec.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {
    /**
     * 用户编号
     */
    private String uid;
    /**
     * 设备ID
     */
    private String deviceId;
    /**
     * 用户token
     */
    private String token;
    /**
     * 昵称
     */
    private String nickName = "nickName";
    /**
     * SESSIONID
     */
    private String sessionId;
    /**
     * 平台
     */
    private PLATTYPE platform = PLATTYPE.WINDOWS;

    // windows,mac,android, ios, web , other
    public enum PLATTYPE {
        WINDOWS, MAC, ANDROID, IOS, WEB, OTHER;
    }
}

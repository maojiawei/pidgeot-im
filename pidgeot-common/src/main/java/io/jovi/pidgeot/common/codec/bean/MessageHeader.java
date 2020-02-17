package io.jovi.pidgeot.common.codec.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
public class MessageHeader {
    /**
     * 用户
     */
    private ChatUser user;
    /**
     * 魔数
     */
    private Short magic;
    /**
     * 群ID
     */
    private Long groupId;
}

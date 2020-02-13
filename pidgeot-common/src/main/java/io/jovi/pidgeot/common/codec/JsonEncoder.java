package io.jovi.pidgeot.common.codec;

import io.jovi.pidgeot.common.codec.bean.MessageBody;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

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
@Slf4j
public class JsonEncoder extends MessageToByteEncoder<MessageBody> {
    @Override
    protected void encode(ChannelHandlerContext ctx, MessageBody msg, ByteBuf out) throws Exception {
        out.writeShort(JsonInstant.MAGIC_CODE);
        out.writeShort(JsonInstant.VERSION_CODE);

        byte[] bytes = msg.toByteArray();// 将对象转换为byte

        // 加密消息体
        /*ThreeDES des = channel.channel().attr(Constants.ENCRYPT).get();
        byte[] encryptByte = des.encrypt(bytes);*/
        // 读取消息的长度
        int length = bytes.length;

        // 先将消息长度写入，也就是消息头
        out.writeInt(length);
        // 消息体中包含我们要发送的数据
        out.writeBytes(msg.toByteArray());
    }
}

package io.jovi.pidgeot.handler;

import com.alibaba.fastjson.JSON;
import io.jovi.pidgeot.common.codec.bean.ChatUser;
import io.jovi.pidgeot.common.codec.bean.MessageBody;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
@ChannelHandler.Sharable
@Service("LoginRequestHandler")
public class LoginRequestHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        int len = in.readableBytes();
        byte[] arr = new byte[len];
        in.getBytes(0, arr);
        String message = new String(arr, "UTF-8");
        log.info("server received: " + message);

        MessageBody messageBody = JSON.parseObject(message,MessageBody.class);
        ChatUser user = messageBody.getHeader().getUser();
        if(!"pidgeot".equalsIgnoreCase(user.getAccount())){

        }
    }
}

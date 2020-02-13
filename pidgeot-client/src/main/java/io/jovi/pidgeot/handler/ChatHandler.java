package io.jovi.pidgeot.handler;

import io.jovi.pidgeot.common.codec.bean.MessageBody;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
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
@ChannelHandler.Sharable
@Service("chatHandler")
public class ChatHandler extends ChannelInboundHandlerAdapter {
    /**
     * 业务逻辑处理
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //判断消息实例
        if (null == msg || !(msg instanceof MessageBody)) {
            super.channelRead(ctx, msg);
            return;
        }

        //转换实体
        MessageBody message = (MessageBody) msg;
        String content = message.getContent();

        System.out.println(" 收到消息：" +  content);
    }
}

package io.jovi.pidgeot.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;

/**
 * <p>
 * Title: 用于读取消息信息
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
@Slf4j
@ChannelHandler.Sharable
@Service("chatReceiveHandler")
public class ChatReceiveHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        log.info("msg type: " + (in.hasArray()?"堆内存":"直接内存"));

        int len = in.readableBytes();
        byte[] arr = new byte[len];
        in.getBytes(0, arr);
        log.info("server received: " + new String(arr, "UTF-8"));

        //写回数据，异步任务
        log.info("写回前，msg.refCnt:" + ((ByteBuf) msg).refCnt());

        // 发送消息
        byte[] bytes = "消息已收到!".getBytes(Charset.forName("utf-8"));
        //发送ByteBuf
        ByteBuf buffer = ctx.alloc().buffer();
        buffer.writeBytes(bytes);
        ChannelFuture f = ctx.writeAndFlush(buffer);
        f.addListener((ChannelFuture futureListener) -> {
            log.info("写回后，msg.refCnt:" + ((ByteBuf) msg).refCnt());
        });
    }
}

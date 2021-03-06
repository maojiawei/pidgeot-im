package io.jovi.pidgeot.sender;

import io.jovi.pidgeot.common.codec.JsonInstant;
import io.jovi.pidgeot.common.codec.bean.ChatUser;
import io.jovi.pidgeot.common.codec.bean.MessageBody;
import io.jovi.pidgeot.common.codec.bean.MessageHeader;
import io.jovi.pidgeot.common.codec.bean.MessageTypeEnum;
import io.jovi.pidgeot.handler.ChatCallbackHandler;
import io.jovi.pidgeot.mock.ChatUserMock;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;

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
@Service
public class ChatSender implements BaseSender{
    /**
     * 发送消息
     * @param uid
     * @param content
     */
    @Override
    public void sendChatMsg(String uid, String content) {
        log.info("发送消息 startConnectServer");
        ChatUser chatUser = ChatUserMock.getByUid(uid);
        MessageHeader header = new MessageHeader();
        header.setUser(chatUser);
        header.setMagic(JsonInstant.MAGIC_CODE);
        MessageBody chatMsg = new MessageBody();
        chatMsg.setContent(content);
        chatMsg.setHeader(header);
        chatMsg.setType(MessageTypeEnum.TEXT.type());
        this.sendMsg(chatMsg);
    }

    /**
     * 发送消息
     * @param message
     */
    public void sendMsg(MessageBody message){
        //事件池
        EventLoopGroup group = new NioEventLoopGroup();
        // 启动器
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel)  {
                        channel.pipeline().addLast(new ChatCallbackHandler());
                    }
                });
        ChannelFuture future = null;
        try {
            future = bootstrap.connect("127.0.0.1", 6002);
            future.addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    // 回调
                    if (future.isSuccess()) {
                        log.info("服务端连接成功");
                    } else {
                        log.info("服务端连接失败");
                    }
                }
            });
            // 阻塞,直到连接完成
            future.sync();
            Channel channel = future.channel();

            // 发送消息
            byte[] bytes = message.toByteArray();
            //发送ByteBuf
            ByteBuf buffer = channel.alloc().buffer();
            buffer.writeBytes(bytes);
            channel.writeAndFlush(buffer);

            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }

    }
}

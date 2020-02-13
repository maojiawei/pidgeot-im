package io.jovi.pidgeot.sender;

import io.jovi.pidgeot.common.codec.bean.MessageBody;
import io.jovi.pidgeot.common.codec.bean.MessageTypeEnum;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
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
@Service
public class ChatSender implements BaseSender{

    @Override
    public void sendChatMsg(String touid, String content) {
        log.info("发送消息 startConnectServer");
        MessageBody chatMsg = new MessageBody();
        chatMsg.setContent(content);
        chatMsg.setType(MessageTypeEnum.TEXT.type());


        this.sendMsg(content);
    }

    public void sendMsg(String message){

        Channel channel = new NioSocketChannel();
        ChannelFuture f = channel.writeAndFlush(message);
        f.addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future)
                    throws Exception {
                // 回调
                if (future.isSuccess()) {
                    log.info("回调成功，消息：{}",message);
                } else {
                    log.info("回调失败，消息：{}",message);

                }
            }

        });
    }
}

package io.jovi.pidgeot.client;

import io.jovi.pidgeot.common.codec.JsonDecoder;
import io.jovi.pidgeot.common.codec.JsonEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
@Service("NettyClient")
public class PidgeotClient {
    /**
     * 构造器
     */
    public PidgeotClient(){
        bootstrap = new Bootstrap();
    }

    /**
     * Netty应用入口
     */
    private Bootstrap bootstrap;

    private GenericFutureListener<ChannelFuture> connectedListener;

    /**
     * 重连
     */
    public void connect(String host,int port) {
        try {
            // 包工头 负责服务器通道新连接的IO事件的监听
            EventLoopGroup group = new NioEventLoopGroup();

            bootstrap.group(group);
            bootstrap.channel(NioSocketChannel.class);

            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);

            bootstrap.remoteAddress(host, port);

            // 设置通道初始化
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                          public void initChannel(SocketChannel ch) {
                              ch.pipeline().addLast("decoder", new JsonDecoder());
                              ch.pipeline().addLast("encoder", new JsonEncoder());
                          }
                      }
            );
            log.info("客户端开始连接 [疯狂创客圈IM]");

            ChannelFuture f = bootstrap.connect();
            f.addListener(connectedListener);

            // 阻塞
            // f.channel().closeFuture().sync();

        } catch (Exception e) {
            log.info("客户端连接失败!,失败消息：{}" , e.getMessage());
        }
    }
}

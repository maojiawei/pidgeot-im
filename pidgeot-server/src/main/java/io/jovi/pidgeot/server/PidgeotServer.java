package io.jovi.pidgeot.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

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
@Component
public class PidgeotServer implements CommandLineRunner {
    /**
     * 本地文件路径
     */
    @Value("${pidgeot.im.port}")
    private int port;

    @Override
    public void run(String... args) throws Exception {
        log.info("正在启动netty服务,端口:{}",port);
        // 创建反应器线程组
        // 包工头 负责服务器通道新连接的IO事件的监听
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        // 工人 主要负责传输通道的IO事件处理
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        // 1. 设置反应器线程组
        bootstrap.group(boosGroup, workerGroup);
        // 2. 设置nio类型通道
        bootstrap.channel(NioServerSocketChannel.class);
        //3 设置监听端口
        bootstrap.localAddress(new InetSocketAddress(port));
        //4 设置通道选项
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.option(ChannelOption.ALLOCATOR,
                PooledByteBufAllocator.DEFAULT);
        bootstrap
                .childHandler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel)  {
                        channel.pipeline().addLast(new ObjectEncoder());
                        channel.pipeline().addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.weakCachingConcurrentResolver(null)));
                    }
                });
        ChannelFuture future = bootstrap.bind().sync();
        future.channel().closeFuture().sync();
        boosGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    /**
     * 启动服务
     * @param port
     */
    public static void run(int port){

    }
}

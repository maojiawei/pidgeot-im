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
        try{
            // 1. 设置反应器线程组
            bootstrap.group(boosGroup, workerGroup);
            // 2. 设置nio类型通道
            bootstrap.channel(NioServerSocketChannel.class);
            // 3. 设置监听端口
            bootstrap.localAddress(new InetSocketAddress(port));
            // 4. 设置通道选项
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);

            // 5. 装配流水线
            bootstrap.childHandler(new ChannelInitializer<Channel>() {
                @Override
                protected void initChannel(Channel channel)  {
                    channel.pipeline().addLast(new ObjectEncoder());
                    channel.pipeline().addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.weakCachingConcurrentResolver(null)));
                }
            });
            // 6. 开始绑定server
            // 通过调用sync同步方法阻塞直到绑定成功
            ChannelFuture future = bootstrap.bind().sync();
            // 7. 监听通道关闭事件
            // 应用程序会一直等待，直到channel关闭
            future.channel().closeFuture().sync();
        }catch (Exception e){
            log.error(e.getMessage());
        }finally {
            // 8. 优雅关闭EventLoopGroup，
            // 释放掉所有资源包括创建的线程
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }

    /**
     * 启动服务
     * @param port
     */
    public static void run(int port){

    }
}

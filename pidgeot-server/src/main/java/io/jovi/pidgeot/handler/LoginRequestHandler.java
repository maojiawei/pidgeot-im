package io.jovi.pidgeot.handler;

import io.jovi.pidgeot.common.codec.bean.MessageBody;
import io.jovi.pidgeot.concurrent.CallbackTask;
import io.jovi.pidgeot.concurrent.CallbackTaskScheduler;
import io.jovi.pidgeot.server.ServerSession;
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
        if (null == msg || !(msg instanceof MessageBody)) {
            super.channelRead(ctx, msg);
            return;
        }
        MessageBody message = (MessageBody) msg;
        //取得请求类型
//        ProtoMsg.HeadType headType = pkg.getType();
//        if (!headType.equals(loginProcesser.type())) {
//            super.channelRead(ctx, msg);
//            return;
//        }

        ServerSession session = new ServerSession(ctx.channel());
        //异步任务，处理登录的逻辑
        CallbackTaskScheduler.add(new CallbackTask<Boolean>() {
            @Override
            public Boolean execute() throws Exception {
//                boolean r = loginProcesser.action(session, pkg);
                return true;
            }

            //异步任务返回
            @Override
            public void onBack(Boolean r) {
                if (r) {
                    ctx.pipeline().remove(LoginRequestHandler.this);
                    log.info("登录成功:" + session.getUser());

                } else {
                    ServerSession.closeSession(ctx);
                    log.info("登录失败:" + session.getUser());

                }

            }
            //异步任务异常
            @Override
            public void onException(Throwable t) {
                ServerSession.closeSession(ctx);
                log.info("登录失败:" + session.getUser());

            }
        });
    }
}

package io.jovi.pidgeot;

import io.jovi.pidgeot.sender.BaseSender;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { PidgeotClientApplication.class })
public class ChatSenderTest {
    @Autowired
    private BaseSender baseSender;
    @Test
    public void write(){
        String message = "Hello Netty";
        baseSender.sendChatMsg("aaa",message);
    }
}

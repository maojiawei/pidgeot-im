package io.jovi.pidgeot.server;

import io.jovi.pidgeot.common.codec.bean.User;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * <p>
 * Title: 登录的用户Map
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
public class SessionMap {
    private SessionMap() {
    }

    private static SessionMap singleInstance = new SessionMap();

    //会话集合
    private ConcurrentHashMap<String, ServerSession> map = new ConcurrentHashMap<String, ServerSession>();

    public static SessionMap instance() {
        return singleInstance;
    }

    /**
     * 增加session对象
     */
    public void addSession(
            String sessionId, ServerSession s) {
        map.put(sessionId, s);
        log.info("用户登录:id= " + s.getUser().getUid()
                + "   在线总数: " + map.size());

    }

    /**
     * 获取session对象
     */
    public ServerSession getSession(String sessionId) {
        if (map.containsKey(sessionId)) {
            return map.get(sessionId);
        } else {
            return null;
        }
    }

    /**
     * 根据用户id，获取session对象
     */
    public List<ServerSession> getSessionsBy(String userId) {

        List<ServerSession> list = map.values()
                .stream()
                .filter(s -> s.getUser().getUid().equals(userId))
                .collect(Collectors.toList());
        return list;
    }

    /**
     * 删除session
     */
    public void removeSession(String sessionId) {
        if (!map.containsKey(sessionId)) {
            return;
        }
        ServerSession s = map.get(sessionId);
        map.remove(sessionId);
        System.out.println("用户下线:id= " + s.getUser().getUid() + "   在线总数: " + map.size());
    }


    public boolean hasLogin(User user) {
        Iterator<Map.Entry<String, ServerSession>> it =
                map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, ServerSession> next = it.next();
            User u = next.getValue().getUser();
            if (u.getUid().equals(user.getUid())
                    && u.getPlatform().equals(user.getPlatform())) {
                return true;
            }
        }

        return false;
    }
}

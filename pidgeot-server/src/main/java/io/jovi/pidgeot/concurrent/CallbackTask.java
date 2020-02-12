package io.jovi.pidgeot.concurrent;

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
public interface CallbackTask<T> {
    /**
     * 执行
     * @return
     * @throws Exception
     */
    T execute() throws Exception;

    /**
     * 回调
     * @param t
     */
    void onBack(T t);

    /**
     * 遇到异常
     * @param t
     */
    void onException(Throwable t);
}

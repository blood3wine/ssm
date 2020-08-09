package com.ssm.service;

public interface RedisRedPacketService {

    /**
     * 保存抢红包列表
     *
     * @param redPacketId 红包id
     * @param unitAmount  红包金额
     */
    void saveUserRedPacketByRedis(Long redPacketId, Double unitAmount);
}

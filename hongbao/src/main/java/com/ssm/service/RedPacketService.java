package com.ssm.service;

import com.ssm.domain.RedPacket;

public interface RedPacketService {

    /**
     * 获取红包
     *
     * @param id 红包id
     * @return 红包信息
     */
    RedPacket getRedPacket(Long id);


    /**
     * 扣减红包
     *
     * @param id 红包id
     * @return 影响条数
     */
    int decreaseRedPacket(Long id);

    /**
     * 获取红包
     * <p>
     * 悲观锁
     * 使用for update 语句加锁
     *
     * @param id 红包id
     * @return 红包具体信息
     */
    RedPacket getRedPacketForUpdate(Long id);

    /**
     * 扣减红包
     * <p>
     * 乐观锁
     * 使用CAS原理
     *
     * @param id      红包id
     * @param version 版本号
     * @return 更新记录条数
     */
    int decreaseRedPacketForVersion(Long id, Integer version);
}

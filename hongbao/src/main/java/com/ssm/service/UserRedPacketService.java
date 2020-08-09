package com.ssm.service;

public interface UserRedPacketService {

    /**
     * 保存抢红包信息
     * @param redPacketId 红包id
     * @param userId      用户id
     * @return 抢红包信息
     */
    int grapRedPacket(Long redPacketId, Long userId);

    /**
     * 保存抢红包信息 --悲观锁
     *
     * @param redPacketId 红包id
     * @param userId      用户id
     * @return 抢红包信息
     */
    int grapRedPacketByLock(Long redPacketId, Long userId);

    /**
     * 保存抢红包信息 --乐观锁
     *
     * @param redPacketId 红包id
     * @param userId      用户id
     * @return 抢红包信息
     */
    int grapRedPacketByVersion(Long redPacketId, Long userId);

    /**
     * 保存抢红包信息 --乐观锁(时间戳重入)
     *
     * @param redPacketId 红包id
     * @param userId      用户id
     * @return 抢红包信息
     */
    int grapRedPacketByVersion1(Long redPacketId, Long userId);

    /**
     * 保存抢红包信息 --乐观锁(计数重入)
     *
     * @param redPacketId 红包id
     * @param userId      用户id
     * @return 抢红包信息
     */
    int grapRedPacketByVersion2(Long redPacketId, Long userId);

//    Long grapRedPacketByRedis(Long redPacketId, Long userId);

    /**
     * 保存抢红包信息  --Redis
     *
     * @param redPacketId --红包编号
     * @param userId      -- 用户编号
     * @return 0-没有库存，失败
     * 1--成功，且不是最后一个红包
     * 2--成功，且是最后一个红包
     */
    Long grapRedPacketByRedis(Long redPacketId, Long userId);
}

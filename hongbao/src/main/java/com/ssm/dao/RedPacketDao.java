package com.ssm.dao;

import com.ssm.domain.RedPacket;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RedPacketDao {


    /**
     * 获取红包信息
     *
     * @param id 红包id
     * @return 红包具体信息
     */
    RedPacket getRedPacket(@Param("id") Long id);


    /**
     * 扣减红包数量
     *
     * @param id 红包id
     * @return 更新记录条数
     */
    int decreaseRedPacket(Long id);

    /**
     * 悲观锁
     * 使用for update 语句加锁
     *
     * @param id 红包id
     * @return 红包具体信息
     */
    RedPacket getRedPacketForUpdate(@Param("id") Long id);

    /**
     * 乐观锁
     * 使用CAS原理
     *
     * @param id      红包id
     * @param version 版本号
     * @return 更新记录条数
     */
    int decreaseRedPacketForVersion(@Param("id") Long id, @Param("version") Integer version);
}

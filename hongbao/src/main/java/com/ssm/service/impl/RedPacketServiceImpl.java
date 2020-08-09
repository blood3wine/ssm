package com.ssm.service.impl;

import com.ssm.dao.RedPacketDao;
import com.ssm.domain.RedPacket;
import com.ssm.service.RedPacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class RedPacketServiceImpl implements RedPacketService {


    private final RedPacketDao redPacketDao;

    @Autowired
    public RedPacketServiceImpl(RedPacketDao redPacketDao) {
        this.redPacketDao = redPacketDao;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class)
    public RedPacket getRedPacket(Long id) {
        return redPacketDao.getRedPacket(id);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class)
    public int decreaseRedPacket(Long id) {
        return redPacketDao.decreaseRedPacket(id);
    }

    @Override
    public RedPacket getRedPacketForUpdate(Long id) {
        return redPacketDao.getRedPacketForUpdate(id);
    }

    @Override
    public int decreaseRedPacketForVersion(Long id, Integer version) {
        return redPacketDao.decreaseRedPacketForVersion(id, version);
    }
}

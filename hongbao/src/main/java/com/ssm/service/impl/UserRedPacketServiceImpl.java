package com.ssm.service.impl;

import com.ssm.dao.RedPacketDao;
import com.ssm.dao.UserRedPacketDao;
import com.ssm.domain.RedPacket;
import com.ssm.domain.UserRedPacket;
import com.ssm.service.RedisRedPacketService;
import com.ssm.service.UserRedPacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

@Service
public class UserRedPacketServiceImpl implements UserRedPacketService {

    private final RedPacketDao redPacketDao;

    private final UserRedPacketDao userRedPacketDao;

    private final RedisTemplate redisTemplate;

    private final RedisRedPacketService redisRedPacketService;

    /**
     * 抢红包失败
     */
    private static final int FAILED = 0;

    /**
     * 在缓存LUA脚本后，使用该变量保存Redis返回的32位的SHA1编码，使用它去执行缓存的LUA脚本[加入这句话]
     */
    private String sha1 = null;


    @Autowired
    public UserRedPacketServiceImpl(RedPacketDao redPacketDao, UserRedPacketDao userRedPacketDao, RedisTemplate redisTemplate, RedisRedPacketService redisRedPacketService) {
        this.redPacketDao = redPacketDao;
        this.userRedPacketDao = userRedPacketDao;
        this.redisTemplate = redisTemplate;
        this.redisRedPacketService = redisRedPacketService;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class)
    public int grapRedPacket(Long redPacketId, Long userId) {

        //获取红包信息
        RedPacket redPacket = redPacketDao.getRedPacket(redPacketId);

        //当前小红包库存大于0
        if (redPacket != null && redPacket.getStock() > 0) {

            //扣减库存
            redPacketDao.decreaseRedPacket(redPacketId);

            //生成抢红包信息
            UserRedPacket userRedPacket = new UserRedPacket();
            userRedPacket.setRedPacketId(redPacketId);
            userRedPacket.setUserId(userId);
            userRedPacket.setAmount(redPacket.getAmount());
            userRedPacket.setNote("抢红包 : " + redPacketId);


            //插入抢红包信息
            return userRedPacketDao.grapRedPacket(userRedPacket);
        }

        return FAILED;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class)
    public int grapRedPacketByLock(Long redPacketId, Long userId) {
        //获取红包信息 by 加锁
        RedPacket redPacket = redPacketDao.getRedPacketForUpdate(redPacketId);

        if (redPacket != null && redPacket.getStock() > 0) {

            //扣减库存
            redPacketDao.decreaseRedPacket(redPacketId);

            //生成抢红包信息
            UserRedPacket userRedPacket = new UserRedPacket();
            userRedPacket.setRedPacketId(redPacketId);
            userRedPacket.setUserId(userId);
            userRedPacket.setAmount(redPacket.getAmount());
            userRedPacket.setNote("抢红包 : " + redPacketId);

            //插入抢红包信息
            return userRedPacketDao.grapRedPacket(userRedPacket);
        }

        return FAILED;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class)
    public int grapRedPacketByVersion(Long redPacketId, Long userId) {

        RedPacket redPacket = redPacketDao.getRedPacket(redPacketId);

        if (redPacket != null && redPacket.getStock() > 0) {

            //再次传入线程保存的 version 旧值给 SQL 判断，是否有其他线程修改过数据
            int update = redPacketDao.decreaseRedPacketForVersion(redPacketId, redPacket.getVersion());

            //如果没有数据更新
            if (update == 0) {
                return FAILED;
            }

            //生成抢红包信息
            UserRedPacket userRedPacket = new UserRedPacket();
            userRedPacket.setRedPacketId(redPacketId);
            userRedPacket.setUserId(userId);
            userRedPacket.setAmount(redPacket.getAmount());
            userRedPacket.setNote("抢红包 : " + redPacketId);

            //插入抢红包信息
            return userRedPacketDao.grapRedPacket(userRedPacket);
        }

        return FAILED;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class)
    public int grapRedPacketByVersion1(Long redPacketId, Long userId) {

        //开始时间戳
        long start = System.currentTimeMillis();
        while (true) {

            // 获取循环中当前时间
            long end = System.currentTimeMillis();

            //1000ms内可重入
            if (end - start > 1000) {
                return FAILED;
            }

            RedPacket redPacket = redPacketDao.getRedPacket(redPacketId);

            if (redPacket != null && redPacket.getStock() > 0) {

                //再次传入线程保存的 version 旧值给 SQL 判断，是否有其他线程修改过数据
                int update = redPacketDao.decreaseRedPacketForVersion(redPacketId, redPacket.getVersion());

                //如果没有数据更新
                if (update == 0) {
                    return FAILED;
                }

                //生成抢红包信息
                UserRedPacket userRedPacket = new UserRedPacket();
                userRedPacket.setRedPacketId(redPacketId);
                userRedPacket.setUserId(userId);
                userRedPacket.setAmount(redPacket.getAmount());
                userRedPacket.setNote("抢红包 : " + redPacketId);

                //插入抢红包信息
                return userRedPacketDao.grapRedPacket(userRedPacket);
            } else {
                return FAILED;
            }
        }
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class)
    public int grapRedPacketByVersion2(Long redPacketId, Long userId) {

        //可重入次数
        int count = 10;

        for (int i = 0; i < count; i++) {
            RedPacket redPacket = redPacketDao.getRedPacket(redPacketId);

            if (redPacket != null && redPacket.getStock() > 0) {

                //再次传入线程保存的 version 旧值给 SQL 判断，是否有其他线程修改过数据
                int update = redPacketDao.decreaseRedPacketForVersion(redPacketId, redPacket.getVersion());

                //如果没有数据更新
                if (update == 0) {
                    return FAILED;
                }

                //生成抢红包信息
                UserRedPacket userRedPacket = new UserRedPacket();
                userRedPacket.setRedPacketId(redPacketId);
                userRedPacket.setUserId(userId);
                userRedPacket.setAmount(redPacket.getAmount());
                userRedPacket.setNote("抢红包 : " + redPacketId);

                //插入抢红包信息
                return userRedPacketDao.grapRedPacket(userRedPacket);
            } else {
                return FAILED;
            }
        }

        return FAILED;
    }

    /**
     * 获取 Lua脚本
     *
     * @return Lua脚本
     */
    private String getScript() {
        // Lua脚本
        String script = "local listKey = 'red_packet_list_'..KEYS[1] \n"
                + "local redPacket = 'red_packet_'..KEYS[1] \n"
                + "local stock = tonumber(redis.call('hget', redPacket, 'stock')) \n"
                + "if stock <= 0 then return 0 end \n"
                + "stock = stock -1 \n"
                + "redis.call('hset', redPacket, 'stock', tostring(stock)) \n"
                + "redis.call('rpush', listKey, ARGV[1]) \n"
                + "if stock == 0 then return 2 end \n"
                + "return 1 \n";

        return script;
    }


    @Override
    public Long grapRedPacketByRedis(Long redPacketId, Long userId) {
        // 当前抢红包用户和日期信息
        String args = userId + "-" + System.currentTimeMillis();
        Long result = null;
        // 获取底层Redis操作对象
        Jedis jedis = (Jedis) redisTemplate.getConnectionFactory().getConnection().getNativeConnection();
        try {

            // 如果脚本没有加载过，那么进行加载，这样就会返回一个sha1编码
            if (sha1 == null) {
                sha1 = jedis.scriptLoad(getScript());
            }
            // 执行脚本，返回结果
            Object res = jedis.evalsha(sha1, 1, redPacketId + "", args);
            result = Long.valueOf(String.valueOf(res));
            // 返回2时为最后一个红包，此时将抢红包信息通过异步保存到数据库中
            if (result == 2) {
                // 获取单个小红包金额
                String unitAmountStr = jedis.hget("red_packet_" + redPacketId, "unit_amount");
                // 触发保存数据库操作
                Double unitAmount = Double.parseDouble(unitAmountStr);
                System.err.println("thread_name = " + Thread.currentThread().getName());
                redisRedPacketService.saveUserRedPacketByRedis(redPacketId, unitAmount);
            }
        } finally {
            // 确保jedis顺利关闭
            if (jedis != null && jedis.isConnected()) {
                jedis.close();
            }
        }
        return result;
    }
}

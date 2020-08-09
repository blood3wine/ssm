package com.ssm.controller;

import com.ssm.service.UserRedPacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/userRedPacket")
public class UserRedPacketController {
    private final UserRedPacketService userRedPacketService;

    @Autowired
    public UserRedPacketController(UserRedPacketService userRedPacketService) {
        this.userRedPacketService = userRedPacketService;
    }

    /**
     * 抢红包
     *
     * @param redPacketId 红包id
     * @param userId      用户id
     * @return
     */
    @GetMapping(value = "/grapRedPacket")
    @ResponseBody
    public Map<String, Object> grepRedPacket(@RequestParam(name = "redPacketId") Long redPacketId
            , @RequestParam(name = "userId") Long userId) {

        //抢红包
        int result = userRedPacketService.grapRedPacket(redPacketId, userId);

        Map<String, Object> resMap = new HashMap<String, Object>(1 << 4);

        boolean flag = result > 0;
        resMap.put("success", flag);
        resMap.put("message", flag ? "抢红包成功！" : "抢红包失败");
        return resMap;
    }

    /**
     * 悲观锁抢红包
     *
     * @param redPacketId 红包id
     * @param userId      用户id
     * @return 响应结果
     */
    @GetMapping(value = "/grapRedPacketForLock")
    @ResponseBody
    public Map<String, Object> grapRedPacketForLock(@RequestParam(name = "redPacketId") Long redPacketId
            , @RequestParam(name = "userId") Long userId) {

        //抢红包
        int result = userRedPacketService.grapRedPacketByLock(redPacketId, userId);

        Map<String, Object> resMap = new HashMap<String, Object>(1 << 4);

        boolean flag = result > 0;
        resMap.put("success", flag);
        resMap.put("message", flag ? "抢红包成功！" : "抢红包失败");
        return resMap;
    }

    /**
     * 利用乐观锁抢红包
     *
     * @param redPacketId 红包id
     * @param userId      用户id
     * @return 响应结果
     */
    @GetMapping(value = "/grapRedPacketForVersion")
    @ResponseBody
    public Map<String, Object> grapRedPacketForVersion(@RequestParam(name = "redPacketId") Long redPacketId
            , @RequestParam(name = "userId") Long userId) {

        //抢红包
        long result = userRedPacketService.grapRedPacketByVersion(redPacketId, userId);

        Map<String, Object> resMap = new HashMap<String, Object>(1 << 4);

        boolean flag = result > 0;
        resMap.put("success", flag);
        resMap.put("message", flag ? "抢红包成功！" : "抢红包失败");
        return resMap;
    }

    /**
     * 利用乐观锁抢红包  --时间戳重入
     *
     * @param redPacketId 红包id
     * @param userId      用户id
     * @return 响应结果
     */
    @GetMapping(value = "/grapRedPacketForVersion1")
    @ResponseBody
    public Map<String, Object> grapRedPacketForVersion1(@RequestParam(name = "redPacketId") Long redPacketId
            , @RequestParam(name = "userId") Long userId) {

        //抢红包
        long result = userRedPacketService.grapRedPacketByVersion1(redPacketId, userId);

        Map<String, Object> resMap = new HashMap<String, Object>(1 << 4);

        boolean flag = result > 0;
        resMap.put("success", flag);
        resMap.put("message", flag ? "抢红包成功！" : "抢红包失败");
        return resMap;
    }

    /**
     * 利用乐观锁抢红包  --计数重入
     *
     * @param redPacketId 红包id
     * @param userId      用户id
     * @return 响应结果
     */
    @GetMapping(value = "/grapRedPacketForVersion2")
    @ResponseBody
    public Map<String, Object> grapRedPacketForVersion2(@RequestParam(name = "redPacketId") Long redPacketId
            , @RequestParam(name = "userId") Long userId) {

        //抢红包
        long result = userRedPacketService.grapRedPacketByVersion2(redPacketId, userId);

        Map<String, Object> resMap = new HashMap<String, Object>(1 << 4);

        boolean flag = result > 0;
        resMap.put("success", flag);
        resMap.put("message", flag ? "抢红包成功！" : "抢红包失败");
        return resMap;
    }


    /**
     * 利用Redis抢红包
     *
     * @param redPacketId 红包id
     * @param userId      用户id
     * @return 响应结果
     */
    @GetMapping(value = "/grapRedPacketForRedis")
    @ResponseBody
    public Map<String, Object> grapRedPacketForRedis(@RequestParam(name = "redPacketId") Long redPacketId
            , @RequestParam(name = "userId") Long userId) {

        //抢红包
        long result = userRedPacketService.grapRedPacketByRedis(redPacketId, userId);

        Map<String, Object> resMap = new HashMap<String, Object>(1 << 4);

        boolean flag = result > 0;
        resMap.put("success", flag);
        resMap.put("message", flag ? "抢红包成功！" : "抢红包失败");
        return resMap;
    }
}

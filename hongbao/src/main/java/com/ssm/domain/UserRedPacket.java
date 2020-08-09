package com.ssm.domain;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class UserRedPacket implements Serializable {
    private static final long serialVersionUID = -2007380653005524349L;

    private Long id;
    private Long redPacketId;
    private Long userId;
    private Double amount;
    private Timestamp grabTime;
    private String note;
}

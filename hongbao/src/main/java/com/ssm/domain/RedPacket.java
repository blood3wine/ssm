package com.ssm.domain;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class RedPacket implements Serializable {
    private static final long serialVersionUID = -5196106115236008960L;

    private Long id;
    private Long userId;
    private Double amount;
    private Timestamp sendDate;
    private Integer total;
    private Double unitAmount;
    private Integer stock;
    private Integer version;
    private String note;


}

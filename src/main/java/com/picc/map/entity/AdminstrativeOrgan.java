package com.picc.map.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by lhx on 2019/11/14.
 */
@Data
@TableName("PRPLISADMINISTRATIVEORGAN")
public class AdminstrativeOrgan implements Serializable {
    private static final long serialVersionUID = -1277171780468841527L;
    private Integer id;
    private String comCode;
    private String comName;
    private Integer comLevel;
    private String upperComCode;
    private Double longitude;
    private Double latitude;
}


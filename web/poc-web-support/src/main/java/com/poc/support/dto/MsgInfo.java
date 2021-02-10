package com.poc.support.dto;

import lombok.Getter;

@Getter
public class MsgInfo {
    private final String msgFormat;
    private final Object[] datas;

    private MsgInfo(String msgFormat, Object... datas){
        this.msgFormat = msgFormat;
        this.datas = datas;
    }

    public static MsgInfo of(String msgFormat, Object... datas) {
        return new MsgInfo(msgFormat, datas);
    }
}

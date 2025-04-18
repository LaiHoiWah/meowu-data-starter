package com.meowu.starter.data.entity;

import java.util.Date;

public interface Updatable{

    Date getUpdateTime();

    void setUpdateTime(Date updateTime);
}

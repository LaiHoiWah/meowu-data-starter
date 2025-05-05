package com.meowu.starter.data.core.entity;

import java.util.Date;

public interface Updatable{

    Date getUpdateTime();

    void setUpdateTime(Date updateTime);
}

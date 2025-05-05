package com.meowu.starter.data.core.entity;

import java.util.Date;

public interface Deletable{

    Date getDeleteTime();

    void setDeleteTime(Date deleteTime);
}

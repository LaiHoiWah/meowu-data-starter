package com.meowu.starter.data.core.entity;

import java.io.Serializable;

public interface Identity<T> extends Serializable{

    T getId();

    void setId(T id);
}

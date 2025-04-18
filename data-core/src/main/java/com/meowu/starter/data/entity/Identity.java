package com.meowu.starter.data.entity;

import java.io.Serializable;

public interface Identity<T> extends Serializable{

    T getId();

    void setId(T id);
}

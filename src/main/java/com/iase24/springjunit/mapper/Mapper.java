package com.iase24.springjunit.mapper;

public interface Mapper <F, T> {

    T map(F object);
}

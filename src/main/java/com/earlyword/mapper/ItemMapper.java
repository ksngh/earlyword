package com.earlyword.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.earlyword.domain.Item;

@Mapper
public interface ItemMapper {

	Item findById(Long itemNumber);
}

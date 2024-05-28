package com.earlyword.mapper;

import static org.junit.jupiter.api.Assertions.*;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.earlyword.domain.Item;

@SpringBootTest
class ItemMapperTest {

	@Autowired
	private ItemMapper itemMapper;

	@Test
	void 상품정보_조회() {
		Item findItem = itemMapper.findById(1L);
		System.out.println(findItem);
	}
}
package com.earlyword.mapper;

import org.assertj.core.api.Assertions;
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

	@Test
	void 상품정보_저장() {
		Item item = Item.builder()
			.itemName("영어 단어(중급)")
			.itemPrice(10000)
			.build();

		int result = itemMapper.save(item);
		Assertions.assertThat(result).isEqualTo(1);
	}
}
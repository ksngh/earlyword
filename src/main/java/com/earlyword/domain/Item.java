package com.earlyword.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class Item {
	private Long itemNumber;
	private String itemName;
	private int itemPrice;
}

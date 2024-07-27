package com.earlyword.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Word {
	private int wordNumber;
	private int itemNumber;
	private String word;
	private String definition;
	private LocalDateTime createDate;

}

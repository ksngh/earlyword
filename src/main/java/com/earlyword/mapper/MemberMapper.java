package com.earlyword.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.earlyword.domain.Member;

@Mapper
public interface MemberMapper {
	Optional<Member> findByEmail(String email);

	Optional<Member> save(Member member);
}

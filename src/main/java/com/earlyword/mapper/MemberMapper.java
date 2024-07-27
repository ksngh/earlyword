package com.earlyword.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.earlyword.domain.Member;

@Mapper
public interface MemberMapper {

	List<Member> selectAllMembers();  // 전체 회원 조회

	Member selectMemberByEmail(String email);   // ID로 회원 조회

	void insertMember(Member member);   // 회원 추가

	void updateMember(Member member);    // 회원 정보 수정

	void deleteMember(String email);           // 회원 삭제
}

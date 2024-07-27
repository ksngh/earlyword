package com.earlyword.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.earlyword.domain.Member;
import com.earlyword.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService {

	private final MemberMapper memberMapper;

	public void saveOrUpdateMember(Member member){
		if (memberMapper.selectMemberByEmail(member.getEmail())==null){
			memberMapper.insertMember(member);
		} else {
			memberMapper.updateMember(member);
		}
	}

	public List<Member> getMemberList(){
		return memberMapper.selectAllMembers();
	}

	public Member getMemberByEmail(String email){
		return memberMapper.selectMemberByEmail(email);
	}

	public void updateMember(Member member){
		memberMapper.updateMember(member);
	}
}

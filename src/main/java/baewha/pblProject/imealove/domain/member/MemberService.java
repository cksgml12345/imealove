package baewha.pblProject.imealove.domain.member;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public boolean checkLoginIdDuplicate(String loginId) {
		return memberRepository.existsByLoginId(loginId);
	}

	public void join(JoinRequest joinRequest) {
		memberRepository.save(joinRequest.toEntity());
	}

	public Member login(LoginRequest loginRequest) {
		Member findMember = memberRepository.findByLoginId(loginRequest.getLoginId());

		if(findMember == null){
			return null;
		}

		if (!findMember.getPassword().equals(loginRequest.getPassword())) {
			return null;
		}

		return findMember;
	}

	public void securityJoin(JoinRequest joinRequest){
		if(memberRepository.existsByLoginId(joinRequest.getLoginId())){
			return;
		}

		joinRequest.setPassword(bCryptPasswordEncoder.encode(joinRequest.getPassword()));

		memberRepository.save(joinRequest.toEntity());
	}

	public Member getLoginMemberByLoginId(String loginId) {
		if(loginId == null) return null;
		Optional<Member> findMember = Optional.ofNullable(memberRepository.findByLoginId(loginId));
		return findMember.orElse(null);
	}

	public Member getLoginMemberById(Long memberId){
		if(memberId == null) return null;

		Optional<Member> findMember = memberRepository.findById(memberId);
		return findMember.orElse(null);
	}
}

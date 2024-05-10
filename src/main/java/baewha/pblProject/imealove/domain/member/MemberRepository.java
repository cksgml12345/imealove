package baewha.pblProject.imealove.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>  {

	boolean existsByLoginId(String loginId);

	Member findByLoginId(String loginId);
}

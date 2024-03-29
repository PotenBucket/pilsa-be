package potenday.pilsa.member.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import potenday.pilsa.member.domain.Member;
import potenday.pilsa.member.domain.SocialType;
import potenday.pilsa.member.domain.Status;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findBySocialTypeAndMemberKey(SocialType socialType, String memberKey);
    Optional<Member> findByIdAndStatus(Long id, Status status);
}

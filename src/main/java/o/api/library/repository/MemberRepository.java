package o.api.library.repository;

import o.api.library.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByIdAndBalanceGreaterThanEqual(Long checkoutId, int price);
}

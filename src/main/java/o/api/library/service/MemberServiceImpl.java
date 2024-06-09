package o.api.library.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import o.api.library.config.ApiResponseManager;
import o.api.library.dto.MemberCreateDto;
import o.api.library.dto.MemberUpdateDto;
import o.api.library.entity.Member;
import o.api.library.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {
    private MemberRepository memberRepository;

    @Override
    public ApiResponseManager createMember(MemberCreateDto memberCreateDto) {
        return ApiResponseManager.success(memberRepository.save(memberCreateDto.create()));
    }

    @Override
    public ApiResponseManager readMember(Long id) {
        return ApiResponseManager.success(memberRepository.findById(id));
    }

    @Override
    public ApiResponseManager readMemberList() {
        return ApiResponseManager.success(memberRepository.findAll());
    }

    @Override
    public ApiResponseManager deleteMember(Long id) {
        Optional<Member> member = memberRepository.findById(id);
        if (member.isPresent()) {
            memberRepository.deleteById(id);
            return ApiResponseManager.success(member);
        } else {
            return ApiResponseManager.failed(member);
        }
    }

    @Override
    public ApiResponseManager updateMember(MemberUpdateDto memberUpdateDto) {
        return ApiResponseManager.success(memberRepository.save(memberUpdateDto.update()));
    }
}

package o.api.library.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import o.api.library.config.ApiResponseManager;
import o.api.library.dto.MemberSignUpDto;
import o.api.library.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {
    private MemberRepository memberRepository;

    @Override
    public ApiResponseManager signUp(MemberSignUpDto memberSignUpDto) {
        return ApiResponseManager.success(memberRepository.save(memberSignUpDto.signUp()));
    }
}

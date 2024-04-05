package o.api.library.service;

import jdk.jfr.Description;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import o.api.library.config.ApiResponseManager;
import o.api.library.dto.BookCheckoutDto;
import o.api.library.dto.BookConsignDto;
import o.api.library.dto.BookListDto;
import o.api.library.dto.MemberSignUpDto;
import o.api.library.repository.BookRepository;
import o.api.library.repository.MemberRepository;
import org.springframework.scheduling.annotation.Scheduled;
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

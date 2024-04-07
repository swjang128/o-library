package o.api.library.service;

import o.api.library.config.ApiResponseManager;
import o.api.library.dto.MemberSignUpDto;

public interface MemberService {
    ApiResponseManager signUp(MemberSignUpDto memberSignUpDto);
}

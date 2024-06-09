package o.api.library.service;

import o.api.library.config.ApiResponseManager;
import o.api.library.dto.MemberSignUpDto;

public interface MemberService {
    ApiResponseManager createMember(MemberSignUpDto memberSignUpDto);

    ApiResponseManager readMember(Long id);

    ApiResponseManager readMemberList();
}

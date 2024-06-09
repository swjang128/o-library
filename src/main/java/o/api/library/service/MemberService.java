package o.api.library.service;

import o.api.library.config.ApiResponseManager;
import o.api.library.dto.MemberCreateDto;
import o.api.library.dto.MemberUpdateDto;

public interface MemberService {
    ApiResponseManager createMember(MemberCreateDto memberCreateDto);

    ApiResponseManager readMember(Long id);

    ApiResponseManager readMemberList();

    ApiResponseManager deleteMember(Long id);

    ApiResponseManager updateMember(MemberUpdateDto memberUpdateDto);
}

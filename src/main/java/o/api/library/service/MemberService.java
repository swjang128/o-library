package o.api.library.service;

import o.api.library.config.ApiResponseManager;
import o.api.library.dto.BookCheckoutDto;
import o.api.library.dto.BookConsignDto;
import o.api.library.dto.BookListDto;
import o.api.library.dto.MemberSignUpDto;

public interface MemberService {
    ApiResponseManager signUp(MemberSignUpDto memberSignUpDto);
}

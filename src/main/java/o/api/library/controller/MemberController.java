package o.api.library.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import o.api.library.config.ApiResponseManager;
import o.api.library.dto.MemberSignUpDto;
import o.api.library.service.MemberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Tag(name = "회원 관리 API", description = "회원 관리 API 모음")
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "회원 가입", description = "회원 정보를 등록하는 API")
    @ApiResponse(responseCode = "200", description = "OK")
    @PostMapping()
    public ApiResponseManager signUp(@Valid @RequestBody MemberSignUpDto memberSignUpDto) {
        return memberService.signUp(memberSignUpDto);
    }

}

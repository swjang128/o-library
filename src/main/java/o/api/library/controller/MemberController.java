package o.api.library.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import o.api.library.config.ApiResponseManager;
import o.api.library.dto.MemberSignUpDto;
import o.api.library.service.MemberService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Tag(name = "회원 관리 API", description = "회원 관리 API 모음")
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "회원 가입", description = "회원 정보를 등록하는 API")
    @ApiResponse(responseCode = "200", description = "OK")
    @PostMapping()
    public ApiResponseManager createMember(@Valid @RequestBody MemberSignUpDto memberSignUpDto) {
        return memberService.createMember(memberSignUpDto);
    }

    @Operation(summary = "특정 회원 조회", description = "특정 회원 정보를 조회하는 API")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping("/{id}")
    public ApiResponseManager readMember(@PathVariable(value = "id", defaultValue = "1") Long id) {
        return memberService.readMember(id);
    }

    @Operation(summary = "전체 회원 조회", description = "전체 회원 정보를 조회하는 API")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping()
    public ApiResponseManager readMemberList() {
        return memberService.readMemberList();
    }

}

package o.api.library.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jdk.jfr.Description;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import o.api.library.entity.Member;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberSignUpDto {
    @Schema(defaultValue = "장성욱")
    @Size(min = 1,  max = 50)
    private String name;

    @Schema(defaultValue = "swjang128@udo.com")
    @Email
    private String email;

    @Schema(defaultValue = "P@ssw0rd")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).{6,10}$", message = "비밀번호는 영문 대소문자, 숫자 중 최소 2가지 이상의 조합으로 최소 6자 이상, 최대 10자 이하이어야 합니다.")
    private String password;

    @Schema(defaultValue = "01012349876")
    @Pattern(regexp = "\\d{11}")
    private String phone;

    @Description("회원 가입에 필요한 엔티티 생성")
    public Member signUp() {
        return Member.builder()
                .name(name)
                .email(email)
                .password(password)
                .phone(phone)
                .build();
    }
}

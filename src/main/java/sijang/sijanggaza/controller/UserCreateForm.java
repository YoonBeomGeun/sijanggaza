package sijang.sijanggaza.controller;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import sijang.sijanggaza.domain.UserStatus;

@Getter @Setter
public class UserCreateForm {

    @Size(min = 3, max = 25, message = "아이디는 3 ~ 25자입니다.")
    @NotEmpty(message = "사용자 ID는 필수입니다.")
    private String username;

    @NotEmpty(message = "비밀번호는 필수입니다.")
    private String password1;

    @NotEmpty(message = "비밀번호 확인은 필수입니다.")
    private String password2;

    @NotEmpty(message = "이메일은 필수입니다.")
    @Email
    private String email;

    @NotNull(message = "회원 구분은 필수입니다.")
    private String status;

}

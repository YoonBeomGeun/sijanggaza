package sijang.sijanggaza.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import sijang.sijanggaza.domain.UserStatus;

@Data
@AllArgsConstructor
public class UserRequestDTO {
    private String username;
    private String password;
    private String email;
    private UserStatus status;

}

package sijang.sijanggaza.dto.user.response;

import lombok.Data;

@Data
public class CreateUserResponseDTO {

    private Long id;

    public CreateUserResponseDTO(Long id) {
        this.id = id;
    }

}

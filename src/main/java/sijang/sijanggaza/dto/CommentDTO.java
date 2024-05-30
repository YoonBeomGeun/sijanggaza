package sijang.sijanggaza.dto;

import lombok.Data;
import sijang.sijanggaza.domain.SiteUser;
import sijang.sijanggaza.dto.user.UserDto;

import java.util.Set;

@Data
public class CommentDTO {
    private Long id;
    private String content;
    private UserDto author;
    private Set<SiteUser> ddabong;
}

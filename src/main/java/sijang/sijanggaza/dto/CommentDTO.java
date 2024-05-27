package sijang.sijanggaza.dto;

import lombok.Data;
import sijang.sijanggaza.domain.SiteUser;

import java.util.Set;

@Data
public class CommentDTO {
    private Long id;
    private String content;
    private SiteUserDTO author;
    private Set<SiteUser> ddabong;
}

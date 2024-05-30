package sijang.sijanggaza.dto;

import lombok.Data;
import sijang.sijanggaza.domain.SiteUser;
import sijang.sijanggaza.dto.user.UserDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
public class BoardDTO {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime postDate;
    private UserDto author;
    private LocalDateTime modifyDate;
    private List<CommentDTO> commentList;
    private List<ItemDTO> itemList;
    private Set<SiteUser> ddabong;
}

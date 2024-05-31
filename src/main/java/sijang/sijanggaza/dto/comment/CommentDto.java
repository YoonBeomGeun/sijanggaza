package sijang.sijanggaza.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import sijang.sijanggaza.domain.Comment;
import sijang.sijanggaza.domain.SiteUser;
import sijang.sijanggaza.dto.user.UserDto;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
public class CommentDto {
    private Long id;
    private String content;
    private String author;
    private LocalDateTime postDate;
    private LocalDateTime modifyDate;

    public CommentDto(Comment comment) {
        id = comment.getId();
        content = comment.getContent();
        author = comment.getAuthor().getUsername();
        postDate = comment.getPostDate();
        modifyDate = comment.getModifyDate();
    }
}

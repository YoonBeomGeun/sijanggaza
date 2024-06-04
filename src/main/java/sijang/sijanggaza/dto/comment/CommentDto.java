package sijang.sijanggaza.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sijang.sijanggaza.domain.Comment;
import sijang.sijanggaza.domain.SiteUser;
import sijang.sijanggaza.dto.user.UserDto;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private String content;
    private String author;
    private Long boardId;
    private LocalDateTime postDate;
    private LocalDateTime modifyDate;

    public CommentDto(Comment comment) {
        id = comment.getId();
        content = comment.getContent();
        author = comment.getAuthor().getUsername();
        boardId = comment.getBoard() != null ? comment.getBoard().getId() : null;
        postDate = comment.getPostDate();
        modifyDate = comment.getModifyDate();
    }
}

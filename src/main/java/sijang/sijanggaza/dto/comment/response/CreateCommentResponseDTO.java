package sijang.sijanggaza.dto.comment.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sijang.sijanggaza.domain.Comment;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentResponseDTO {
    private Long id;
    private String content;
    private LocalDateTime postDate;
    private LocalDateTime modifyDate;

    public CreateCommentResponseDTO(Comment comment) {
        id = comment.getId();
        content = comment.getContent();
        postDate = comment.getPostDate();
        modifyDate = comment.getModifyDate();
    }
}

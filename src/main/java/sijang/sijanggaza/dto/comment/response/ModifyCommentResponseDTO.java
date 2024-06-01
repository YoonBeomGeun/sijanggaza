package sijang.sijanggaza.dto.comment.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sijang.sijanggaza.domain.Comment;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifyCommentResponseDTO {
    private Long id;
    private String content;
    private LocalDateTime postDate;
    private LocalDateTime modifyDate;

    public ModifyCommentResponseDTO(Comment comment) {
        id = comment.getId();
        content = comment.getContent();
        postDate = comment.getPostDate();
        modifyDate = comment.getModifyDate();
    }
}

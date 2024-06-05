package sijang.sijanggaza.dto.board.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sijang.sijanggaza.domain.Board;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserBoardResponseDTO {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime postDate;
    private LocalDateTime modifyDate;

    public CreateUserBoardResponseDTO(Board board) {
        id = board.getId();
        title = board.getTitle();
        content = board.getContent();
        postDate = board.getPostDate();
        modifyDate = board.getModifyDate();
    }
}

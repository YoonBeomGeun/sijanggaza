package sijang.sijanggaza.dto.board.response;

import lombok.Data;
import sijang.sijanggaza.domain.Board;
import sijang.sijanggaza.dto.comment.CommentDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserBoardResponseDTO {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime postDate;
    private LocalDateTime modifyDate;
    private List<CommentDto> commentDtoList;

    public UserBoardResponseDTO(Board board) {
        id = board.getId();
        title = board.getTitle();
        content = board.getContent();
        postDate = board.getPostDate();
        modifyDate = board.getModifyDate();
        commentDtoList = board.getCommentList().stream()
                .map(comment -> new CommentDto(comment))
                .collect(Collectors.toList());
    }
}

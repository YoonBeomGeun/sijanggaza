package sijang.sijanggaza.dto.board.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sijang.sijanggaza.domain.Board;
import sijang.sijanggaza.dto.comment.CommentDto;
import sijang.sijanggaza.dto.item.ItemDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CeoBoardResponseDTO {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime postDate;
    private LocalDateTime modifyDate;
    private List<CommentDto> commentDtoList;
    private List<ItemDto> itemDtoList;

    public CeoBoardResponseDTO(Board board) {
        id = board.getId();
        title = board.getTitle();
        content = board.getContent();
        postDate = board.getPostDate();
        modifyDate = board.getModifyDate();
        commentDtoList = board.getCommentList().stream()
                .map(comment -> new CommentDto(comment))
                .collect(Collectors.toList());
        itemDtoList = board.getItemList().stream()
                .map(item -> new ItemDto(item))
                .collect(Collectors.toList());
    }
}

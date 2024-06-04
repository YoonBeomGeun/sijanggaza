package sijang.sijanggaza.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sijang.sijanggaza.domain.Board;
import sijang.sijanggaza.dto.comment.CommentDto;
import sijang.sijanggaza.dto.item.ItemDto;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime postDate;
    private String author;
    private LocalDateTime modifyDate;
    private List<CommentDto> commentList;
    private List<ItemDto> itemList;

    public BoardDto(Board board) {
        id = board.getId();
        title = board.getTitle();
        content = board.getContent();
        postDate = board.getPostDate();
        author = board.getAuthor().getUsername();
        modifyDate = board.getModifyDate();
        commentList = board.getCommentList().stream()
                .map(comment -> new CommentDto(comment))
                .collect(toList());
        itemList = board.getItemList().stream()
                .map(item -> new ItemDto(item))
                .collect(toList());
    }
}

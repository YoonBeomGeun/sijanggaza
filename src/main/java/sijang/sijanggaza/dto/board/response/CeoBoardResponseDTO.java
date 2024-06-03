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
    private String author;
    private LocalDateTime postDate;
    private LocalDateTime modifyDate;
    private List<ItemDto> itemDtoList;

    public CeoBoardResponseDTO(Board board) {
        id = board.getId();
        title = board.getTitle();
        content = board.getContent();
        author = board.getAuthor().getUsername();
        postDate = board.getPostDate();
        modifyDate = board.getModifyDate();
        itemDtoList = board.getItemList().stream()
                .map(item -> new ItemDto(item))
                .collect(Collectors.toList());
    }

    public CeoBoardResponseDTO(Long id, String title, String content, LocalDateTime postDate, LocalDateTime modifyDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.postDate = postDate;
        this.modifyDate = modifyDate;
    }

}

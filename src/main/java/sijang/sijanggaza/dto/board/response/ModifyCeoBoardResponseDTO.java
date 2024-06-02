package sijang.sijanggaza.dto.board.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sijang.sijanggaza.domain.Board;
import sijang.sijanggaza.dto.item.ItemDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifyCeoBoardResponseDTO {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime postDate;
    private LocalDateTime modifyDate;
    private List<ItemDto> itemDtoList;

    public ModifyCeoBoardResponseDTO(Board board) {
        id = board.getId();
        title = board.getTitle();
        content = board.getContent();
        postDate = board.getPostDate();
        modifyDate = board.getModifyDate();
        itemDtoList = board.getItemList().stream()
                .map(item -> new ItemDto(item))
                .collect(Collectors.toList());
    }
}

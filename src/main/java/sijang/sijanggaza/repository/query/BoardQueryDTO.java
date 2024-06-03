package sijang.sijanggaza.repository.query;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import sijang.sijanggaza.domain.Board;
import sijang.sijanggaza.dto.board.response.CeoBoardResponseDTO;
import sijang.sijanggaza.dto.item.ItemDto;
import sijang.sijanggaza.repository.BoardRepository;
import sijang.sijanggaza.repository.ItemRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BoardQueryDTO {

    private final BoardRepository boardRepository;
    private final ItemRepository itemRepository;


}

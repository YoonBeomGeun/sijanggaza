package sijang.sijanggaza.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sijang.sijanggaza.repository.ItemRepository;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

}

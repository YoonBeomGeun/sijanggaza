package sijang.sijanggaza.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import sijang.sijanggaza.service.ItemService;

@Controller
@RequiredArgsConstructor

public class ItemBoardController {

    private final ItemService itemService;


}

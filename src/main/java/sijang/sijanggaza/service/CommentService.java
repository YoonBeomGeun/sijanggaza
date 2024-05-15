package sijang.sijanggaza.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sijang.sijanggaza.domain.Board;
import sijang.sijanggaza.domain.Comment;
import sijang.sijanggaza.repository.CommentRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {

    @Autowired
    private final CommentRepository commentRepository;

    public void create(Board board, String content) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setPostDate(LocalDateTime.now());
        comment.setBoard(board);
        this.commentRepository.save(comment);
    }
}

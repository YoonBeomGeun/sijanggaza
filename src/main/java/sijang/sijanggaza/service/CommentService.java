package sijang.sijanggaza.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sijang.sijanggaza.domain.Board;
import sijang.sijanggaza.domain.Comment;
import sijang.sijanggaza.domain.SiteUser;
import sijang.sijanggaza.repository.CommentRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public void create(Board board, String content, SiteUser author) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setPostDate(LocalDateTime.now());
        comment.setBoard(board);
        comment.setAuthor(author);
        this.commentRepository.save(comment);
    }
}

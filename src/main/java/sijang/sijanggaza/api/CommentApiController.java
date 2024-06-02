package sijang.sijanggaza.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sijang.sijanggaza.domain.Board;
import sijang.sijanggaza.domain.Comment;
import sijang.sijanggaza.domain.SiteUser;
import sijang.sijanggaza.dto.board.response.DeleteUserBoardResponseDTO;
import sijang.sijanggaza.dto.comment.CommentDto;
import sijang.sijanggaza.dto.comment.request.CommentRequestDTO;
import sijang.sijanggaza.dto.comment.response.CreateCommentResponseDTO;
import sijang.sijanggaza.dto.comment.response.DeleteCommentResponseDTO;
import sijang.sijanggaza.dto.comment.response.ModifyCommentResponseDTO;
import sijang.sijanggaza.repository.CommentRepository;
import sijang.sijanggaza.service.BoardService;
import sijang.sijanggaza.service.CommentService;
import sijang.sijanggaza.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;
    private final BoardService boardService;
    private final UserService userService;
    private final CommentRepository commentRepository;

    /**
     * 댓글 단건 조회
     */
    @GetMapping("/api/v1/comment/{commentId}")
    public CommentDto getComment(@PathVariable("commentId") Long commentId) {
        Comment comment = this.commentService.getComment(Math.toIntExact(commentId));
        return new CommentDto(comment);
    }

    /**
     * 댓글 전체 조회(최적화 완료)
     */
    @GetMapping("/api/v1/commentList/{boardId}")
    public List<CommentDto> getCommentList(@PathVariable("boardId") Long boardId) {
        List<CommentDto> result = this.commentRepository.findAllComment(boardId).stream()
                .map(comment -> new CommentDto(comment))
                .collect(Collectors.toList());
        return result;
    }

    /**
     * 댓글 생성
     */
    @PostMapping("/api/v1/comment/{boardId}")
    public CreateCommentResponseDTO createComment(@PathVariable("boardId") Long boardId, @RequestBody CommentRequestDTO request) {
        Board board = this.boardService.getBoard(Math.toIntExact(boardId));
        SiteUser siteUser = this.userService.getUser(board.getAuthor().getUsername());
        Comment comment = this.commentService.create(board, request.getContent(), siteUser);
        return new CreateCommentResponseDTO(comment);
    }

    /**
     * 댓글 수정
     */
    @PutMapping("/api/v1/comment/{commentId}")
    public ModifyCommentResponseDTO modifyComment(@PathVariable("commentId") Long commentId, @RequestBody CommentRequestDTO request) {
        Comment comment = this.commentService.getComment(Math.toIntExact(commentId));
        this.commentService.modify(comment, request.getContent());
        return new ModifyCommentResponseDTO(comment);
    }

    /**
     * 댓글 삭제
     */
    @DeleteMapping("/api/v1/comment/{commentId}")
    public DeleteCommentResponseDTO deleteComment(@PathVariable("commentId") Long commentId) {
        Comment comment = this.commentService.getComment(Math.toIntExact(commentId));
        this.commentService.delete(comment);
        return new DeleteCommentResponseDTO(comment.getId());
    }

}
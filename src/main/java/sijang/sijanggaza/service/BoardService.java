package sijang.sijanggaza.service;

import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sijang.sijanggaza.controller.BoardForm;
import sijang.sijanggaza.exception.DataNotFoundException;
import sijang.sijanggaza.domain.Board;
import sijang.sijanggaza.domain.Comment;
import sijang.sijanggaza.domain.SiteUser;
import sijang.sijanggaza.repository.BoardRepository;
import sijang.sijanggaza.repository.CommentRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public List<Board> getList() {
        return this.boardRepository.findAll();
    }

    public void save(Board board) {
        this.boardRepository.save(board);
    }


    //게시글 목록 페이징으로 불러오기
    //회원 유형 == USER
    public Page<Board> getListOfUserV1(int page, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("postDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        /*Specification<Board> spec = search(kw); // Specification 방법으로 검색
        return this.boardRepository.findAll(spec, pageable);*/
        return this.boardRepository.findAllByKeywordOfUserV1(kw, pageable);
    }

    public Page<Board> getListOfUserV2(int page, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("postDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.boardRepository.findAllByKeywordOfUserV2(kw, pageable);
    }


    //회원 유형 == CEO
    public Page<Board> getListOfCeoV1(int page, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("postDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        /*Specification<Board> spec = search(kw); // Specification 방법으로 검색
        return this.boardRepository.findAll(spec, pageable);*/
        return this.boardRepository.findAllByKeywordOfCeoV1(kw, pageable);
    }

    public Page<Board> getListOfCeoV2(int page, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("postDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.boardRepository.findAllByKeywordOfCeoV2(kw, pageable);
    }

    public Board getBoard(Integer id) {
        Optional<Board> board = this.boardRepository.findById(id);
        if (board.isPresent()) {
            return board.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }

    @Transactional
    public Long create(SiteUser user, BoardForm boardForm) {
        Board b = new Board();
        b.setTitle(boardForm.getTitle());
        b.setContent(boardForm.getContent());
        b.setPostDate(LocalDateTime.now());
        b.setAuthor(user);
        Board board = this.boardRepository.save(b);
        return board.getId();
    }

    @Transactional
    public Board itemBoardcreate(String title, String content, SiteUser user) {
        Board b = new Board();
        b.setTitle(title);
        b.setContent(content);
        b.setPostDate(LocalDateTime.now());
        b.setAuthor(user);
        this.boardRepository.save(b);
        return b;
    }

    @Transactional
    public void modify(Board board, String title, String content) {
        board.setTitle(title);
        board.setContent(content);
        board.setModifyDate(LocalDateTime.now());
        this.boardRepository.save(board);
    }

    @Transactional
    public void delete(Board board) {
        this.boardRepository.delete(board);
    }

    public void ddabong(Board board, SiteUser siteUser) {
        board.getDdabong().add(siteUser);
        this.boardRepository.save(board);
    }

    private Specification<Board> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Board> b, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거
                Join<Board, SiteUser> u1 = b.join("author", JoinType.LEFT);
                Join<Board, Comment> c = b.join("commentList", JoinType.LEFT);
                Join<Comment, SiteUser> u2 = c.join("author", JoinType.LEFT);
                return cb.or(cb.like(b.get("title"), "%" + kw + "%"), // 제목
                        cb.like(b.get("content"), "%" + kw + "%"),      // 내용
                        cb.like(u1.get("username"), "%" + kw + "%"),    // 질문 작성자
                        cb.like(c.get("content"), "%" + kw + "%"),      // 답변 내용
                        cb.like(u2.get("username"), "%" + kw + "%"));   // 답변 작성자
            }
        };
    }

}

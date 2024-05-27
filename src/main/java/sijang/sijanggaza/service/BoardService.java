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
import sijang.sijanggaza.dto.BoardDTO;
import sijang.sijanggaza.dto.CommentDTO;
import sijang.sijanggaza.dto.ItemDTO;
import sijang.sijanggaza.dto.SiteUserDTO;
import sijang.sijanggaza.exception.DataNotFoundException;
import sijang.sijanggaza.domain.Board;
import sijang.sijanggaza.domain.Comment;
import sijang.sijanggaza.domain.SiteUser;
import sijang.sijanggaza.repository.BoardRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public List<Board> getList() {
        return this.boardRepository.findAll();
    }

    public void save(Board board) {
        this.boardRepository.save(board);
    }

    //게시글 목록 페이징으로 불러오기
    //회원 유형 == USER
    public Page<Board> getListOfUser(int page, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("postDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        /*Specification<Board> spec = search(kw); // Specification 방법으로 검색
        return this.boardRepository.findAll(spec, pageable);*/
        return this.boardRepository.findAllByKeywordOfUser(kw, pageable);
    }

    //회원 유형 == CEO
    public Page<Board> getListOfCeo(int page, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("postDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        /*Specification<Board> spec = search(kw); // Specification 방법으로 검색
        return this.boardRepository.findAll(spec, pageable);*/
        return this.boardRepository.findAllByKeywordOfCeo(kw, pageable);
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
    public void create(String title, String content, SiteUser user) {
        Board b = new Board();
        b.setTitle(title);
        b.setContent(content);
        b.setPostDate(LocalDateTime.now());
        b.setAuthor(user);
        this.boardRepository.save(b);
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

    /**
     * DTO 사용
     */
    /*public BoardDTO getBoardDTO(Integer id) {
        Board board = this.getBoard(id);
        return convertToDTO(board);
    }

    private BoardDTO convertToDTO(Board board) {
        BoardDTO dto = new BoardDTO();
        dto.setId(board.getId());
        dto.setTitle(board.getTitle());
        dto.setContent(board.getContent());
        dto.setPostDate(board.getPostDate());
        dto.setAuthor(convertToSiteUserDTO(board.getAuthor()));
        dto.setModifyDate(board.getModifyDate());
        dto.setCommentList(board.getCommentList().stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setId(comment.getId());
            commentDTO.setContent(comment.getContent());
            commentDTO.setAuthor(convertToSiteUserDTO(comment.getAuthor()));
            commentDTO.setDdabong(comment.getDdabong());
            return commentDTO;
        }).collect(Collectors.toList()));
        dto.setItemList(board.getItemList().stream().map(item -> {
            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setId(item.getId());
            itemDTO.setIName(item.getIName());
            itemDTO.setPrice(item.getPrice());
            itemDTO.setStockQuantity(item.getStockQuantity());
            return itemDTO;
        }).collect(Collectors.toList()));
        dto.setDdabong(board.getDdabong());
        return dto;
    }

    private SiteUserDTO convertToSiteUserDTO(SiteUser user) {
        if (user == null) {
            return null;
        }
        SiteUserDTO userDTO = new SiteUserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        return userDTO;
    }*/
}

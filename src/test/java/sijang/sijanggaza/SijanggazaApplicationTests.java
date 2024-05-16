package sijang.sijanggaza;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sijang.sijanggaza.domain.Board;
import sijang.sijanggaza.domain.Comment;
import sijang.sijanggaza.domain.SiteUser;
import sijang.sijanggaza.domain.UserStatus;
import sijang.sijanggaza.repository.BoardRepository;
import sijang.sijanggaza.repository.CommentRepository;
import sijang.sijanggaza.repository.UserRepository;
import sijang.sijanggaza.service.BoardService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SijanggazaApplicationTests {

	@Autowired
	private BoardRepository boardRepository;
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BoardService boardService;

	@Test
	void testJpa() {
		Board b1 = new Board();
		b1.setTitle("맛집 추천");
		b1.setContent("맛집 추천해줘요");
		b1.setPostDate(LocalDateTime.now());
		this.boardRepository.save(b1);

		Board b2 = new Board();
		b2.setTitle("치킨");
		b2.setContent("치킨 최고");
		b2.setPostDate(LocalDateTime.now());
		this.boardRepository.save(b2);
	}

	@Test
	void 사용자등록() {
		SiteUser user = new SiteUser();
		user.setUsername("범근");
		user.setPassword("1111");
		user.setEmail("beomgeun@naver.com");
		this.userRepository.save(user);
		assertEquals("범근", user.getUsername());
	}

	/*@Test
	void 사용자수정() {
		Optional<SiteUser> os = this.userRepository.findById(1);
		assertTrue(os.isPresent());
		SiteUser user = os.get();
		user.setStatus(UserStatus.USER);
		this.userRepository.save(user);
	}*/

	@Test
	void 보드리스트뽑기() {
		List<Board> all = this.boardRepository.findAll();
		assertEquals(2, all.size());

		Board b = all.get(0);
		assertEquals("맛집 추천", b.getTitle());
	}

	@Test
	void 보드아이디로찾기() {
		Optional<Board> ob = this.boardRepository.findById(1);
		//1에 대한 값이 없을 수도 있기 때문에 null 값을 유연하게 처리하기 위해 Optional 클래스 사용
		if (ob.isPresent()) {
			Board b = ob.get();
			assertEquals("맛집 추천해줘요", b.getContent());
		}
	}

	@Test
	void 보드제목으로찾기() {
		Board b = this.boardRepository.findByTitle("맛집 추천");
		assertEquals(1, b.getId());
	}

	@Test
	void 보드제목과내용으로찾기() {
		Board b = this.boardRepository.findByTitleAndContent("맛집 추천", "맛집 추천해줘요");
		assertEquals(1, b.getId());
	}

	@Test
	void 제목Like로찾기() {
		List<Board> bList = this.boardRepository.findByTitleLike("맛집%");
		Board b = bList.get(0);
		assertEquals("맛집 추천", b.getTitle());
	}

	@Test
	void 게시판수정() {
		Optional<Board> ob = this.boardRepository.findById(1);
		assertTrue(ob.isPresent());
		Board b = ob.get();
		b.setTitle("제목 수정 테스트");
		this.boardRepository.save(b);
	}

	@Test
	void 게시판삭제() {
		assertEquals(2, this.boardRepository.count());
		Optional<Board> ob = this.boardRepository.findById(1);
		assertTrue(ob.isPresent());
		Board b = ob.get();
		this.boardRepository.delete(b);
		assertEquals(1, this.boardRepository.count());
	}
	@Test
	void 댓글저장() {
		Optional<Board> ob = this.boardRepository.findById(2);
		assertTrue(ob.isPresent());
		Board b = ob.get();

		Comment c = new Comment();
		c.setContent("나도 최고");
		c.setBoard(b);  // 어떤 질문의 답변인지 알기위해서 Question 객체가 필요하다.
		c.setPostDate(LocalDateTime.now());
		this.commentRepository.save(c);
	}

	@Test
	void 댓글단건조회() {
		Optional<Comment> oc = this.commentRepository.findById(1);
		assertTrue(oc.isPresent());
		Comment c = oc.get();
		assertEquals(2, c.getBoard().getId());
	}

	@Test
	@Transactional
	void 댓글전체조회() {
		Optional<Board> ob = this.boardRepository.findById(2);
		assertTrue(ob.isPresent());
		Board q = ob.get();

		List<Comment> answerList = q.getCommentList();

		assertEquals(1, answerList.size());
		assertEquals("나도 최고", answerList.get(0).getContent());
	}



	@Test
	void 테스트데이터생성() {
		for(int i=1; i<=150; i++) {
			String title = String.format("테스트 데이터 생성:[%03d]", i);
			String content = "내용 없음";
			this.boardService.create(title, content, null);
		}
	}


}

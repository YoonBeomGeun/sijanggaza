package sijang.sijanggaza;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sijang.sijanggaza.domain.Board;
import sijang.sijanggaza.domain.SiteUser;
import sijang.sijanggaza.domain.UserStatus;
import sijang.sijanggaza.repository.BoardRepository;
import sijang.sijanggaza.repository.UserRepository;

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
	private UserRepository userRepository;

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

	@Test
	void 사용자수정() {
		Optional<SiteUser> os = this.userRepository.findById(1);
		assertTrue(os.isPresent());
		SiteUser user = os.get();
		user.setStatus(UserStatus.USER);
		this.userRepository.save(user);
	}

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

}

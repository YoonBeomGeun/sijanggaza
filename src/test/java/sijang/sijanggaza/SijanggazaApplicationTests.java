package sijang.sijanggaza;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sijang.sijanggaza.controller.BoardForm;
import sijang.sijanggaza.domain.*;
import sijang.sijanggaza.repository.*;
import sijang.sijanggaza.service.BoardService;
import sijang.sijanggaza.service.ItemService;
import sijang.sijanggaza.service.OrderService;
import sijang.sijanggaza.service.UserService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
	@Autowired
	private ItemService itemService;
	@Autowired
	private UserService userService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private ItemRepository itemRepository;

	@Test
	void 사용자등록() {
		SiteUser user1 = new SiteUser();
		user1.setUsername("111");
		user1.setPassword("111");
		user1.setEmail("beomgeun@naver.com");
		user1.setStatus(UserStatus.USER);
		this.userRepository.save(user1);
		assertEquals("111", user1.getUsername());

		SiteUser user2 = new SiteUser();
		user2.setUsername("222");
		user2.setPassword("222");
		user2.setEmail("bsd@dsfd");
		user2.setStatus(UserStatus.USER);
		this.userRepository.save(user2);
		assertEquals("222", user2.getUsername());

		SiteUser user3 = new SiteUser();
		user3.setUsername("333");
		user3.setPassword("333");
		user3.setEmail("ben@naveom");
		user3.setStatus(UserStatus.CEO);
		this.userRepository.save(user3);
		assertEquals("333", user3.getUsername());
	}

	/*@Test
	void 사용자수정() {
		Optional<SiteUser> os = this.userRepository.findById(1);
		assertTrue(os.isPresent());
		SiteUser user = os.get();
		user.setStatus(UserStatus.USER);
		this.userRepository.save(user);
	}*/

	/*@Test
	void 회원탈퇴() {

	}*/

	@Test
	void 게시글등록() {
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
	void 게시글전체삭제() {
		List<Board> all = this.boardRepository.findAll();
		for(int i=0; i<all.size(); i++) {
			Board board = all.get(i);
			this.boardRepository.delete(board);
		}
	}


	@Test
	void 댓글저장() {
		Optional<Board> ob = this.boardRepository.findById(1);
		assertTrue(ob.isPresent());
		Board b = ob.get();

		Comment c = new Comment();
		c.setContent("굳");
		c.setBoard(b);  // 어떤 질문의 답변인지 알기위해서 Question 객체가 필요하다.
		c.setPostDate(LocalDateTime.now());
		this.commentRepository.save(c);
	}

	@Test
	void 댓글단건조회() {
		Optional<Comment> oc = this.commentRepository.findById(12);
		assertTrue(oc.isPresent());
		Comment c = oc.get();
		assertEquals(190, c.getBoard().getId());
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
	void 페이징테스트데이터생성() {
		Optional<SiteUser> os = userRepository.findByusername("범근님");
		SiteUser user = os.get();
		for(int i=1; i<=20; i++) {
			String title = String.format("시장을 소개합니다.(부산)");
			String content = String.format("부산 시장입니다.:[%02d]", i);
			BoardForm b = new BoardForm();
			b.setTitle(title);
			b.setContent(content);
			this.boardService.create(user, b);
		}
	}

	//TODO ddabong 추가


	/*@Test
	@Transactional
	void 주문생성() {
		Optional<SiteUser> os = this.userRepository.findByusername("111");
		SiteUser siteUser = os.get();
		System.out.println(siteUser);
		Optional<Board> ob = this.boardRepository.findById(1);
		Board board = ob.get();
		System.out.println(board);
		Item item = this.itemRepository.findById(1);
		this.orderService.testCreate("111", item, 2);
	}*/


	@Test
	void 회원생성() {
		for(int i=1;i<=150;i++) {
			SiteUser user = new SiteUser();
			user.setUsername(String.format("%d%d%d", i,i,i));
			user.setPassword(String.format("%d%d%d", i,i,i));
			user.setEmail(String.format("user%d@naver.com", i));
			user.setStatus(UserStatus.USER);
			this.userRepository.save(user);
		}

	}


	/*// TODO ddabong 동시성 테스트
	@Test
	@Transactional
	void 따봉동시성테스트() throws InterruptedException {
		// 테스트할 보드 ID
		Board board = this.boardService.getBoard(30);
		// 동시에 실행할 스레드 수
		int threadCount = 6;
		// CountDownLatch를 사용하여 모든 스레드가 동작을 완료할 때까지 대기
		CountDownLatch latch = new CountDownLatch(threadCount);

		// 스레드 풀 생성
		ExecutorService executor = Executors.newFixedThreadPool(threadCount);

		// 여러 스레드 생성 및 실행
		for (int i = 1; i <= threadCount; i++) {
			Long finalI = (long)i;
			executor.submit(() -> {
				try {
					Optional<SiteUser> os = this.userRepository.findById(finalI);
					SiteUser user = os.get();
					// 각 스레드에서 보드를 추천하는 작업 수행
					boardService.ddabong(board, user);
				} finally {
					// 작업이 완료되면 CountDownLatch 카운트 감소
					latch.countDown();
				}
			});
		}

		// 모든 스레드가 작업을 완료할 때까지 대기
		latch.await();

		// 보드 추천 수를 확인하는 등의 검증 작업 수행
		int ddabongSize = boardService.ddabongSize(board);
		assertEquals(threadCount, ddabongSize);
	}*/

	@Test
	@Transactional
	public void 따봉_동시성_테스트() throws Exception {
	    //given
		long startTime = System.currentTimeMillis();
		Board board = boardService.getBoard(1);

		int thread = 100;
		ExecutorService executorService = Executors.newFixedThreadPool(32);
		CountDownLatch latch = new CountDownLatch(thread);

	    //when
		for(int i = 1; i <= thread; i++) {
			int userId = i;
			executorService.submit(() -> {
				try {
					SiteUser user = userService.findOne((long) userId);
					boardService.ddabong(board, user);
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();

	    //then
		Double sec = (System.currentTimeMillis() - startTime) / 1000.0;
		System.out.printf("thread 10개, 소요시간 --> (%.2f초)%n", sec);

		int ddabongSize = boardService.ddabongSize(board);
		assertEquals(thread, ddabongSize);
	}

	@Test
	public void 재고_감소() throws Exception {
	    //given
		long startTime = System.currentTimeMillis();
		Item item = itemRepository.findById(1);
		int beforeStock = item.getStockQuantity();
		int k = 3;
		//when
	    itemService.removeStockV1_5(item, k);

	    //then
		int restStock = item.getStockQuantity();

		Double sec = (System.currentTimeMillis() - startTime) / 1000.0;
		System.out.printf("소요시간 --> (%.2f초)%n", sec);
		assertEquals(beforeStock-k, restStock);

	}

	@Test
	public void 주문_동시성_테스트() throws Exception {
		//given
		long startTime = System.currentTimeMillis();

		Item item = itemRepository.findById(1);
		System.out.println(item);
		int beforeStock = item.getStockQuantity();
		int threadCount = 100;
		ExecutorService executorService = Executors.newFixedThreadPool(32);
		CountDownLatch latch = new CountDownLatch(threadCount);

		//when
		for (int i = 0; i < threadCount; i++) {
			executorService.submit(() -> {
				try {
					itemService.removeStockV2(item, 1);
				}
				finally {
					latch.countDown();
				}
			});
		}

		latch.await();

		//then

		Double sec = (System.currentTimeMillis() - startTime) / 1000.0;
		System.out.printf("thread 100개, 소요시간 --> (%.2f초)%n", sec);
		Item finalItem = this.itemRepository.findById(1).orElseThrow();

		assertEquals(beforeStock-threadCount, finalItem.getStockQuantity());
	}

	/*@Test
	public void 주문_동시성_테스트_낙관적락() throws Exception {
		//given
		long startTime = System.currentTimeMillis();
		Item item = itemRepository.findById(2);
		int threadCount = 100;
		ExecutorService executorService = Executors.newFixedThreadPool(32);
		CountDownLatch latch = new CountDownLatch(threadCount);

		//when
		for (int i = 0; i < threadCount; i++) {
			executorService.submit(() -> {
				try {
					itemService.removeStockUsingOptimisticLock(item.getId(), 1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();

		//then
		Double sec = (System.currentTimeMillis() - startTime) / 1000.0;
		System.out.printf("thread 100개, 소요시간 --> (%.2f초)%n", sec);
		Item finalItem = itemRepository.findById(2);
		assertEquals(900, finalItem.getStockQuantity());
	}


	@BeforeEach
	public void before() {
		Item item = this.itemRepository.findById(2);
		item.setIName("상품");
		item.setStockQuantity(1000);
		item.setPrice(1000);
		item.setVersion(0);
		itemRepository.save(item);
	}*/
}

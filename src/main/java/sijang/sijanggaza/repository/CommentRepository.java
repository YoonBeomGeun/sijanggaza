package sijang.sijanggaza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sijang.sijanggaza.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

}

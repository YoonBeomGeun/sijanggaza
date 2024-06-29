package sijang.sijanggaza.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoard is a Querydsl query type for Board
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoard extends EntityPathBase<Board> {

    private static final long serialVersionUID = 2105903931L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoard board = new QBoard("board");

    public final QSiteUser author;

    public final ListPath<Comment, QComment> commentList = this.<Comment, QComment>createList("commentList", Comment.class, QComment.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    public final SetPath<SiteUser, QSiteUser> ddabong = this.<SiteUser, QSiteUser>createSet("ddabong", SiteUser.class, QSiteUser.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<Item, QItem> itemList = this.<Item, QItem>createList("itemList", Item.class, QItem.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> postDate = createDateTime("postDate", java.time.LocalDateTime.class);

    public final StringPath title = createString("title");

    public QBoard(String variable) {
        this(Board.class, forVariable(variable), INITS);
    }

    public QBoard(Path<? extends Board> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoard(PathMetadata metadata, PathInits inits) {
        this(Board.class, metadata, inits);
    }

    public QBoard(Class<? extends Board> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.author = inits.isInitialized("author") ? new QSiteUser(forProperty("author")) : null;
    }

}


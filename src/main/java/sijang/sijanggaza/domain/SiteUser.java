package sijang.sijanggaza.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class SiteUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //원시타입인 int, long은 null 값을 가질 수 없음
    //Wrapper 클래스인 Integer와 Long은 null 가능(JPA에서 권장)

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private UserStatus status; //회원 분류(CEO, USER)

    @OneToMany(mappedBy = "siteUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orderList = new ArrayList<>();
}

package scheduleapp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter // 추가된 Setter
@AllArgsConstructor
public class Author {

    private Long id;
    private String name;
    private String email;
    private String password;
    private LocalDateTime created;
    private LocalDateTime updated;

    // 이름, 이메일, 비밀번호만 사용하는 생성자
    public Author(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}


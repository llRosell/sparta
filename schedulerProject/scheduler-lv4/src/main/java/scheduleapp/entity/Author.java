package scheduleapp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Author
 *
 * 작성자 정보를 표현하는 Entity 클래스입니다.
 * 데이터베이스 테이블의 레코드를 객체로 매핑하여 사용합니다.
 */
@Getter
@Setter
@AllArgsConstructor
public class Author {

    private Long id; // 작성자 ID
    private String name; // 작성자 이름
    private String email; // 작성자 이메일
    private String password; // 작성자 비밀번호
    private LocalDateTime created; // 작성자 정보 생성 시간
    private LocalDateTime updated; // 작성자 정보 수정 시간

    /**
     * Author 생성자 (이름, 이메일, 비밀번호만 초기화).
     *
     * @param name 작성자 이름
     * @param email 작성자 이메일
     * @param password 작성자 비밀번호
     */
    public Author(String name, String email, String password) {
        this.name = name;       // 작성자 이름 설정
        this.email = email;     // 작성자 이메일 설정
        this.password = password; // 작성자 비밀번호 설정
    }
}

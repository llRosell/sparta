package scheduleapp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Task
 *
 * 할 일 정보를 표현하는 Entity 클래스입니다.
 * 데이터베이스 테이블의 레코드를 객체로 매핑하여 사용합니다.
 */
@Getter
@Setter
@AllArgsConstructor
public class Task {

    private Long id; // 할 일 ID
    private String todo; // 할 일 내용
    private String password; // 작성자 비밀번호
    private Long authorId; // 작성자 ID

    /**
     * 할 일 생성자 (내용과 작성자 ID만 초기화).
     *
     * @param todo 할 일 내용
     * @param authorId 작성자 ID
     */
    public Task(String todo, Long authorId) {
        this.todo = todo;       // 할 일 내용 설정
        this.authorId = authorId; // 작성자 ID 설정
    }

    /**
     * 기본 생성자.
     * 빈 객체를 생성할 때 사용됩니다.
     */
    public Task() {
    }
}

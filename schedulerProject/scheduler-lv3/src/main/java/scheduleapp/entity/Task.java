package scheduleapp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter // 추가된 Setter
@AllArgsConstructor
public class Task {

    private Long id;
    private String todo;
    private String password;
    private Long authorId;


    public Task(String todo, Long authorId) {
        this.todo = todo;
        this.authorId = authorId;
    }

        // 기본 생성자
    public Task() {
    }
}


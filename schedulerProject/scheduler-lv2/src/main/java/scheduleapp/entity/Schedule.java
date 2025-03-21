package scheduleapp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter // 추가된 Setter
@AllArgsConstructor
public class Schedule {

    private Long id;
    private String todo;
    private String name;
    private String password;
    private LocalDateTime created;
    private LocalDateTime updated;

    // 비밀번호 없이 사용하는 경우
    public Schedule(String todo, String name, String password) {
        this.todo = todo;
        this.name = name;
        this.password = password;
    }
}

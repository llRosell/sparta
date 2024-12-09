package scheduleapp.dto;

import lombok.Getter;

@Getter
public class AuthorRequestDto {
    private String name;     // 작성자 이름
    private String email;    // 작성자 이메일
    private String password; // 작성자 비밀번호
}

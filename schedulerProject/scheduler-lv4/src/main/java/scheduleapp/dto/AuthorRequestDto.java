package scheduleapp.dto;

import lombok.Getter;

/**
 * AuthorRequestDto
 *
 * 작성자 관련 요청에서 필요한 데이터를 담는 DTO(Data Transfer Object) 클래스입니다.
 */
@Getter
public class AuthorRequestDto {

    private String name; // 작성자 이름
    private String email; // 작성자 이메일

    /**
     * 작성자 비밀번호.
     * 작성자의 계정 보호를 위한 비밀번호를 저장하는 필드입니다.
     * 해당 필드는 보안상 외부로 노출되지 않아야 합니다.
     */
    private String password;
}

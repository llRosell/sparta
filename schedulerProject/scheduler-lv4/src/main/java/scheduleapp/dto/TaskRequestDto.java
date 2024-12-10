package scheduleapp.dto;

import lombok.Getter;

/**
 * TaskRequestDto
 *
 * 할 일 생성 및 수정 요청 데이터를 담는 DTO 클래스입니다.
 */
@Getter
public class TaskRequestDto {

    private String todo; // 할 일 내용
    private Long authorId; // 작성자 ID

    /**
     * 작성자 비밀번호.
     * 작성자의 계정 보호를 위한 비밀번호를 저장하는 필드입니다.
     * 해당 필드는 보안상 외부로 노출되지 않아야 합니다.
     */
    private String password; // 작성자 비밀번호
}

package scheduleapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import scheduleapp.entity.Author;

import java.time.LocalDateTime;

/**
 * AuthorResponseDto
 *
 * 작성자 관련 응답 데이터를 담는 DTO(Data Transfer Object) 클래스입니다.
 */
@Getter
@AllArgsConstructor
public class AuthorResponseDto {

    private Long id; // 작성자 ID
    private String name; // 작성자 이름
    private String email; // 작성자 이메일
    private LocalDateTime created; // 작성자 정보 생성 시간
    private LocalDateTime updated; // 작성자 정보 수정 시간

    /**
     * Author 엔티티를 AuthorResponseDto로 변환하는 생성자.
     *
     * @param author Author 엔티티 객체
     *               - Entity의 데이터를 기반으로 DTO 필드에 값을 설정합니다.
     */
    public AuthorResponseDto(Author author) {
        this.id = author.getId();          // 작성자 ID 설정
        this.name = author.getName();      // 작성자 이름 설정
        this.email = author.getEmail();    // 작성자 이메일 설정
        this.created = author.getCreated(); // 작성자 생성 시간 설정
        this.updated = author.getUpdated(); // 작성자 수정 시간 설정
    }
}

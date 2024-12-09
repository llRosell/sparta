package scheduleapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import scheduleapp.entity.Author;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AuthorResponseDto {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime created;
    private LocalDateTime updated;

    // Entity를 DTO로 변환하는 생성자
    public AuthorResponseDto(Author author) {
        this.id = author.getId();
        this.name = author.getName();
        this.email = author.getEmail();
        this.created = author.getCreated();
        this.updated = author.getUpdated();
    }
}

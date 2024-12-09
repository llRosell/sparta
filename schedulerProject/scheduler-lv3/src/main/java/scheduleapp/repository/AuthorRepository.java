package scheduleapp.repository;

import scheduleapp.dto.AuthorResponseDto;
import scheduleapp.entity.Author;

import java.util.Optional;

public interface AuthorRepository {
    // 작성자 저장
    AuthorResponseDto saveAuthor(Author author);

    // 작성자 조회 (ID, 비밀번호 검증 조회)
    Optional<Author> getAuthor(Long id);

    // 작성자 정보 수정 (이름, 이메일/ ID, 비밀번호 검증 조회))
    int updateAuthor(Author author);

    // 작성자 삭제 (ID, 비밀번호 검증 조회)
    void deleteAuthor(Long id);

    // 작성자 존재여부 확인
    boolean existsById(Long id);

    String findPasswordById(Long authorId);
}

package scheduleapp.repository;

import scheduleapp.dto.AuthorResponseDto;
import scheduleapp.entity.Author;

import java.util.Optional;

/**
 * AuthorRepository
 *
 * 작성자 데이터를 관리하기 위한 저장소 인터페이스입니다.
 * 작성자 데이터를 저장, 조회, 수정, 삭제하는 메서드를 정의합니다.
 */
public interface AuthorRepository {

    AuthorResponseDto saveAuthor(Author author); // 작성자 정보 저장
    Optional<Author> getAuthor(Long id); // 작성자 정보 조회
    int updateAuthor(Author author); // 작성자 정보 수정
    void deleteAuthor(Long id); // 작성자 정보 삭제
    boolean existsById(Long id); // 작성자 존재여부 확인
    String findPasswordById(Long authorId); // 작성자 비밀번호 조회
}

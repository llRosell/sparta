package scheduleapp.service;

import scheduleapp.dto.AuthorRequestDto;
import scheduleapp.dto.AuthorResponseDto;

/**
 * AuthorService 인터페이스
 *
 * 작성자(Author)에 대한 비즈니스 로직을 정의합니다.
 * 작성자 정보를 저장, 조회, 수정 및 삭제하는 기능을 제공합니다.
 */
public interface AuthorService {

    AuthorResponseDto saveAuthor(AuthorRequestDto dto); // 작성자 정보 저장
    AuthorResponseDto getAuthor(Long id, String password);  // 작성자 정보 조회 수정
    AuthorResponseDto updateAuthor(Long id, AuthorRequestDto dto, String password); // 작성자 정보 수정
    void deleteAuthor(Long id, String password); // 작성자 정보 삭제
}

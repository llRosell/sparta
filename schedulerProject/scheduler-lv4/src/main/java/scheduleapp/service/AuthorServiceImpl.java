package scheduleapp.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import scheduleapp.dto.AuthorRequestDto;
import scheduleapp.dto.AuthorResponseDto;
import scheduleapp.entity.Author;
import scheduleapp.repository.AuthorRepository;

import java.util.Optional;

/**
 * AuthorServiceImpl 클래스
 *
 * 작성자(Author) 관련 비즈니스 로직을 구현한 클래스입니다.
 * 작성자를 저장, 조회, 수정 및 삭제하는 기능을 제공합니다.
 */
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository; // 작성자 레포지토리

    // 생성자 주입을 통해 AuthorRepository를 주입받습니다.
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorResponseDto saveAuthor(AuthorRequestDto dto) {
        // Author 객체 생성
        Author author = new Author(dto.getName(), dto.getEmail(), dto.getPassword());
        return authorRepository.saveAuthor(author); // Author 저장
    }

    @Override
    public AuthorResponseDto getAuthor(Long id, String password) {
        Optional<Author> optionalAuthor = authorRepository.getAuthor(id);

        // 작성자를 찾지 못한 경우 예외 발생
        if (optionalAuthor.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "작성자를 찾을 수 없습니다.");
        }

        Author author = optionalAuthor.get();

        // 비밀번호 확인
        if (!author.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호가 일치하지 않습니다.");
        }

        return new AuthorResponseDto(author); // AuthorResponseDto 반환
    }

    @Transactional // 트랜잭션을 사용하여 데이터베이스 변경 관리
    @Override
    public AuthorResponseDto updateAuthor(Long id, AuthorRequestDto dto, String password) {
        Optional<Author> optionalAuthor = authorRepository.getAuthor(id);

        // 작성자를 찾지 못한 경우 예외 발생
        if (optionalAuthor.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "작성자를 찾을 수 없습니다.");
        }

        Author author = optionalAuthor.get();

        // 비밀번호 검증
        if (!verifyPassword(author.getId(), password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        // Author 필드 업데이트
        author.setName(dto.getName());
        author.setEmail(dto.getEmail());

        // 데이터베이스에 업데이트 반영
        authorRepository.updateAuthor(author);

        // 업데이트된 AuthorResponseDto 반환
        return new AuthorResponseDto(author);
    }

    @Transactional // 트랜잭션을 사용하여 데이터베이스 변경 관리
    @Override
    public void deleteAuthor(Long id, String password) { // 매개변수 수정
        Optional<Author> optionalAuthor = authorRepository.getAuthor(id);

        // 작성자를 찾지 못한 경우 예외 발생
        if (optionalAuthor.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "작성자를 찾을 수 없습니다.");
        }

        // Author 삭제
        authorRepository.deleteAuthor(id);
    }

    // 비밀번호 검증 메서드
    private boolean verifyPassword(Long authorId, String password) {
        // 작성자의 비밀번호를 데이터베이스에서 가져옴
        String storedPassword = authorRepository.findPasswordById(authorId);

        // 비밀번호가 일치하는지 확인
        return storedPassword != null && storedPassword.equals(password);
    }
}

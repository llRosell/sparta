package scheduleapp.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import scheduleapp.entity.Author;
import scheduleapp.dto.AuthorResponseDto;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * JdbcTemplateAuthorRepository
 *
 * 작성자 데이터를 데이터베이스와 상호작용하여 처리하는 JDBC 기반의 구현 클래스입니다.
 */
@Repository
public class JdbcTemplateAuthorRepository implements AuthorRepository {

    private final JdbcTemplate jdbcTemplate; // JDBC 작업을 수행하기 위한 도구
    private final SimpleJdbcInsert jdbcInsert; // 데이터 삽입을 단순화하기 위한 도구

    /**
     * 데이터 소스를 주입받아 JdbcTemplate과 SimpleJdbcInsert를 초기화합니다.
     *
     * @param dataSource 데이터 소스 (DB 연결 정보)
     */
    public JdbcTemplateAuthorRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("author") // 대상 테이블명
                .usingGeneratedKeyColumns("id"); // 자동 생성되는 키 컬럼 지정
    }

    /**
     * 작성자 데이터를 저장합니다.
     *
     * @param author 저장할 작성자 객체
     * @return 저장된 작성자의 응답 DTO
     */
    @Override
    public AuthorResponseDto saveAuthor(Author author) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", author.getName()); // 작성자 이름
        parameters.put("email", author.getEmail()); // 작성자 이메일
        parameters.put("password", author.getPassword()); // 작성자 비밀번호
        parameters.put("created", LocalDateTime.now()); // 생성 시각
        parameters.put("updated", LocalDateTime.now()); // 수정 시각

        // 데이터를 삽입하고 생성된 ID를 반환받음
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        // 응답 DTO 생성 및 반환
        return new AuthorResponseDto(
                key.longValue(),
                author.getName(),
                author.getEmail(),
                author.getCreated(),
                author.getUpdated()
        );
    }

    /**
     * ID로 작성자를 조회합니다.
     *
     * @param id 조회할 작성자의 ID
     * @return 조회된 작성자 객체 (Optional로 감싸 반환)
     */
    @Override
    public Optional<Author> getAuthor(Long id) {
        String query = "SELECT * FROM author WHERE id = ?";
        List<Author> result = jdbcTemplate.query(query, authorRowMapper(), id);
        return result.stream().findAny(); // 결과 중 하나를 반환 (없으면 빈 Optional)
    }

    /**
     * 작성자 데이터를 수정합니다.
     *
     * @param author 수정할 작성자 객체
     * @return 수정된 행의 수
     */
    @Override
    public int updateAuthor(Author author) {
        return jdbcTemplate.update("UPDATE author SET name = ?, email = ?, updated = ? WHERE id = ?",
                author.getName(),       // 수정할 이름
                author.getEmail(),      // 수정할 이메일
                LocalDateTime.now(),    // 수정 시각
                author.getId()          // 대상 작성자 ID
        );
    }

    /**
     * 작성자 데이터를 삭제합니다.
     *
     * @param id 삭제할 작성자의 ID
     */
    @Override
    public void deleteAuthor(Long id) {
        jdbcTemplate.update("DELETE FROM author WHERE id = ?", id);
    }

    /**
     * 작성자의 존재 여부를 확인합니다.
     *
     * @param id 확인할 작성자의 ID
     * @return 작성자가 존재하면 true, 그렇지 않으면 false
     */
    @Override
    public boolean existsById(Long id) {
        String query = "SELECT COUNT(*) FROM author WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(query, Integer.class, id);
        return count != null && count > 0; // 0보다 크면 존재
    }

    /**
     * 작성자의 비밀번호를 조회합니다.
     *
     * @param authorId 조회할 작성자의 ID
     * @return 작성자의 비밀번호
     */
    public String findPasswordById(Long authorId) {
        String sql = "SELECT password FROM author WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, authorId);
    }

    /**
     * 작성자를 데이터베이스의 레코드와 매핑하는 RowMapper를 생성합니다.
     *
     * @return 작성자 객체를 생성하는 RowMapper
     */
    private RowMapper<Author> authorRowMapper() {
        return (rs, rowNum) -> new Author(
                rs.getLong("id"),                          // 작성자 ID
                rs.getString("name"),                      // 작성자 이름
                rs.getString("email"),                     // 작성자 이메일
                rs.getString("password"),                  // 작성자 비밀번호
                rs.getTimestamp("created").toLocalDateTime(), // 생성 시각
                rs.getTimestamp("updated").toLocalDateTime()  // 수정 시각
        );
    }
}

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

@Repository
public class JdbcTemplateAuthorRepository implements AuthorRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcTemplateAuthorRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("author")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public AuthorResponseDto saveAuthor(Author author) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", author.getName());
        parameters.put("email", author.getEmail());
        parameters.put("password", author.getPassword());
        parameters.put("created", LocalDateTime.now());
        parameters.put("updated", LocalDateTime.now());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new AuthorResponseDto(
                key.longValue(),
                author.getName(),
                author.getEmail(),
                author.getCreated(),
                author.getUpdated()
        );
    }

    @Override
    public Optional<Author> getAuthor(Long id) {
        String query = "SELECT * FROM author WHERE id = ?";
        List<Author> result = jdbcTemplate.query(query, authorRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public int updateAuthor(Author author) {
        return jdbcTemplate.update("UPDATE author SET name = ?, email = ?, updated = ? WHERE id = ?",
                author.getName(),
                author.getEmail(),
                LocalDateTime.now(),
                author.getId()
        );
    }

    @Override
    public void deleteAuthor(Long id) {
        jdbcTemplate.update("DELETE FROM author WHERE id = ?", id);
    }

    private RowMapper<Author> authorRowMapper() {
        return (rs, rowNum) -> new Author(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getTimestamp("created").toLocalDateTime(),
                rs.getTimestamp("updated").toLocalDateTime()
        );
    }

    @Override
    public boolean existsById(Long id) {
        String query = "SELECT COUNT(*) FROM author WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(query, Integer.class, id);
        return count != null && count > 0;
    }

    public String findPasswordById(Long authorId) {
        String sql = "SELECT password FROM author WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, authorId); // 간소화된 쿼리
    }
}

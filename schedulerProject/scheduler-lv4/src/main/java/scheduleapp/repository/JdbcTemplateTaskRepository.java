package scheduleapp.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import scheduleapp.dto.TaskResponseDto;
import scheduleapp.entity.Task;
import scheduleapp.entity.Author;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcTemplateTaskRepository implements TaskRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final AuthorRepository authorRepository;

    public JdbcTemplateTaskRepository(DataSource dataSource, AuthorRepository authorRepository) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.authorRepository = authorRepository;
    }

        @Override
    public TaskResponseDto saveTask(Task task) {

        // Author의 비밀번호를 가져오기 위해 AuthorRepository를 주입받습니다.
        Author author = authorRepository.getAuthor(task.getAuthorId()).orElseThrow(() ->
                new RuntimeException("작성자를 찾을 수 없습니다."));

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName("task")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("todo", task.getTodo());
        parameters.put("author_id", task.getAuthorId());
        parameters.put("password", task.getPassword());


        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new TaskResponseDto(
                key.longValue(),
                task.getTodo(),
                task.getAuthorId()
        );
    }

    @Override
    public Optional<Task> getTask(Long id) {
        String query = "SELECT * FROM task WHERE id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id); // 파라미터를 MapSqlParameterSource에 추가

        List<Task> result = namedParameterJdbcTemplate.query(query, params, taskRowMapper());
        return result.stream().findAny();
    }

    @Override
    public List<Task> getAllTasks(Long authorId, int page, int size) {
        int offset = (page - 1) * size;  // OFFSET 계산

        // 쿼리와 파라미터를 배열로 전달
        String allTasksQuery = "SELECT * FROM task WHERE author_id = :authorId ORDER BY id ASC LIMIT :size OFFSET :offset";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("authorId", authorId);
        params.addValue("size", size);
        params.addValue("offset", offset);

        // 데이터 조회
        return namedParameterJdbcTemplate.query(allTasksQuery, params, taskRowMapper());
        }

    @Override
    public int updateTask(Task task) {
        String query = "UPDATE task SET todo = :todo WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("todo", task.getTodo());
        params.addValue("id", task.getId());

        return namedParameterJdbcTemplate.update(query, params);
    }

    @Override
    public void deleteTask(Long id) {
        String query = "DELETE FROM task WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        namedParameterJdbcTemplate.update(query, params);
    }

    private RowMapper<Task> taskRowMapper() {
        return (rs, rowNum) -> new Task(
                rs.getLong("id"),
                rs.getString("todo"),
                rs.getString("password"),
                rs.getLong("author_id")
        );
    }
}

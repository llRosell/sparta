package scheduleapp.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
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

    private final JdbcTemplate jdbcTemplate;
    private final AuthorRepository authorRepository;

    public JdbcTemplateTaskRepository(DataSource dataSource, AuthorRepository authorRepository) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.authorRepository = authorRepository;
    }

        @Override
    public TaskResponseDto saveTask(Task task) {

        // Author의 비밀번호를 가져오기 위해 AuthorRepository를 주입받습니다.
        Author author = authorRepository.getAuthor(task.getAuthorId()).orElseThrow(() ->
                new RuntimeException("작성자를 찾을 수 없습니다."));

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
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
        String query = "SELECT * FROM task WHERE id = ?";
        List<Task> result = jdbcTemplate.query(query, taskRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public List<Task> getAllTasks() {
        String query = "SELECT * FROM task ORDER BY id ASC";
        return jdbcTemplate.query(query, taskRowMapper());
    }

    @Override
    public int updateTask(Task task) {
        return jdbcTemplate.update("UPDATE task SET todo = ? WHERE id = ?",
                task.getTodo(),
                task.getId()
        );
    }

    @Override
    public void deleteTask(Long id) {
        jdbcTemplate.update("DELETE FROM task WHERE id = ?", id);
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

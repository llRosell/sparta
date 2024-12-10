package scheduleapp.repository;

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

/**
 * JdbcTemplateTaskRepository
 *
 * 작업(Task) 데이터를 데이터베이스와 상호작용하여 처리하는 JDBC 기반의 구현 클래스입니다.
 */
@Repository
public class JdbcTemplateTaskRepository implements TaskRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate; // 명명된 파라미터를 지원하는 JDBC 템플릿
    private final AuthorRepository authorRepository; // 작성자 정보를 조회하기 위한 저장소

    /**
     * 데이터 소스와 AuthorRepository를 주입받아 JdbcTemplate을 초기화합니다.
     *
     * @param dataSource 데이터 소스 (DB 연결 정보)
     * @param authorRepository 작성자 정보를 처리하기 위한 저장소
     */
    public JdbcTemplateTaskRepository(DataSource dataSource, AuthorRepository authorRepository) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.authorRepository = authorRepository;
    }

    /**
     * 작업(Task) 데이터를 저장합니다.
     *
     * @param task 저장할 작업 객체
     * @return 저장된 작업의 응답 DTO
     */
    @Override
    public TaskResponseDto saveTask(Task task) {
        // Author의 비밀번호를 가져오기 위해 AuthorRepository를 통해 작성자를 조회합니다.
        Author author = authorRepository.getAuthor(task.getAuthorId()).orElseThrow(() ->
                new RuntimeException("작성자를 찾을 수 없습니다."));

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName("task") // 대상 테이블명
                .usingGeneratedKeyColumns("id"); // 자동 생성되는 키 컬럼 지정

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("todo", task.getTodo()); // 작업 제목
        parameters.put("author_id", task.getAuthorId()); // 작성자 ID
        parameters.put("password", task.getPassword()); // 작성자 비밀번호

        // 데이터를 삽입하고 생성된 ID를 반환받음
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        // 응답 DTO 생성 및 반환
        return new TaskResponseDto(
                key.longValue(), // 생성된 작업 ID
                task.getTodo(),  // 작업 제목
                task.getAuthorId() // 작성자 ID
        );
    }

    /**
     * ID로 작업을 조회합니다.
     *
     * @param id 조회할 작업의 ID
     * @return 조회된 작업 객체 (Optional로 감싸 반환)
     */
    @Override
    public Optional<Task> getTask(Long id) {
        String query = "SELECT * FROM task WHERE id = :id"; // 작업 ID로 조회하는 쿼리

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id); // 파라미터를 MapSqlParameterSource에 추가

        List<Task> result = namedParameterJdbcTemplate.query(query, params, taskRowMapper());
        return result.stream().findAny(); // 결과 중 하나를 반환 (없으면 빈 Optional)
    }

    /**
     * 특정 작성자의 모든 작업을 페이지 단위로 조회합니다.
     *
     * @param authorId 작성자의 ID
     * @param page 페이지 번호
     * @param size 한 페이지에 포함할 작업 수
     * @return 조회된 작업 목록
     */
    @Override
    public List<Task> getAllTasks(Long authorId, int page, int size) {
        int offset = (page - 1) * size;  // OFFSET 계산

        // 작성자의 모든 작업을 조회하는 쿼리
        String allTasksQuery = "SELECT * FROM task WHERE author_id = :authorId ORDER BY id ASC LIMIT :size OFFSET :offset";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("authorId", authorId); // 작성자 ID
        params.addValue("size", size); // 페이지 크기
        params.addValue("offset", offset); // OFFSET

        // 데이터 조회
        return namedParameterJdbcTemplate.query(allTasksQuery, params, taskRowMapper());
    }

    /**
     * 작업(Task) 데이터를 수정합니다.
     *
     * @param task 수정할 작업 객체
     * @return 수정된 행의 수
     */
    @Override
    public int updateTask(Task task) {
        String query = "UPDATE task SET todo = :todo WHERE id = :id"; // 작업 제목을 수정하는 쿼리
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("todo", task.getTodo()); // 수정할 작업 제목
        params.addValue("id", task.getId()); // 대상 작업 ID

        return namedParameterJdbcTemplate.update(query, params); // 수정된 행의 수 반환
    }

    /**
     * 작업(Task) 데이터를 삭제합니다.
     *
     * @param id 삭제할 작업의 ID
     */
    @Override
    public void deleteTask(Long id) {
        String query = "DELETE FROM task WHERE id = :id"; // 작업 ID로 삭제하는 쿼리
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id); // 파라미터 추가

        namedParameterJdbcTemplate.update(query, params); // 작업 삭제
    }

    /**
     * 데이터베이스의 레코드를 Task 객체와 매핑하는 RowMapper를 생성합니다.
     *
     * @return Task 객체를 생성하는 RowMapper
     */
    private RowMapper<Task> taskRowMapper() {
        return (rs, rowNum) -> new Task(
                rs.getLong("id"),           // 작업 ID
                rs.getString("todo"),       // 작업 제목
                rs.getString("password"),    // 작성자 비밀번호
                rs.getLong("author_id")     // 작성자 ID
        );
    }
}

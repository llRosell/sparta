package scheduleapp.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import scheduleapp.dto.ScheduleResponseDto;
import scheduleapp.entity.Schedule;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateScheduleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("schedule")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("todo", schedule.getTodo());
        parameters.put("name", schedule.getName());
        parameters.put("password", schedule.getPassword());
        parameters.put("created", LocalDateTime.now());
        parameters.put("updated", LocalDateTime.now());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new ScheduleResponseDto(
                key.longValue(),
                schedule.getTodo(),
                schedule.getName(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    @Override
    public List<ScheduleResponseDto> getAllSchedules() {
        String query = "SELECT * FROM schedule ORDER BY updated DESC";
        return jdbcTemplate.query(query, scheduleRowMapper());
    }

    @Override
    public Optional<ScheduleResponseDto> getScheduleById(Long id) {
        String query = "SELECT * FROM schedule WHERE id = ?";
        List<ScheduleResponseDto> result = jdbcTemplate.query(query, scheduleRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public Optional<Schedule> getScheduleByIdEntity(Long id) {
        String query = "SELECT * FROM schedule WHERE id = ?";
        List<Schedule> result = jdbcTemplate.query(query, scheduleEntityRowMapper(), id);
        return result.stream().findAny(); // Schedule 반환
    }

    @Override
    public int updateSchedule(Schedule schedule) {
        return jdbcTemplate.update("UPDATE schedule SET todo = ?, name = ?, updated = ? WHERE id = ?",
                schedule.getTodo(),
                schedule.getName(),
                LocalDateTime.now(),
                schedule.getId()
        );
    }

    @Override
    public void deleteSchedule(Long id) {
        // 주어진 ID로 일정을 삭제하는 SQL 쿼리
        jdbcTemplate.update("DELETE FROM schedule WHERE id = ?", id);
    }

    private RowMapper<ScheduleResponseDto> scheduleRowMapper() {
        return (rs, rowNum) -> new ScheduleResponseDto(
                rs.getLong("id"),
                rs.getString("todo"),
                rs.getString("name"),
                rs.getTimestamp("created").toLocalDateTime(),
                rs.getTimestamp("updated").toLocalDateTime()
        );
    }

    private RowMapper<Schedule> scheduleEntityRowMapper() {
        return (rs, rowNum) -> new Schedule(
                rs.getLong("id"),
                rs.getString("todo"),
                rs.getString("name"),
                rs.getString("password"), // 비밀번호
                rs.getTimestamp("created").toLocalDateTime(),
                rs.getTimestamp("updated").toLocalDateTime()
        );
    }
}

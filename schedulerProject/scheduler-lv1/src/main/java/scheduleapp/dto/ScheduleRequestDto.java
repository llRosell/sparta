package scheduleapp.dto;

import lombok.Getter;

// Schedule 클래스에 있는 데이터를 작성하기 위해 클라이언트에게 전달받아야 하는 것들.
// id는 서버에서 관리함.
// 클라이언트에게 전달받을 때 ScheduleRequestDto로 받는다.
@Getter
public class ScheduleRequestDto {

    private String todo;
    private String name;
    private String password;

}

package pe.com.utp.service.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HeaderRequest {
    String authorization;
    String correlatorId;
    String messageId;
    String requestDate;
    String originSystem;
    String targetSystem;
    String country;
}
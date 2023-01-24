package bloodcenter.api_key;

import lombok.*;
import org.springframework.dao.DuplicateKeyException;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Getter
public class Key {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    private String email;
    private String key;

    public Key(String email, String key) {
        this.email = email;
        this.key = key;
    }

    public static class ApiKeyAlreadyExistsException extends Exception {
        public ApiKeyAlreadyExistsException(String msg) { super(msg); }
    }

    public static class InvalidKeyException extends  Exception {
        public InvalidKeyException(String msg) { super(msg); }
    }
}

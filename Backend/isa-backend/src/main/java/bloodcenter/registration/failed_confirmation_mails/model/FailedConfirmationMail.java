package bloodcenter.registration.failed_confirmation_mails.model;

import bloodcenter.person.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "failed_confirmation_mails")
public class FailedConfirmationMail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "recipient_id"
    )
    private User recipient;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public FailedConfirmationMail(User recipient, LocalDateTime createdAt) {
        this.recipient = recipient;
        this.createdAt = createdAt;
    }
}

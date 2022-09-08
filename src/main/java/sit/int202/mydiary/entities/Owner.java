package sit.int202.mydiary.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor()
@Entity
@Table(name = "owners")
public class Owner {
    @Id
    @NonNull
    @Column(name = "username", nullable = false, length = 30)
    private String userName;

    @NonNull
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @NonNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_login")
    private Date lastLogin;

    @NonNull
    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    @NonNull
    @Column(name = "updated_date", nullable = false)
    private Date updatedDate;

    @OneToMany(mappedBy = "ownerUsername", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Diary> diaries ;
}
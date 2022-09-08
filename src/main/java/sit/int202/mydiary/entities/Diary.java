package sit.int202.mydiary.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor()
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "diaries")
@NamedQueries({ @NamedQuery(name = "Diary.ShowAllByOwner", query = "select d from Diary d where d.ownerUsername = :param order by d.diaryDate desc"), })
public class Diary {
    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_username")
    private Owner ownerUsername;

    @NonNull
    @Temporal(TemporalType.DATE)
    @Column(name = "diary_date", nullable = false)
    private Date diaryDate;

    @NonNull
    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @NonNull
    @Lob
    @Column(name = "body", nullable = false)
    private String body;

    @NonNull
    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    @NonNull
    @Column(name = "updated_date", nullable = false)
    private Date updatedDate;

}
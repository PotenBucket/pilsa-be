package potenday.pilsa.pilsa.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "pilsaCategory")
public class PilsaCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryCd;

    // 카테고리 명
    @Column
    private String categoryName;

    // 설명
    @Column
    private String description;

    // 사용 여부
    @Enumerated(value = EnumType.STRING)
    @Column
    private YN useYn;

    private LocalDateTime registDate;

    private LocalDateTime updateDate;
}

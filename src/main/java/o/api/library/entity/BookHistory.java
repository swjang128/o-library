package o.api.library.entity;

import jakarta.persistence.*;
import jdk.jfr.Description;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import o.api.library.domain.BookHistoryType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BookHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bookId;

    @Column(nullable = false, length = 60)
    private String title;

    @Column(nullable = false, length = 13)
    private String isbn;

    private Long checkoutId;

    @Enumerated(EnumType.STRING)
    private BookHistoryType bookHistoryType;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;
}
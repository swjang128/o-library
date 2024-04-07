package o.api.library.entity;

import jakarta.persistence.*;
import jdk.jfr.Description;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    private String title;

    @Column(nullable = false, length = 13)
    private String isbn;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private int price;

    private Long consignerId;

    private Long checkoutId;

    @Column(columnDefinition = "boolean default false")
    private boolean status;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private int checkoutCount;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @Description("checkout_id를 변경하고, checkout_count + 1, status = false로 변경")
    public void setCheckOutAndIncreaseCount(Long checkoutId) {
        this.checkoutId = checkoutId;
        this.status = false;
        this.checkoutCount++;
    }
}
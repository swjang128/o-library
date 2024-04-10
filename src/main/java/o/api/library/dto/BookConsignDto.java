package o.api.library.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import jdk.jfr.Description;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import o.api.library.entity.Book;
import o.api.library.entity.BookHistory;

import static o.api.library.domain.BookHistoryType.CONSIGN;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookConsignDto {
    @Size(min = 1,  max = 60)
    @Schema(description = "도서 제목", defaultValue = "사피엔스")
    private String title;

    @Pattern(regexp = "\\d{13}")
    @Schema(description = "isbn", defaultValue = "9780062316097")
    private String isbn;

    @PositiveOrZero
    @Schema(description = "대여료", defaultValue = "5000")
    private int price;

    @Schema(description = "Consigner's ID", defaultValue = "1")
    @Positive
    private Long consignerId;

    @Pattern(regexp = "[01]")
    @Schema(description = "대여 가능 여부", defaultValue = "true")
    private boolean status;

    @Description("도서 위탁에 필요한 객체")
    public Book consign() {
        return Book.builder()
                .consignerId(consignerId)
                .title(title)
                .isbn(isbn)
                .price(price)
                .status(status)
                .build();
    }

    public BookHistory historyByConsign(Book book) {
        return BookHistory.builder()
                .bookId(book.getId())
                .checkoutId(null)
                .title(title)
                .isbn(isbn)
                .bookHistoryType(CONSIGN)
                .build();
    }
}

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
import o.api.library.entity.Member;

import static o.api.library.domain.BookHistoryType.CONSIGN;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookConsignDto {
    @Size(min = 1,  max = 60)
    private String title;

    @Pattern(regexp = "\\d{13}")
    private String isbn;

    @PositiveOrZero
    private int price;

    @Schema(description = "Consigner's ID")
    @Positive
    private Long consignerId;

    @Pattern(regexp = "[01]")
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

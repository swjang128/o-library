package o.api.library.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import jdk.jfr.Description;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import o.api.library.entity.Book;
import o.api.library.entity.BookHistory;

import static o.api.library.domain.BookHistoryType.CHECKOUT;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookCheckoutDto {
    private Long id;

    @Schema(description = "Checkout ID")
    @Positive
    private Long checkoutId;

    @Description("도서 대여 내역 저장에 필요한 객체")
    public BookHistory historyByCheckout(Book book, Long checkoutId) {
        return BookHistory.builder()
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .bookId(book.getId())
                .checkoutId(checkoutId)
                .bookHistoryType(CHECKOUT)
                .build();
    }
}

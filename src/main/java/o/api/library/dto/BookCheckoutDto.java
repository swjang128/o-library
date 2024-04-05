package o.api.library.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import jdk.jfr.Description;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import o.api.library.domain.BookSortingMethod;
import o.api.library.entity.Book;
import o.api.library.entity.BookHistory;
import o.api.library.entity.Member;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookCheckoutDto {
    private Long id;

    @Schema(description = "Checkout ID")
    @Positive
    private Long checkoutId;

    @Description("도서 대여한 내용을 테이블에 수정하는 엔티티 객체 생성")
    public Book checkout() {
        return Book.builder()
                .id(id)
                .checkoutId(checkoutId)
                .build();
    }

    @Description("도서 대여 내역 저장에 필요한 객체")
    public BookHistory historyByCheckout(Book book, Long checkoutId) {
        return BookHistory.builder()
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .bookId(book.getId())
                .checkoutId(checkoutId)
                .build();
    }
}

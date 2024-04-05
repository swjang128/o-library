package o.api.library.dto;

import jdk.jfr.Description;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import o.api.library.domain.BookSortingMethod;
import o.api.library.entity.Book;
import o.api.library.entity.Member;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookListDto {
    private BookSortingMethod bookSortingMethod;
    private int minimumPrice;
    private int maximumPrice;
    private boolean status;
}

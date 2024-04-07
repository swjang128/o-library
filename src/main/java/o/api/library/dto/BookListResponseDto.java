package o.api.library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import o.api.library.domain.BookSortingMethod;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookListResponseDto {
    private String title;
    private String isbn;
    private int price;
    private String consignerName;
}

package o.api.library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import o.api.library.domain.BookSortingMethod;
import org.springframework.data.domain.Pageable;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookListRequestDto {
    private Pageable pageable;
    private BookSortingMethod bookSortingMethod;
    private int minimumPrice;
    private int maximumPrice;
    private boolean status;
}

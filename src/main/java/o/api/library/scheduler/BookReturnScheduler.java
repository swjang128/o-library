package o.api.library.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import o.api.library.entity.Book;
import o.api.library.entity.BookHistory;
import o.api.library.repository.BookHistoryRepository;
import o.api.library.repository.BookRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

import static o.api.library.domain.BookHistoryType.RETURN;

@Component
@Slf4j
@RequiredArgsConstructor
public class BookReturnScheduler {
    private final BookRepository bookRepository;
    private final BookHistoryRepository bookHistoryRepository;

    @Scheduled(cron = "*/20 * * * * *") // 초단위로 20초마다 실행
    public void returnBook() {
        // Book의 status가 false이고, modified_date가 현재 시각보다 10초가 넘고, checkoutId가 null이 아닌 도서 목록 조회
        List<Book> books = bookRepository.findByStatusAndModifiedDateBeforeAndCheckoutIdIsNotNull(false, LocalDateTime.now().minusSeconds(10));
        // 반납할 도서가 없으면 스케줄러 종료
        if (books.isEmpty()) {
            log.info("반납할 도서가 없습니다");
            return;
        }
        // 반납 대상 도서가 있으면 status를 true로 바꾸고 checkoutId를 null로 바꾼다.
        log.info("**** 도서 반납 스케줄러 실행");
        for (Book book : books) {
            Book updatedBook = Book.builder()
                    .id(book.getId())
                    .consignerId(book.getConsignerId())
                    .checkoutCount(book.getCheckoutCount())
                    .title(book.getTitle())
                    .isbn(book.getIsbn())
                    .price(book.getPrice())
                    .status(true)
                    .checkoutId(null)
                    .build();
            bookRepository.save(updatedBook);
            // 위의 정보를 BookHistory에 아래와 같이 INSERT
            BookHistory bookHistory = BookHistory.builder()
                    .bookId(book.getId())
                    .checkoutId(null)
                    .title(book.getTitle())
                    .isbn(book.getIsbn())
                    .bookHistoryType(RETURN)
                    .build();
            // BookHistory 저장
            bookHistoryRepository.save(bookHistory);
        }
    }
}
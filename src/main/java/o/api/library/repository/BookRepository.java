package o.api.library.repository;

import o.api.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    List<Book> findByStatusAndModifiedDateBeforeAndCheckoutIdIsNotNull(boolean status, LocalDateTime localDateTime);

    List<Book> findByIdIn(List<Long> bookIds);
}

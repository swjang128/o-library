package o.api.library.specification;

import jakarta.persistence.criteria.Predicate;
import o.api.library.domain.BookSortingMethod;
import o.api.library.entity.Book;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {
    public static Specification<Book> withSortingMethod(BookSortingMethod sortingMethod, Integer minimumPrice, Integer maximumPrice) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            // 제목, ISBN, 대여료에 따라 조회 순서를 변경한다.
            switch (sortingMethod) {
                case TITLE -> query.orderBy(criteriaBuilder.asc(root.get("title")));
                case ISBN -> query.orderBy(criteriaBuilder.asc(root.get("isbn")));
                case PRICE -> query.orderBy(criteriaBuilder.asc(root.get("price")));
                default -> throw new IllegalArgumentException("Invalid sorting method: " + sortingMethod);
            }
            // 최저 대여료부터 최고 대여료 사이의 도서를 조회하는 조건을 추가
            if (minimumPrice != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minimumPrice));
            }
            if (maximumPrice != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("price"), maximumPrice));
            }
            return predicate;
        };
    }
}
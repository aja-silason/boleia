package com.boleia.boleia.shared.types;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class Paginator {

  public static Pageable fromRequest(int page, int size, String order, String orderBy) {
    return PageRequest.of(
        Math.max(0, page - 1), size, Sort.by(Sort.Direction.fromString(order), orderBy));
  }

  public static <T> Pagination<T> toPagination(List<T> data, Page page, long total) {
    return new Pagination<T>(
        data, page.getNumber() + 1, page.getSize(), total, page.getTotalPages());
  }
}

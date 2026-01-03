package org.wireBarley.common.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class BasePage<T> extends PageImpl<T> {

    private static final long serialVersionUID = 3248189030448292002L;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public BasePage(@JsonProperty("content") List<T> content,
        @JsonProperty("number") int number,
        @JsonProperty("size") int size,
        @JsonProperty("totalElements") Long totalElements) {
        super(content, PageRequest.of(number, size), totalElements);
    }

    public BasePage(List<T>
        content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public BasePage(List<T> content) {
        super(content);
    }

    public BasePage() {
        super(new ArrayList<>());
    }

    @JsonIgnore
    @Override
    public Pageable getPageable() {
        return super.getPageable();
    }

    @JsonIgnore
    @Override
    public Sort getSort() {
        return super.getSort();
    }
}

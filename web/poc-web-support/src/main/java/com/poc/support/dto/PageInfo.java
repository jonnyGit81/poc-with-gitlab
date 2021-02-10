package com.poc.support.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.io.Serializable;

@Getter
public final class PageInfo implements Serializable {

    private static final long serialVersionUID = -7954525524761208952L;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final int currentPageNumber;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final long numbersOfElements;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final long totalElements;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final long totalPages;

    private PageInfo(int currentPageNumber,
                     long numbersOfElements,
                     long totalElements,
                     long totalPages) {
        this.currentPageNumber = currentPageNumber;
        this.numbersOfElements = numbersOfElements;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public static class Builder {
        private int currentPageNumber;
        private long numbersOfElements;
        private long totalElements;
        private long totalPages;

        public Builder(){}

        public Builder currentPageNumber(int currPageNumber) {
            this.currentPageNumber = currPageNumber;
            return this;
        }

        public Builder numbersOfElements(long numbersOfElements) {
            this.numbersOfElements = numbersOfElements;
            return this;
        }

        public Builder totalElements(long totalElements) {
            this.totalElements = totalElements;
            return this;
        }


        public Builder totalPages(long totalPages) {
            this.totalPages = totalPages;
            return this;
        }

        public PageInfo build() {
            return new PageInfo(this.currentPageNumber,
                    this.numbersOfElements,
                    this.totalElements,
                    this.totalPages);
        }
    }
}



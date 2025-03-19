package me.dionclei.dslist.dto;

import java.util.List;

public record PagedResult<T>(Integer page, Integer totalPages, List<T> data) {

}

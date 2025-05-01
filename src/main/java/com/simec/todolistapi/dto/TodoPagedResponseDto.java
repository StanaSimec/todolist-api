package com.simec.todolistapi.dto;

import java.util.List;

public record TodoPagedResponseDto(List<TodoResponseDto> data, Integer page, Integer limit, Integer total) {
}

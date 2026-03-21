package com.boleia.boleia.shared.types;

import java.util.List;

public record Pagination<T>(List<T> entries, long page, long size, long total, long pages) {}

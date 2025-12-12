package com.yurt.patterns;

import java.util.List;

public interface SortStrategy {
    // Listeyi alıp sıralayan metod
    void sort(List<String> data);
}
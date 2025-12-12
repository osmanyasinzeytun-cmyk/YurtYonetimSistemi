package com.yurt.patterns;

import java.util.Collections;
import java.util.List;

public class SortDescending implements SortStrategy {
    @Override
    public void sort(List<String> data) {
        // Tersten sıralama
        Collections.sort(data, Collections.reverseOrder());
        System.out.println("List Z-A şeklinde sıralandı.");
    }
}
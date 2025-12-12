package com.yurt.patterns;

import java.util.Collections;
import java.util.List;

public class SortAscending implements SortStrategy {
    @Override
    public void sort(List<String> data) {
        // Java'nın hazır alfabetik sıralamasını kullanıyoruz
        Collections.sort(data);
        System.out.println("List A-Z şeklinde sıralandı.");
    }
}
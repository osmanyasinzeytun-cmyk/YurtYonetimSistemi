package com.yurt.patterns;

public interface Observer {
    // Bir olay olduğunda bu metod çalışacak
    void update(String message);
}
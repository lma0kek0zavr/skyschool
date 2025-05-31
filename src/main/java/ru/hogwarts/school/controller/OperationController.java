package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/operation")
public class OperationController {
    
    @GetMapping("/default")
    public String getDefaultTime() {
        return timeIt("Default time", () -> {
            int sum = Stream.iterate(1, a -> a +1)
                    .limit(1_000_000)
                    .reduce(0, (a, b) -> a + b );
        });
    }
    
    @GetMapping("/optimized")
    public String getOptimizedTime() {
        return timeIt("Optimized time", () -> {
            int sum = IntStream.rangeClosed(1, 1_000_000)
                    .sum();
        });
    }

    private static String timeIt(String label, Runnable task) {
        long start = System.nanoTime();
        task.run();
        long duration = System.nanoTime() - start;
        return String.format("%s -> %.3f ms%n", label, duration / 1_000_000.0);
    }
}

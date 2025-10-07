package ru.naumen.collection.task4;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Supplier;

/**
 * Класс управления расчётами
 */
public class ConcurrentCalculationManager<T> {
    //Гарантия порядка
    private final LinkedBlockingQueue<CompletableFuture<T>> futuresQueue = new LinkedBlockingQueue<>();

    /**
     * Добавить задачу на параллельное вычисление
     */
    public void addTask(Supplier<T> task) {
        CompletableFuture<T> future = CompletableFuture.supplyAsync(task);
        //Сложность O(1) тк происходит вставка в известное место (порядок известен)
        futuresQueue.offer(future);
    }
    /**
     * Получить результат вычисления.
     * Возвращает результаты в том порядке, в котором добавлялись задачи.
     */
    public T getResult() {
        try {
            //Сложность O(1) тк происходит извлечение из известного места (порядок известен)
            CompletableFuture<T> future = futuresQueue.take();
            return future.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
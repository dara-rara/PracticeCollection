package ru.naumen.collection.task4;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Supplier;

/**
 * Класс управления расчётами
 */
//Время выполнения зависит от самой долгой задачи
public class ConcurrentCalculationManager<T> {
    //Выбор коллекции, тк имеет блокирующие операции, операции O(1), хранит порядок
    private final BlockingQueue<CompletableFuture<T>> futuresQueue = new LinkedBlockingQueue<>();

    /**
     * Добавить задачу на параллельное вычисление
     */
    public void addTask(Supplier<T> task) {
        CompletableFuture<T> future = CompletableFuture.supplyAsync(task);
        //O(1) - вставка в конец
        futuresQueue.offer(future);
    }
    /**
     * Получить результат вычисления.
     * Возвращает результаты в том порядке, в котором добавлялись задачи.
     */
    public T getResult() {
        try {
            //O(1) - извелечение с начала + блокировка при пустой очереди
            CompletableFuture<T> future = futuresQueue.take();
            return future.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
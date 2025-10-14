package ru.naumen.collection.task3;

import java.nio.file.Path;
import java.util.*;

/**
 * <p>Написать консольное приложение, которое принимает на вход произвольный текстовый файл в формате txt.
 * Нужно собрать все встречающийся слова и посчитать для каждого из них количество раз, сколько слово встретилось.
 * Морфологию не учитываем.</p>
 * <p>Вывести на экран наиболее используемые (TOP) 10 слов и наименее используемые (LAST) 10 слов</p>
 * <p>Проверить работу на романе Льва Толстого “Война и мир”</p>
 *
 * @author vpyzhyanov
 * @since 19.10.2023
 */
public class WarAndPeace
{

    private static final Path WAR_AND_PEACE_FILE_PATH = Path.of("src/main/resources",
            "Лев_Толстой_Война_и_мир_Том_1,_2,_3,_4_(UTF-8).txt");

    //Общая сложность: O(n) -> n - количество слов в файле
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        //Выбор коллекции, тк быстрая итерация
        Map<String, Integer> wordCountMap = new LinkedHashMap<>();

        //O(n) -> n - количество слов в файле
        new WordParser(WAR_AND_PEACE_FILE_PATH).forEachWord(word -> {
            //Функция hashcode(определена в string) гарантирует O(1) сложность в операции
            wordCountMap.merge(word, 1, Integer::sum);
        });

        //Выбор коллекции, тк поддерживает порядок и лучше сортировки целиком
        //O(n) -> O(1) -> размер кучи 10
        PriorityQueue<Map.Entry<String, Integer>> topHeap =
                new PriorityQueue<>(11, Comparator.comparingInt(Map.Entry::getValue));

        PriorityQueue<Map.Entry<String, Integer>> lastHeap =
                new PriorityQueue<>(11, (a, b) -> b.getValue() - a.getValue());

        //O(n) -> n - количество уникальных слов
        for (Map.Entry<String, Integer> entry : wordCountMap.entrySet()) {
            topHeap.offer(entry);//O(log n) -> O(1) -> размер кучи 10
            if (topHeap.size() > 10) topHeap.poll();
            lastHeap.offer(entry);
            if (lastHeap.size() > 10) lastHeap.poll();
        }

        System.out.println("\nTOP 10 САМЫХ ЧАСТЫХ:");
        //Выбор коллекции, тк нужен обратный порядок элементов из очереди
        List<Map.Entry<String, Integer>> topList = new LinkedList<>();
        while (!topHeap.isEmpty()) topList.addFirst(topHeap.poll());//O(1)
        for (Map.Entry<String, Integer> entry : topList) {
            System.out.println(entry);
        }
        System.out.println("\nLAST 10 САМЫХ РЕДКИХ:");
        List<Map.Entry<String, Integer>> lastList = new LinkedList<>();
        while (!lastHeap.isEmpty()) lastList.addFirst(lastHeap.poll());
        for (Map.Entry<String, Integer> entry : lastList) {
            System.out.println(entry);
        }

        System.out.printf("%nВремя: %d мс%n", System.currentTimeMillis() - startTime);
    }
}

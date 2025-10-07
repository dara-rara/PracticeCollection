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

    public static void main(String[] args) {

        Map<String, Integer> wordCount = new HashMap<>();

        new WordParser(WAR_AND_PEACE_FILE_PATH)
                .forEachWord(word -> {
                    //Функция hashcode(определена в string) гарантирует O(1) сложность в операции
                    wordCount.merge(word, 1, Integer::sum);
                });

        //Нужен список, для полной сортровки
        //Полная сортировка имеет сложность O(n log n)
        //Но тк в файле мало данных + многие результаты частот дублируются - лучшая производительность
        List<Map.Entry<String, Integer>> sortedWords = new ArrayList<>(wordCount.entrySet());
        sortedWords.sort(Map.Entry.<String, Integer>comparingByValue().reversed());

        //Быстрый доступ по индексу O(1)
        System.out.println("TOP 10 самых частых слов:");
        sortedWords.stream().limit(10).forEach(entry ->
                System.out.println(entry.getKey() + ": " + entry.getValue())
        );

        System.out.println("\nLAST 10 самых редких слов:");
        sortedWords.stream().skip(sortedWords.size() - 10).forEach(entry ->
                System.out.println(entry.getKey() + ": " + entry.getValue())
        );
    }
}

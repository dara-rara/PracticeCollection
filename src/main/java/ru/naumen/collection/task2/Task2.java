package ru.naumen.collection.task2;

import java.util.*;

/**
 * Дано:
 * <pre>
 * public class User {
 *     private String username;
 *     private String email;
 *     private byte[] passwordHash;
 *     …
 * }
 * </pre>
 * Нужно реализовать метод
 * <pre>
 * public static List<User> findDuplicates(Collection<User> collA, Collection<User> collB);
 * </pre>
 * <p>который возвращает дубликаты пользователей, которые есть в обеих коллекциях.</p>
 * <p>Одинаковыми считаем пользователей, у которых совпадают все 3 поля: username,
 * email, passwordHash. Дубликаты внутри коллекций collA, collB можно не учитывать.</p>
 * <p>Метод должен быть оптимален по производительности.</p>
 * <p>Пользоваться можно только стандартными классами Java SE.
 * Коллекции collA, collB изменять запрещено.</p>
 *
 * См. {@link User}
 *
 * @author vpyzhyanov
 * @since 19.10.2023
 */
public class Task2
{

    /**
     * Возвращает дубликаты пользователей, которые есть в обеих коллекциях
     */
    //Общая сложность: O(n+m) -> n,m размеры коллекций
    public static List<User> findDuplicates(Collection<User> collA, Collection<User> collB) {
        //Функция hashcod и equals (переопределены в user) гарантирует O(1) сложность в операции поиска
        //Выбираем эту коллекцию, тк contains О(1) + хранение уник. значений
        Set<User> setA = new HashSet<>(collA);//Создание новой коллекции O(n)!
        //Выбираем эту коллекцию, тк add О(1)
        List<User> duplicates = new ArrayList<>(Math.min(collA.size(), collB.size()));//Исключаем динамическое расширение
        //O(m) - длина коллекции
        for (User user : collB) {
            if (setA.contains(user)) {
                duplicates.add(user);
            }
        }
        return duplicates;
    }
}

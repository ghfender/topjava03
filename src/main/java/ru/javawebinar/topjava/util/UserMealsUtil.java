package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500)
        );
        getFilteredMealsWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredMealsWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        final Map<LocalDate, Integer> calsPerDay = new HashMap<>();
        // считаем по дням
        for (UserMeal userMeal : mealList) {
            final LocalDate date = userMeal.getDateTime().toLocalDate();
            final int cals = userMeal.getCalories();
            if (!calsPerDay.containsKey(date)) {
                calsPerDay.put(date, cals);
            } else {
                calsPerDay.put(date, calsPerDay.get(date) + cals);
            }
        }
        // перебираем и возвращаем
        List<UserMealWithExceed> res = new ArrayList<>(mealList.size());
        for (UserMeal userMeal : mealList) {
            LocalDateTime dateTime = userMeal.getDateTime();
            if (!TimeUtil.isBetween(dateTime.toLocalTime(), startTime, endTime)) {
                continue;
            }
            boolean exceed = calsPerDay.get(dateTime.toLocalDate()) > caloriesPerDay;
            res.add(new UserMealWithExceed(dateTime, userMeal.getDescription(), userMeal.getCalories(), exceed));
        }
        return res;
    }
}

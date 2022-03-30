package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.formatters.DateFormatter;
import ru.javawebinar.topjava.util.formatters.TimeFormatter;
import ru.javawebinar.topjava.web.SecurityUtil;


import java.net.URI;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping(value = "/rest/meals", produces = MediaType.APPLICATION_JSON_VALUE)
public class MealRestController extends AbstractMealController {

    @Autowired
    UserService userService;
    @Autowired
    DateFormatter dateFormatter;
    @Autowired
    TimeFormatter timeFormatter;

    @GetMapping()
    public List<MealTo> getAll() {
        return super.getAll();
    }

    @GetMapping("/{id}")
    public Meal get(@PathVariable int id) {
        return super.get(id);
    }

    @GetMapping("/filter")
    public List<MealTo> getByFiltered(@RequestParam("startDate") String startDateParam,
                                      @RequestParam("endDate") String endDateParam,
                                      @RequestParam("startTime") String startTimeParam,
                                      @RequestParam("endTime") String endTimeParam) {
        Locale locale = new Locale("ru");

        LocalDate startDate = null;
        LocalDate endDate = null;
        LocalTime startTime = null;
        LocalTime endTime = null;
        try {
            startDate = dateFormatter.parse(startDateParam, locale);
            endDate = dateFormatter.parse(endDateParam, locale);
            startTime = timeFormatter.parse(startTimeParam, locale);
            endTime = timeFormatter.parse(endTimeParam, locale);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return super.getBetween(startDate, startTime, endDate, endTime);

    }


    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Meal meal, @PathVariable int id) {
        Meal oldMeal = super.get(id);
        if (meal.getDateTime() != null) {
            oldMeal.setDateTime(meal.getDateTime());
        }
        if (meal.getDescription() != null) {
            oldMeal.setDescription(meal.getDescription());
        }
        if (meal.getCalories() != 0) {
            oldMeal.setCalories(meal.getCalories());
        }
        super.update(oldMeal, id);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createMeal(@RequestBody Meal meal) {
        Meal created = super.create(meal);
        created.setUser(userService.get(SecurityUtil.authUserId()));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("meals" + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

}
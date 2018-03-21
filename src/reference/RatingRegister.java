/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import reference.domain.Film;
import reference.domain.Person;
import reference.domain.Rating;

/**
 *
 * @author filip
 */
public class RatingRegister {

    private Map<Film, List<Rating>> register;
    private Map<Person, Map<Film, Rating>> personalRatings;

    public RatingRegister() {
        register = new HashMap<Film, List<Rating>>();
        personalRatings = new HashMap<Person, Map<Film, Rating>>();
    }

    public void addRating(Film film, Rating rating) {
        if (!register.containsKey(film)) {
            List<Rating> ratings = new ArrayList<Rating>();
            ratings.add(rating);
            register.put(film, ratings);
        } else {
            register.get(film).add(rating);
        }
    }

    public List<Rating> getRatings(Film film) {
        return register.get(film);
    }

    public Map<Film, List<Rating>> filmRatings() {
        return register;
    }

    public void addRating(Person person, Film film, Rating rating) {

        if (!personalRatings.containsKey(person)) {
            Map<Film, Rating> filmRating = new HashMap<Film, Rating>();
            filmRating.put(film, rating);
            addRating(film, rating);
            personalRatings.put(person, filmRating);
        } else {
            addRating(film, rating);
            Map<Film,Rating> filmRating = personalRatings.get(person);
            if (!filmRating.containsKey(film)) {
                filmRating.put(film, rating);
            }
        }
    }
    
    public Rating getRating(Person person, Film film) {
        Rating result = personalRatings.get(person).get(film);
        if (result == null) {
            return Rating.NOT_WATCHED;
        }
        return result;
    }
    
    public Map<Film, Rating> getPersonalRatings(Person person) {
        Map<Film, Rating> result = personalRatings.get(person);
        if (result == null) {
            return new HashMap<Film, Rating>();
        }
        return result;
    }
    
    public List<Person> reviewers() {
        List<Person> people = new ArrayList<Person>();
        Iterator<Person> iterator = personalRatings.keySet().iterator();
        while (iterator.hasNext()) {
            people.add(iterator.next());
        }
        return people;
    }

}

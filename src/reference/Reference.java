/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import reference.comparator.FilmComparator;
import reference.comparator.PersonComparator;
import reference.domain.Film;
import reference.domain.Person;
import reference.domain.Rating;

/**
 *
 * @author filip
 */
public class Reference {

    private RatingRegister register;

    public Reference(RatingRegister register) {
        this.register = register;
    }

    public Film recommendFilm(Person person) {
        if (register.getPersonalRatings(person).isEmpty()) {
            List<Film> films = new ArrayList<Film>(register.filmRatings().keySet());
            Collections.sort(films, new FilmComparator(register.filmRatings()));
            return films.get(0);
        }
        
        Map<Person, Integer> similarityMap = new HashMap<Person, Integer>();
        for (Person p : register.reviewers()) {
            if (p.equals(person)) {
                continue;
            }
            int similarityScore = 0;
            for (Film f : register.filmRatings().keySet()) {
                similarityScore += register.getRating(person, f).getValue() * register.getRating(p, f).getValue();
            }
            similarityMap.put(p, similarityScore);
        }
        
        List<Person> reviewers = new ArrayList<Person>(similarityMap.keySet());
        if (similarityMap.isEmpty()) {
            return null;
        }
        
        Collections.sort(reviewers, new PersonComparator(similarityMap));
        Person bestMatch = reviewers.get(0);
        
        for (Film f : register.getPersonalRatings(bestMatch).keySet()) {            
            if (!register.getRating(person, f).equals(Rating.NOT_WATCHED)) {
                continue;
            }
            if (register.getRating(bestMatch, f).getValue() > 1) {
                return f;
            }
        }  
               
        return null;
        
    }
    


}

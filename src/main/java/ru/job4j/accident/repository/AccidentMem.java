package ru.job4j.accident.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {
    private static final AtomicInteger ID = new AtomicInteger(1);
    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private final Map<Integer, AccidentType> types = new ConcurrentHashMap<>();
    private final Map<Integer, Rule> rules = new ConcurrentHashMap<>();

    @Autowired
    public AccidentMem() {
        types.put(1, AccidentType.of(1, "Две машины"));
        types.put(2, AccidentType.of(2, "Машина и человек"));
        types.put(3, AccidentType.of(3, "Машина и велосипед"));
        rules.put(1, Rule.of(1, "Статья. 1"));
        rules.put(2, Rule.of(2, "Статья. 2"));
        rules.put(3, Rule.of(3, "Статья. 3"));
    }

    public List<AccidentType> getAccidentTypes() {
        return new ArrayList<>(types.values());
    }

    public List<Accident> findAllAccident() {
        return new ArrayList<>(accidents.values());
    }

    public Collection<Accident> getAccidentHashMap() {
        return accidents.values();
    }

    public void  create(Accident accident) {
        if (accident.getId() == 0) {
            accident.setId(ID.getAndIncrement());
        }
        AccidentType newType = types.get(accident.getType().getId());
        accident.setType(newType);
        accidents.put(accident.getId(), accident);
    }

    public Accident findById(int id) {
        return accidents.get(id);
    }

    public Accident updateAcc(Accident accident) {
        accident.setType(types.get(accident.getType().getId() - 1));
        return accidents.put(accident.getId(), accident);
    }

    public Collection<Rule> getRules() {
        return rules.values();
    }

    public Rule findRule(int id) {
        return rules.get(id);
    }

}

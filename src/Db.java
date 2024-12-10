package src;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class Db {

    private Map<Integer, Person> ranking;
    private String filename;

    public Db(){
        this.ranking = new LinkedHashMap<Integer, Person>();
        this.filename = "db.json";
    }


    // Lire le fichier JSON et retourner une Map<Integer, Person>
    public void readFromFile() {
        try (FileReader reader = new FileReader(filename)) {
            // Définir le type exact de la Map à désérialiser
            Type type = new TypeToken<Map<Integer, Person>>() {}.getType();
            Gson gson = new Gson();
            ranking = gson.fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
            ranking = new HashMap<>(); // Retourne une map vide si erreur
        }
    }

    // Écrire dans le fichier JSON
    public void writeToFile() {
        try (FileWriter writer = new FileWriter(filename)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(ranking, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Ajouter une nouvelle personne dans le classement
    public void addPerson(String name, int score) {
        Person newPerson = new Person(name, score);
        // Trouver un ID disponible
        int newId = ranking.size() + 1;
        ranking.put(newId, newPerson);
    }

    // Trier les personnes par score décroissant
    public void sortRanking() {
        // Convertir la Map en une liste pour trier
        List<Map.Entry<Integer, Person>> list = new ArrayList<>(ranking.entrySet());

        // Trier par score décroissant
        list.sort((entry1, entry2) -> Integer.compare(entry2.getValue().score, entry1.getValue().score));

        // Recréer une Map triée
        Map<Integer, Person> sortedMap = new LinkedHashMap<>();
        int index = 1;
        for (Map.Entry<Integer, Person> entry : list) {
            sortedMap.put(index++, entry.getValue());
        }

        ranking = sortedMap;
    }
}


class Person {
    String name;
    int score;

    public Person(String name, int score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public String toString() {
        return "Person{name='" + name + "', score=" + score + "}";
    }
}
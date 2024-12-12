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

    private Map<Integer, Player> ranking;
    private String filename;

    public Db(){
        this.ranking = new LinkedHashMap<Integer, Player>();
        this.filename = "db.json";
    }


    // Lire le fichier JSON et retourner une Map<Integer, Player>
    public void readFromFile() {
        try (FileReader reader = new FileReader(filename)) {
            // Définir le type exact de la Map à désérialiser
            Type type = new TypeToken<Map<Integer, Player>>() {}.getType();
            Gson gson = new Gson();
            if (reader.ready()) ranking = gson.fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
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
        Player newPlayer = new Player(name, score);
        // Trouver un ID disponible
        int newId = (this.ranking.size() > 0 ? ranking.size() + 1 : 1);
        ranking.putIfAbsent(newId, newPlayer);
    }

    // Trouver le meilleur score enregistrer
    public int bestScore() {
        if(this.ranking.isEmpty()) return 0;
        else return this.ranking.get(1).getScore();
    }


    // Trier les personnes par score décroissant
    public void sortRanking() {
        // Convertir la Map en une liste pour trier
        List<Map.Entry<Integer, Player>> list = new ArrayList<>(ranking.entrySet());

        // Trier par score décroissant
        list.sort((entry1, entry2) -> Integer.compare(entry2.getValue().score, entry1.getValue().score));

        // Recréer une Map triée
        Map<Integer, Player> sortedMap = new LinkedHashMap<>();
        int index = 1;
        for (Map.Entry<Integer, Player> entry : list) {
            sortedMap.put(index++, entry.getValue());
        }

        ranking = sortedMap;
    }
}


class Player {
    String name;
    int score;

    public Player(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }
    public int getScore() {
        return score;
    }
}
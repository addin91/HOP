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


    /**
     * la méthode readFromFile lit le fichier JSON (base donnée) et enregistre le contenu dans ranking
     */
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

    
    /**
     * la méthode writeToFile écrit le contenu de ranking dans le fichier JSON (base donnée)
     */
    public void writeToFile() {
        try (FileWriter writer = new FileWriter(filename)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(ranking, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * la méthode addPerson ajoute une nouvelle personne dans le classement
     * @param name le nom du joueur
     * @param score le score du joueur
     */
    public void addPlayer(String name, int score) {
        Player newPlayer = new Player(name, score);
        // Trouver un ID disponible
        int newId = (this.ranking.size() > 0 ? ranking.size() + 1 : 1);
        ranking.putIfAbsent(newId, newPlayer);
    }

    /**
     * la méthode bestScore renvoi le meilleur score enregistrer
     * @return le meilleur score enrigistrer sinon 0
     */
    public int bestScore() {
        if(this.ranking.isEmpty()) return 0;
        else return this.ranking.get(1).getScore();
    }

    
    /**
     * la méthode renvoi le classement pour le score donné
     * @param score le score durant la parti
     * @return le rang dans le classement
     */
    public int rank(int score) {
        for(int i : ranking.keySet()){
            if(score >= ranking.get(i).getScore()){
                return i;
            }
        }
        return ranking.size()+1;
    }



    /**
     * la méthode sortRanking trie les joueurs par score décroissant dans ranking
     */
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

// Represente le joueur
class Player {
    String name;
    int score;

    public Player(final String name, final int score) {
        this.name = name;
        this.score = score;
    }

    // GETTERS

    public String getName() {
        return name;
    }
    public int getScore() {
        return score;
    }
}
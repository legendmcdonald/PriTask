package model.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.PriList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Handles the saving and loading of the Lists.
 */



public class Persistence {
    private static PriList[] lists;
//    private static PriList[] listsFromJson;
    private static String jsonString;


    public Persistence(){
    }

    public static void saveToJson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        jsonString = gson.toJson(lists);

        new File("json.txt");

        try {
            FileWriter fileWriter = new FileWriter("json.txt");
            PrintWriter printWriter = new PrintWriter(fileWriter,false);
            printWriter.println(jsonString);
            printWriter.close();
        } catch (IOException e) {
            System.out.println("Error writing to file...");
            e.printStackTrace();
        }

    }

    public static void loadJsonFile(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());
        Gson gson = gsonBuilder.setPrettyPrinting().create();

        try {
            jsonString = Files.readString(Paths.get("json.txt"));
            lists = gson.fromJson(jsonString, PriList[].class);
            LinkedList<PriList> priListLinkedList = new LinkedList<>();
            Collections.addAll(priListLinkedList, Persistence.lists);
            setList(priListLinkedList);

        } catch (IOException e) {
            // No file to be found, start from scratch, no need to print error message here.
        }
    }

    public static void setList(LinkedList<PriList> lists){
        Persistence.lists = lists.toArray(new PriList[lists.size()]);

    }

    public static LinkedList<PriList> getListsLoadedFromJson() {
        LinkedList<PriList> temp = new LinkedList<>();
        if (lists != null){
            Collections.addAll(temp, lists);
        }
        return temp;
    }

}

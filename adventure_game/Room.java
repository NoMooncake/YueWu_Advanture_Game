package adventure_game;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import adventure_game.items.Consumable;

public class Room {
    private int id;
    private String name;
    private String description;
    private Room east;
    private Room north;
    private Room west;
    private Room south;
    private NPC opponent;
    private ArrayList<Consumable> items;
    
    private static HashMap<Integer, Room> roomMap = new HashMap<>();
    
    public Room(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.items = new ArrayList<>();
        roomMap.put(id, this);
    }
    
    public static void readRoomsFromFile(String fileName) {
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            int numRooms = 0;
            while ((line = bufferedReader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                if (numRooms == 0) {
                    numRooms = Integer.parseInt(line);
                } else {
                    String[] tokens = line.split(":");
                    int id = Integer.parseInt(tokens[0]);
                    String name = tokens[1];
                    String description = tokens[2];
                    Room room = new Room(id, name, description);
                    for (int i = 3; i < tokens.length; i++) {
                        int roomId = Integer.parseInt(tokens[i]);
                        if (roomId != -1) {
                            if (i == 3) {
                                room.setEast(roomMap.get(roomId));
                            } else if (i == 4) {
                                room.setNorth(roomMap.get(roomId));
                            } else if (i == 5) {
                                room.setWest(roomMap.get(roomId));
                            } else if (i == 6) {
                                room.setSouth(roomMap.get(roomId));
                            }
                        }
                    }
                }
            }
            bufferedReader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void setEast(Room room) {
        this.east = room;
        room.west = this;
    }
    
    public void setNorth(Room room) {
        this.north = room;
        room.south = this;
    }
    
    public void setWest(Room room) {
        this.west = room;
        room.east = this;
    }
    
    public void setSouth(Room room) {
        this.south = room;
        room.north = this;
    }
    
    
}



package adventure;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GameWorldBuilder {

    private String location;
    private final List<String> inventory;
    private final Map<String, Room> roomMap;
    private final Map<String, Thing> objectMap;
    private final Map<String, Responder> responderMap;

    public GameWorldBuilder() {
        this.location = "The starting location has not been set.";
        this.inventory = new LinkedList<>();
        this.roomMap = new HashMap<>();
        this.objectMap = new HashMap<>();
        this.responderMap = new HashMap<>();
    }
    
    public void addObject(Thing thing) {
        if (thing.parent.equals("inventory")) {
            inventory.add(thing.name);
        }
        if (roomMap.containsKey(thing.parent)) {
            Room room = roomMap.get(thing.parent);
            room.children.add(thing.name);
        }
        if (objectMap.containsKey(thing.parent)) {
            Thing objParent = objectMap.get(thing.parent);
            objParent.children.add(thing.name);
        }
        objectMap.put(thing.name, thing);
    }
    
    public void addRoom(Room room) {
        roomMap.put(room.name, room);
    }
    
    public void addResponse(String name, Responder resp) {
        responderMap.put(name, resp);
    }
    
    public void setLocation(String loc) {
        location = loc;
    }

    public GameWorld toGameWorld() {
        return new GameWorld(location, inventory, roomMap, objectMap, responderMap);
    }
    

}

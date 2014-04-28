package adventure;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameWorld {

    private String location;
    private final List<String> inventory;
    private final Map<String, Room> roomMap;
    private final Map<String, Thing> objectMap;
    private final Map<String, Responder> responderMap;

    public GameWorld(String location,
            List<String> inventory,
            Map<String, Room> roomMap,
            Map<String, Thing> thingMap,
            Map<String, Responder> responderMap) {
        this.location = location;
        this.inventory = inventory;
        this.roomMap = roomMap;
        this.objectMap = thingMap;
        this.responderMap = responderMap;
    }

    public String getLocation() {
        return location;
    }

    public List<String> getInventory() {
        List<String> result = new ArrayList<>();
        for (String s : inventory) {
            result.add(s);
        }
        return result;
    }

    public List<String> getChildren(String thing) {
        List<String> result = new ArrayList<>();
        for (String s : objectMap.get(thing).children) {
            result.add(s);
        }
        return result;
    }

    public void movePlayer(String newLocation) {
        this.location = newLocation;
    }

    public void moveItemToInventory(String name) {
        inventory.add(name); //adds thing to inventory list
        objectMap.get(name).parent = "inventory"; //sets parent as inventory

        // since this didn't work when the object removed was in another obkect
        // in the room I rewrote it to find the parent and remove its child
        //checks if in main room
        if (roomMap.get(location).children.contains(name)) {
            roomMap.get(location).children.remove(name);
        } //checks if in object in room
        else {
            List<String> items = getAllRoomObjects(location);
            for (String item : items) {
                if (getThing(item).children.contains(name)) {
                    getThing(item).children.remove(name);
                }
            }
        }
    }

    public boolean objectHasProperty(String obj, String prop) {
        assert isThing(obj);
        Thing thing = objectMap.get(obj);
        return thing.properties.contains(prop);
    }

    public void giveObjectProperty(String obj, String prop) {
        assert isThing(obj);
        Thing thing = objectMap.get(obj);
        if (!thing.properties.contains(prop)) {
            thing.properties.add(prop);
        }
    }

    public void removeObjectProperty(String obj, String prop) {
        assert isThing(obj);
        Thing thing = objectMap.get(obj);
        thing.properties.remove(prop);
    }

    public Room getRoom(String name) {
        assert roomMap.containsKey(name);
        return roomMap.get(name);
    }

    public Thing getThing(String name) {
        assert objectMap.containsKey(name);
        return objectMap.get(name);
    }

    public boolean hasResponder(String name) {
        return responderMap.containsKey(name);
    }

    public Responder getResponder(String name) {
        assert responderMap.containsKey(name);
        return responderMap.get(name);
    }

    public boolean isValid() {
        return roomMap.containsKey(location);
    }

    public boolean isRoom(String name) {
        return roomMap.containsKey(name);
    }

    public boolean isThing(String name) {
        return objectMap.containsKey(name);
    }

    public boolean isDoor(String name) {
        if (!isThing(name)) {
            return false;
        }
        Thing thing = objectMap.get(name);
        return (thing instanceof Door);
    }

    public boolean isNoun(String word) {
        for (String s : objectMap.keySet()) {
            if (s.endsWith(word)) {
                return true;
            }
        }
        return false;
    }

    public List<String> getAllVisibleRoomObjects(String room) {
        List<String> temp = new ArrayList();
        for (String child : (roomMap.get(room).children)) {
            temp.addAll(getVisibleDescendents(child));
        }

        System.out.println("inventory " + inventory);
        temp.addAll(inventory); //inventory is always in scope

        return temp;
    }

    public List<String> getAllRoomObjects(String room) {
        List<String> temp = new ArrayList();
        for (String child : (roomMap.get(room).children)) {
            temp.addAll(getDescendents(child));
        }

        temp.addAll(inventory); //inventory is always in scope

        return temp;
    }

    public List<String> getDescendents(String name) {
        List<String> temp = new ArrayList();
        // if no more descendents or is closed you can't see it
        if (objectMap.get(name).children.isEmpty()) {
            temp.add(name);
        } else {
            for (String object : (objectMap.get(name).children)) {
                temp.addAll(getDescendents(object));
            }
            temp.add(name);
        }
        return temp;
    }

    public List<String> getVisibleDescendents(String name) {
        List<String> temp = new ArrayList();
        // if no more descendents or is closed you can't see it        
        if ((objectMap.get(name).properties.contains("openable")
                && !objectMap.get(name).properties.contains("open"))
                || (objectMap.get(name).children.isEmpty())) {

            temp.add(name);
        } else if (objectMap.get(name).children.isEmpty()) {
            temp.add(name);
        } else {
            for (String object : (objectMap.get(name).children)) {
                temp.addAll(getDescendents(object));
            }
            temp.add(name);
        }
        return temp;
    }

    // DONE
    public String printAllRoomObjects(String room) {
        List<String> items = roomMap.get(room).children;
        items.remove(room);
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append(room);
        sb.append("<br>");

        for (String item : items) {
            sb.append(printDescendents(item, sb.toString(), 1));
        }
        
        sb.append("</html>");
        return sb.toString();
    }

    // DONE
    public String printDescendents(String obj, String output, int indents) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indents; i++) {
            sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
        }
        if (objectMap.get(obj).children.isEmpty()) {
            sb.append(obj);
            sb.append("<br>");
        } else {
            List<String> children = objectMap.get(obj).children;
            sb.append(obj);
            sb.append("<br>");
            for (String child : children) {
                sb.append(printDescendents(child, sb.toString(), indents + 1));
            }
        }
        return sb.toString();
    }
}

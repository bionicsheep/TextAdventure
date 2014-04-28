
package adventure;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Room {

    public final String name;
    public String description;        
    public final List<String> children;
    public final Map<String, String> destinations;
    public final Map<String, String> doors;

    public Room(String name) {
        assert name != null;
        assert !name.equals("");

        this.name = name;
        description = "NO DESCRIPTION";
        children = new LinkedList<>();
        destinations = new HashMap<>();
        doors = new HashMap();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(name).append("<br>\n");
        sb.append("Description: ").append(description).append("<br>\n");
        sb.append("Children: ").append(children.isEmpty() ? "NO CHILDREN" : children.toString()).append("<br>\n");
        sb.append("Destinations: ").append(destinations.isEmpty() ? "NO DESTINATIONS" : destinations.toString()).append("<br>\n");
        sb.append("Doors: ").append(doors.isEmpty() ? "NO DOORS" : doors.toString()).append("<br>\n");
        return sb.toString();
    }
}

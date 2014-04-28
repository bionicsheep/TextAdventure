
package adventure;

import java.util.LinkedList;
import java.util.List;

public class Thing {

    public final String name;
    public final String noun;
    public String parent;
    public String description;        
    public final List<String> children;
    public final List<String> properties;

    public Thing(String name, String parent) {
        assert name != null;
        assert !(name.trim()).equals("");

        this.name = name;
        String[] words = name.split(" ");
        noun = words[words.length - 1];
        this.parent = parent;
        description = "";
        children = new LinkedList<>();
        properties = new LinkedList<>();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(name).append("<br>\n");
        sb.append("Parent: ").append(parent.equals("") ? "NO PARENT" : parent).append("<br>\n");
        sb.append("Description: ").append(description.equals("") ? "NO DESC" : description).append("<br>\n");
        sb.append("Children: ").append(children.isEmpty() ? "NO CHILDREN" : children.toString()).append("<br>\n");
        sb.append("Properties: ").append(properties.isEmpty() ? "NO PROPERTIES" : properties.toString()).append("<br>\n");
        return sb.toString();
    }

}


package adventure;

public class Door extends Thing {

    public String twin;
    public String key;
    public String destination;

    public Door(String name, String parent) {
        super(name, parent);

        twin = "";
        key = "";
        destination = "";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(name).append("<br>\n");
        sb.append("Parent: ").append(parent).append("<br>\n");
        sb.append("Twin: ").append(twin).append("<br>\n");
        sb.append("Key: ").append(key).append("<br>\n");
        sb.append("Destination: ").append(destination).append("<br>\n");
        sb.append("Description: ").append(description).append("<br>\n");
        sb.append("Properties: ").append(properties.toString()).append("<br>\n");
        return sb.toString();
    }

}

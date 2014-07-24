package adventure;

public class GameEngine {

    GameWorld world;
    DefaultResponder defaultResp;

    public GameEngine() {
        world = MyGame.buildGameWorld();
        defaultResp = new DefaultResponder(world);
    }

    public String getOutputText(String inputText) {
        String command = inputText.trim();
        command = command.toLowerCase();
        StringBuilder sb = new StringBuilder();
        String message = defaultResp.getResponse(command); // potentially updates the world

        sb.append("<html>");
        sb.append(getRoomName());
        sb.append("<br><br>");
        sb.append(getRoomDescription());
        sb.append("<br><br>");
        sb.append(message);
        sb.append("</html>");

        return sb.toString();
    }

    private String getRoomName() {
        return "<strong>" + world.getLocation() + "</strong>";
    }

    private String getRoomDescription() {
        return world.getRoom(world.getLocation()).description;
    }
}


package adventure;

public class MyGame {
    
    public static GameWorld buildGameWorld() {
        GameWorldBuilder gwb = new GameWorldBuilder();

        // ======================================================
        // Add rooms to game
        // ======================================================
        
        Room room;
        Responder resp;

        // Add Living Room and its responder
        room = new Room("Living Room");
        room.description = "You are in the living room of your small apartment. An armchair sits in the center of the room. The kitchen is west and your bedroom is east. You can exit your apartment to the south.";
        room.destinations.put("west", "Kitchen");
        room.destinations.put("east", "Bedroom");
        room.destinations.put("south", "Hallway");
        room.doors.put("south", "living room door");

        gwb.addRoom(room);

        // Add Kitchen and its responder
        room = new Room("Kitchen");
        room.description = "You are in the kitchen of your small aparment. A small refrigerator stands against the north wall. The only exit is east to the living room. There is a small wooden-framed window on the south wall.";
        room.destinations.put("east", "Living Room");

        gwb.addRoom(room);

        resp = (String verb, String noun, GameWorld world) -> {
            if (verb.equals("south")) {
                return "The kitchen window is painted shut - You can't budge it.";
            }
            return "";
        };

        gwb.addResponse(room.name, resp);

        // Add Bedroom
        room = new Room("Bedroom");
        room.description = "You are the bedroom of your small apartment. Your dresser is here. The living room is west and your bathroom is south.";
        room.destinations.put("west", "Living Room");
        room.destinations.put("south", "Bathroom");

        gwb.addRoom(room);

        // Add Bathroom
        room = new Room("Bathroom");
        room.description = "You are in the tiny bathroom of your small apartment. The only exit is north back to your bedroom. A small window lets in light from the south.";
        room.destinations.put("north", "Bedroom");

        gwb.addRoom(room);

        resp = (String verb, String noun, GameWorld world) -> {
            if (verb.equals("south")) {
                return "The bathroom window is painted shut - You can't budge it.";
            }
            return "";
        };

        gwb.addResponse(room.name, resp);
                
        // Add Hallway
        room = new Room("Hallway");
        room.description = "Congratulations - You made it out of your apartment!";
        room.destinations.put("north", "Living Room");
        room.doors.put("north", "apartment door");

        gwb.addRoom(room);

        // Set initial location
        
        gwb.setLocation("Living Room");
        
        // ======================================================
        // Add doors to rooms
        // ======================================================
        
        Door door;
        
        // Add apartment door
        door = new Door("living room door", "Living Room");
        door.key = "metal key";
        door.twin = "apartment door";
        door.properties.add("openable");
        door.properties.add("locked");
        door.destination = "Hallway";
        
        gwb.addObject(door);

        door = new Door("apartment door", "Hallway");
        door.key = "metal key";
        door.twin = "living room door";
        door.properties.add("openable");
        door.properties.add("locked");
        door.destination = "Living Room";
        
        gwb.addObject(door);

        // ======================================================
        // Add objects to rooms
        // ======================================================
        
        Thing thing;
        
        // DEBUG
        thing = new Thing("testingbox", "Living Room");
        thing.properties.add("openable");
        thing.properties.add("container");
        
        gwb.addObject(thing);
        
        thing = new Thing("testing blue ball", "testingbox");
        thing.properties.add("takeable");
        gwb.addObject(thing);
        
        thing = new Thing("testing red key", "testingbox");
        thing.properties.add("takeable");
        gwb.addObject(thing);
        
        thing = new Thing("testing yellow monkey", "testingbox");
        thing.properties.add("takeable");
        gwb.addObject(thing);
        
        // Add refrigerator to kitchen
        thing = new Thing("refrigerator", "Kitchen");
        thing.properties.add("openable");
        thing.properties.add("container");
        
        gwb.addObject(thing);
        
        // Add armchair to living room
        thing = new Thing("armchair", "Living Room");
        thing.description = "The armchair is old but comfortable.";
        
        gwb.addObject(thing);
        
        // Add dresser to bedroom
        thing = new Thing("dresser", "Bedroom");
        thing.properties.add("openable");
        thing.properties.add("container");
        
        gwb.addObject(thing);
       
        // Add key inside dresser 
        thing = new Thing("metal key", "dresser");
        thing.properties.add("takeable");
        
        gwb.addObject(thing);
        
        // ======================================================
        // Create the game world
        // ======================================================
        
        return gwb.toGameWorld();        
    }
}

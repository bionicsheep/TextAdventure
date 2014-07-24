package adventure;

import java.util.Arrays;
import java.util.List;

public class DefaultResponder {

    GameWorld world;
    List<String> verbs1;
    List<String> verbs2;

    public DefaultResponder(GameWorld world) {
        this.world = world;
        verbs1 = Arrays.asList("north", "south", "east", "west", "inventory", "displayroom");
        verbs2 = Arrays.asList("examine", "take", "open", "close", "display");
    }

    // DONE
    public String getResponse(String command) {
        int wordCount = getWordCount(command);
        //check if more than 2 words or none
        if (wordCount > 2) {
            return "I don't understand commands with more than two words.";
        } else if (wordCount < 1) {
            return "";
        }
        // type 1 verbs
        String verb = getVerb(command);
        if (verbs1.contains(verb)) {
            if (wordCount > 1) {
                return "The verb " + verb + " must be used by itself.";
            } else {
                if (verb.equals("inventory")) {
                    return getInventoryResponse();
                } else if (verb.equals("displayroom")) {
                    return getDisplayRoomResponse();
                } else {
                    return getMoveResponse(verb);
                }
            }
        }
        // type 2 verbs
        else if (verbs2.contains(verb)) {
            if (wordCount < 2) {
                return "The verb " + verb + " must be used with a noun.";
            } else {
                String name = getName(command);
                String noun = getNoun(name, false);
                // noun is not understood to game
                if (noun.equals("")) {
                    return "I do not understand the noun: " + name + ".";
                } // noun is understood to game
                else {
                    noun = getNoun(name, true);
                    // noun is understood but invisible at time
                    if (noun.equals("")) {
                        return "I do not see any " + name + " here.";
                    } // noun is understood and visible at time
                    else {
                        if (verb.equals("examine")) {
                            return getExamineResponse(noun);
                        } else if (verb.equals("take")) {
                            return getTakeResponse(noun);
                        } else if (verb.equals("open")) {
                            return getOpenResponse(noun);
                        } else if (verb.equals("close")) {
                            return getCloseResponse(noun);
                        } else if (verb.equals("display")) {
                            return getDisplayResponse(noun);
                        }
                    }
                }
            }
        }
        //unknown verb
        else {
            return "I do not understand the verb: " + verb + ".";
        }
        return "";
    }

    // DONE
    private String getNoun(String nounWord, Boolean mustBeVisible) {
        List<String> items;
        if (mustBeVisible) {
            items = world.getAllVisibleRoomObjects(world.getLocation());
        } else {
            items = world.getAllRoomObjects(world.getLocation());
        }
        for (int i = 0; i < items.size(); i++) {
            if ((world.getThing(items.get(i)).noun.equals(nounWord))) {
                return items.get(i);
            }
        }
        return "";
    }

    private String getVerb(String command) {
        String temp = command;
        for (int i = 0; i < command.length(); i++) {
            if (command.charAt(i) == ' ') {
                temp = command.substring(0, i);
            }
        }
        return temp;
    }

    private String getName(String command) {
        String temp = command;
        for (int i = 0; i < command.length(); i++) {
            if (command.charAt(i) == ' ') {
                temp = command.substring(i + 1, command.length());
            }
        }
        return temp;
    }

    private int getWordCount(String command) {
        int count = 0;
        if (command.length() > 0) {
            count++;
        }
        for (int i = 0; i < command.length(); i++) {
            if (command.charAt(i) == ' ') {
                count++;
            }
        }
        return count;
    }

    // DONE
    public String getMoveResponse(String direction) {
        Room currentRoom = world.getRoom(world.getLocation());
        // if direction is viable
        if(currentRoom.destinations.containsKey(direction)){
            //check if door is in way
            if(currentRoom.doors.containsKey(direction)){
                Door door = (Door) world.getThing(currentRoom.doors.get(direction));
                // if open pass through
                if(door.properties.contains("open")){
                    world.movePlayer(currentRoom.destinations.get(direction));
                    return "";
                }
                // door is closed
                else{
                    return "You need to open the " + door.name + " before you "
                            + "can go " + direction + ". ";
                }
            }
            world.movePlayer(currentRoom.destinations.get(direction));
            return "";
        }
        // if not viable
        else{
            return "You cant go " + direction + ". ";
        }
    }

    // DONE
    public String getInventoryResponse() {
        // if packing items
        if(world.getInventory().size() > 0){
            return "You are carrying " + listToSentence(world.getInventory()) + ". ";
        }
        // empty
        else{
            return "You aren't carrying anything. ";
        }
    }

    // DONE
    public String getDisplayRoomResponse() {
        return world.printAllRoomObjects(world.getLocation());
    }

    // DONE
    public String getDisplayResponse(String noun) {
        return world.getThing(noun).toString();
    }

    // DONE
    public String getOpenResponse(String noun) {
        // if openable
        if (world.getThing(noun).properties.contains("openable")) {
            // if open already
            if (world.getThing(noun).properties.contains("open")) {
                return "The " + noun + " is already open. ";
            } //if closed
            else {
                // if locked (doors only)
                if (world.getThing(noun).properties.contains("locked")) {
                    Door door = (Door) world.getThing(noun);
                    // if you have the key
                    if (world.getInventory().contains(door.key)) {
                        door.properties.remove("locked");
                        door.properties.add("open");
                        //opens twin
                        world.getThing(door.twin).properties.add("open");
                        return "You unlock the " + noun + " and open it. ";
                    } // if you don't have the key
                    else {
                        return "The " + noun + " is locked and you don't"
                                + " have the key";
                    }
                }
                // not locked
                world.getThing(noun).properties.add("open");
                // if contents
                if(world.getThing(noun).children.size() > 0){
                    List<String> tempList = world.getThing(noun).children;
                    tempList.remove(noun);
                    return "Opening the " + noun + " reveals " + listToSentence(tempList) + ". ";
                }
                // no contents
                return "You open the " + noun + ". ";
            }
        } // not openable
        else {
            return "The " + noun + " is not something you can open. ";
        }
    }

    // DONE
    public String getCloseResponse(String noun) {
        //if opennable
        if (world.getThing(noun).properties.contains("openable")) {
            // if open
            if (world.getThing(noun).properties.contains("open")) {
                world.getThing(noun).properties.remove("open");
                return "You close the " + noun + ". ";
            } // already closed
            else {
                return "The " + noun + " is already closed. ";
            }
        } // not openable
        else {
            return "The " + noun + " is not something you can close. ";
        }

    }

    // DONE
    public String getExamineResponse(String noun) {
        StringBuilder sb = new StringBuilder();

        // prints description if it has one
        if (world.getThing(noun).description.equals("")) {
            sb.append("You don't notice anything special about the " + noun + ". ");
        } // no description
        else {
            sb.append(world.getThing(noun).description);
        }
        // openable
        if (world.getThing(noun).properties.contains("openable")) {
            // if open
            if (world.getThing(noun).properties.contains("open")) {
                sb.append("The " + noun + " is open. ");
                // if container
                if (world.getThing(noun).properties.contains("container")) {
                    // if contents
                    if (world.getThing(noun).children.size() > 0) {
                        List<String> tempList = world.getVisibleDescendents(noun);
                        tempList.remove(noun); //remove item being opened
                        sb.append("Inside you see ");
                        sb.append(listToSentence(tempList));
                        sb.append(". ");
                    } // if empty
                    else {
                        sb.append("There is nothing inside. ");
                    }
                }
            } // if closed
            else {
                sb.append("The " + noun + " is closed. ");
            }
        }
        //not openable
        return sb.toString();
    }

    // DONE
    public String getTakeResponse(String noun) {
        // if takeable
        if (world.getThing(noun).properties.contains("takeable")) {
            // already have it
            if (world.getInventory().contains(noun)) {
                return "You already have the " + noun + ". ";
            } // don't have it yet
            else {
                Thing item = world.getThing(noun);
                world.moveItemToInventory(noun);
                return "You take the " + noun + ". ";
            }
        } // not takeable
        else {
            return "The  " + noun + " is not something you can take with you. ";
        }
    }

    // DONE: No need to implement
    private String printList(List<String> list) {
        return "";
    }
    
    // DONE
    private String listToSentence(List<String> items) {
        StringBuilder sb = new StringBuilder();
        List<String> tempList = items;
        int numContents = tempList.size();
        //3 cases because english and commas sucks
        //only one item
        if (numContents == 1) {
            sb.append("a ");
            sb.append(tempList.get(0));
        } //two items
        else if (numContents == 2) {
            sb.append("a ");
            sb.append(tempList.get(0));
            sb.append(" and a ");
            sb.append(tempList.get(1));
        } //3 or more items
        else {
            int i;
            for (i = 0; i < numContents - 1; i++) {
                sb.append("a ");
                sb.append(tempList.get(i));
                sb.append(", ");
            }
            sb.append("and a ");
            sb.append(tempList.get(i));
        }

        return sb.toString();
    }
}

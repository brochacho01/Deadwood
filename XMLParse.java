import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.util.ArrayList;
import java.util.HashMap;

// Parses the XML files for cards and board and creates those respective objects and populates them with data
class XMLParse {
    // parse the deck
    public Deck parseDeck() throws ParserConfigurationException {
        ArrayList<SceneCard> cards = new ArrayList<SceneCard>();
        Document d = getDocFromFile("cards.xml");
        Element root = d.getDocumentElement();
        NodeList cardNodes = root.getElementsByTagName("card");
        for (int i = 0; i < cardNodes.getLength(); i++) {
            HashMap<Role, Integer> playersOnCard = new HashMap<Role, Integer>();
            Node card = cardNodes.item(i);
            // Get cardname
            String cardName = card.getAttributes().getNamedItem("name").getNodeValue();
            // Get sceneDescription
            Node scene = card.getFirstChild().getNextSibling();
            String sceneNumberS = scene.getAttributes().getNamedItem("number").getNodeValue();
            int sceneNumber = Integer.parseInt(sceneNumberS);
            String sceneDescription = scene.getTextContent();
            sceneDescription = sceneDescription.replaceAll("[\\n\\t]", "");
            // Get budget
            String budgetS = card.getAttributes().getNamedItem("budget").getNodeValue();
            int budget = Integer.parseInt(budgetS);
            // need to get all the roles for each SceneCard
            NodeList roles = ((Element) card).getElementsByTagName("part");
            for (int j = 0; j < roles.getLength(); j++) {
                String roleName = roles.item(j).getAttributes().getNamedItem("name").getNodeValue();
                Node roleDescriptionNode = ((NodeList) roles.item(j)).item(3);
                String roleDescription = roleDescriptionNode.getTextContent();
                String rankS = roles.item(j).getAttributes().getNamedItem("level").getNodeValue();
                int rank = Integer.parseInt(rankS);
                Role r = new Role(roleName, roleDescription, rank, "Star");
                playersOnCard.put(r, -1);
            }
            SceneCard s = new SceneCard(cardName, sceneNumber, sceneDescription, budget, playersOnCard);
            cards.add(s);
        }
        Deck newDeck = new Deck(cards);
        return newDeck;
    }

    // parse the board
    public Board parseBoard() throws ParserConfigurationException {
        Board b = Board.getBoard();
        Document d = getDocFromFile("board.xml");
        Element root = d.getDocumentElement();
        NodeList setNodes = root.getElementsByTagName("set");
        // Get sets
        Set[] sets = new Set[setNodes.getLength()];
        for (int i = 0; i < setNodes.getLength(); i++) {
            Node set = setNodes.item(i);
            String setName = set.getAttributes().getNamedItem("name").getNodeValue();
            // Get all of the neighbors
            NodeList neighbors = ((Element) set).getElementsByTagName("neighbor");
            // Get all of the shots for each set
            NodeList takes = ((Element) set).getElementsByTagName("take");
            int shots = takes.getLength();
            // Get all of the roles for each set, each node should contain a name, a level,
            // area, and a line
            NodeList roles = ((Element) set).getElementsByTagName("part");
            // Store the name of all the neighbors of the current set in a string[]
            String[] neighborNames = new String[neighbors.getLength()];
            for (int j = 0; j < neighbors.getLength(); j++) {
                Node setChild = neighbors.item(j);
                neighborNames[j] = setChild.getAttributes().getNamedItem("name").getNodeValue();
            }
            Set s = new Set(setName, neighborNames, shots);
            // Extract all data from roles nodelist and assign to a new Role class
            for (int j = 0; j < roles.getLength(); j++) {
                String roleName = roles.item(j).getAttributes().getNamedItem("name").getNodeValue();
                Node roleDescriptionNode = ((NodeList) roles.item(j)).item(3);
                String roleDescription = roleDescriptionNode.getTextContent();
                String rankS = roles.item(j).getAttributes().getNamedItem("level").getNodeValue();
                int rank = Integer.parseInt(rankS);
                Role r = new Role(roleName, roleDescription, rank, "Extra");
                s.addRole(r);
            }
            sets[i] = s;
        }

        // Get trailer default info
        NodeList trailerNL = root.getElementsByTagName("trailer");
        Node trailer = trailerNL.item(0);
        NodeList trailerNeighbors = ((Element) trailer).getElementsByTagName("neighbor");
        // Get office default info
        NodeList officeNL = root.getElementsByTagName("office");
        Node office = officeNL.item(0);
        NodeList officeNeighbors = ((Element) office).getElementsByTagName("neighbor");
        String[] trailerNeighborNames = new String[trailerNeighbors.getLength()];
        String[] officeNeighborNames = new String[officeNeighbors.getLength()];
        // Create String arrays containing neighbors of trailer and office
        for (int i = 0; i < trailerNeighbors.getLength(); i++) {
            Node trailerNeighbor = trailerNeighbors.item(i);
            Node officeNeighbor = officeNeighbors.item(i);
            trailerNeighborNames[i] = trailerNeighbor.getAttributes().getNamedItem("name").getNodeValue();
            officeNeighborNames[i] = officeNeighbor.getAttributes().getNamedItem("name").getNodeValue();
        }
        // Get office upgrades
        int[][] officeUpgrades = new int[5][3];
        NodeList upgradeNodes = ((Element) office).getElementsByTagName("upgrade");

        for (int i = 0; i < upgradeNodes.getLength(); i++) {
            Node upgradeNode = upgradeNodes.item(i);
            if ("dollar".equals(upgradeNode.getAttributes().getNamedItem("currency").getNodeValue())) {
                officeUpgrades[(i % 5)][0] = (i % 5) + 2;
                officeUpgrades[(i % 5)][1] = Integer
                        .parseInt(upgradeNode.getAttributes().getNamedItem("amt").getNodeValue());
            } else {
                officeUpgrades[(i % 5)][2] = Integer
                        .parseInt(upgradeNode.getAttributes().getNamedItem("amt").getNodeValue());
            }
        }

        // create our trailer and office objects
        Trailer t = new Trailer("Trailer", trailerNeighborNames);
        Office o = new Office("Office", officeNeighborNames, officeUpgrades);
        // Need to create our rooms that board will hold which includes the two rooms
        // without sets
        Room[] rooms = new Room[sets.length + 2];
        for (int i = 0; i < sets.length; i++) {
            rooms[i + 2] = sets[i];
        }
        rooms[0] = t;
        rooms[1] = o;

        b.setRooms(rooms);

        return b;
    }

    public Document getDocFromFile(String filename) throws ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = null;

        try {
            doc = db.parse(filename);
        } catch (Exception ex) {
            System.out.println("XML parse failure");
            ex.printStackTrace();
        }
        return doc;
    }

}

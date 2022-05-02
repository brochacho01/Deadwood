import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.io.File;

class XMLParse
{
    //parse the deck
    public void parseDeck()
    {
        return;
    }

    //parse the board
    public void parseBoard() throws ParserConfigurationException
    {   
        Document d = getDocFromFile("board.xml");
        Element root = d.getDocumentElement();
        NodeList sets = root.getElementsByTagName("set");
        for (int i = 0; i < sets.getLength(); i++)
        {
            Set s = new Set();
            Node set = sets.item(i);
            String setName = set.getAttributes().getNamedItem("name").getNodeValue();
            // Get all of the neighbors
            NodeList neighbors = ((Element)set).getElementsByTagName("neighbor");
            // Get all of the shots for each set
            NodeList takes = ((Element)set).getElementsByTagName("take");
            int shots = takes.getLength();
            // Get all of the roles for each set, each node should contain a name, a level, area, and a line
            NodeList roles = ((Element)set).getElementsByTagName("part");
            // Extract all data from roles nodelist and assign to a new Role class
            for(int j = 0; j < roles.getLength(); j++){
                String roleName = roles.item(j).getAttributes().getNamedItem("name").getNodeValue();
                Node roleDescriptionNode = ((NodeList) roles.item(j)).item(3);
                String roleDescription = roleDescriptionNode.getTextContent();
                String rankS = roles.item(j).getAttributes().getNamedItem("level").getNodeValue();
                int rank = Integer.parseInt(rankS);
                Role r = new Role(roleName, roleDescription, rank);
                System.out.println("DeezNutz");
            }
            // Store the name of all the neighbors of the current set in a string[]
            String[] neighborNames = new String[neighbors.getLength()];
            for(int j = 0; j < neighbors.getLength(); j++)
            {
                Node setChild = neighbors.item(j);
                neighborNames[j] = setChild.getAttributes().getNamedItem("name").getNodeValue();
            }            
        }
        return;
    }

    public Document getDocFromFile(String filename) throws ParserConfigurationException{
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = null;

        try{
            doc = db.parse(filename);
        } catch (Exception ex) {
            System.out.println("XML parse failure");
            ex.printStackTrace();
        }
        return doc;
    }
   

}

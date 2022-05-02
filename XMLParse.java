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
            Node set = sets.item(i);
            String setName = set.getAttributes().getNamedItem("name").getNodeValue();
            NodeList neighbors = ((Element)set).getElementsByTagName("neighbor");
            System.out.println(neighbors.getLength());
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

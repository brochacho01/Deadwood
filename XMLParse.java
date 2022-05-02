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
            String name = set.getAttributes().getNamedItem("name").getNodeValue();
            NodeList children = set.getChildNodes();
            for(int j = 0; j < children.getLength(); j++)
            {
                Node sub = children.item(j);
                if("neighbors".equals(sub.getNodeName()));
                {
                    NodeList neighbors = sub.getChildNodes();
                    int numNeighbors = neighbors.getLength();
                    System.out.println(numNeighbors);
                    String[] names = new String[numNeighbors];
                    for(int k = 0; k < numNeighbors; k++)
                    {
                        Node neighbor = neighbors.item(k);
                        names[k] = neighbor.getAttributes().getNamedItem("name").getNodeValue();
                        System.out.println(names[k]);
                    }
                    System.out.println("aa");
                }
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

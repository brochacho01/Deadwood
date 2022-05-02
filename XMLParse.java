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
        Document boardDoc = getDocFromFile("board.xml");
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

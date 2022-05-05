import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private int deckSize;

    private int cardsInDeck;

    // By having all the SceneCards stored in an arrayList, their order is able to be randomized
    private ArrayList<SceneCard> cards;

    private void shuffle() {
        Collections.shuffle(cards);
    }

    // Creates an arrayList of SceneCards based on the number of players in the game
    public Deck(ArrayList<SceneCard> cards){
        this.cards = cards;
    }

    // Deck deals a card to each set
    public void dealCards(Board b) {
        Room[] rooms = b.getRooms();
        // iterate over each room
        for(int i = 0; i < rooms.length; i++){
            // if room is a set
            if(rooms[i] instanceof Set){
                // give it a scene card
                ((Set) rooms[i]).setSceneCard(this.cards.get(0));
                // remove scene card from deck
                this.cards.remove(0);
            }
        }
    }


}

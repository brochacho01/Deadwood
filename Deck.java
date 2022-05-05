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
    public void dealCards() {

    }


}

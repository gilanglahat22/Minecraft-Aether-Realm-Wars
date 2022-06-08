package com.aetherwars.model;
import java.util.*;

public class Deck <T extends Card>{
    private Stack<T> cards;
    
    Deck(){
        this.cards = new Stack<>();
    }

    public List<T> draw(){
        // asumsi kalo deck gapernah kosong, kalo kosong player kalah
        ArrayList<T> res = new ArrayList<>();

        // ngambil 3 kartu paling atas di deck
        for (int i = 0; i < Math.min(3, this.cards.size()); i ++){
            res.add(this.cards.pop());
        }
        return res;
    }

    public void putBackCards(List<T> cards){
        for (T card : cards) {
            this.cards.push(card);
        }
        // shuffle deck
        Collections.shuffle(this.cards);
    }

    public static List<Card> generateRandomCards(){
        Random rand = new Random();
        CardMap cm = CardMap.getInstance();
        List<Card> cards = new ArrayList<>();
        while (cards.size() < 30){
            Card c = cm.get(rand.nextInt(101 - 1) + 1);
            if(c != null){
                cards.add(c);
            }
        }

        while (cards.size() < 45){
            Card c = cm.get(rand.nextInt(500 - 101) + 101);
            if(c != null){
                cards.add(c);
            }
        }
        Collections.shuffle(cards);
        return cards;
    }

    public Stack<T> getCards() {
        return cards;
    }
}

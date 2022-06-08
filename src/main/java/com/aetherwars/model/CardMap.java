package com.aetherwars.model;

import java.util.HashMap;
import java.util.Set;

// Singleton Design Pattern
public class CardMap {
    private static CardMap cardMap;
    private HashMap<Integer, Card> map;

    private CardMap(){
        this.map = new HashMap<>();
    }

    public static CardMap getInstance(){
        if(CardMap.cardMap == null){
            CardMap.cardMap = new CardMap();
        }
        return CardMap.cardMap;
    }

    /** Tambahkan kartu baru dengan id key */
    public void set(Integer key, Card card){
        this.map.put(key, card);
    }
    /** Mengambil kartu baru dengan id key */
    public Card get(Integer key){
        return this.map.get(key);
    }

    public Set<Integer> getKeySet(){
        return this.map.keySet();
    }
}

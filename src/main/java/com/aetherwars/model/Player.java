package com.aetherwars.model;

import java.util.*;

public class Player {
  // Attributes
  private String name;
  private int health;
  private int mana;
  private Deck<Card> deck;
  private List<Card> hand;
  private Map<Integer, Summon> board;
  private boolean turn;
  private Game game;
  private boolean isAllowToAttack = false;
  private boolean isAllowPutCardCharacter = false;
  private boolean isAllowPutCardSpell = false;
  private boolean isAllowRemoveCardCharacterInHand = false;
  private boolean isAllowRemoveCardSpellInHand = false;
  private boolean isAllowRemoveCharacterInBoard = false;
  private boolean isAllowUseMana = false;
  private boolean isHoverableCard = false;

  public Player(String name, boolean turn) {
    this.name = name;
    this.health = 80;
    this.mana = 1; // Asumsi mana awal bernilai sama dengan health
    this.deck = new Deck<>();
    this.hand = new ArrayList<>();
    this.board = new HashMap<>();
    this.turn = turn;
  }

  // Getters
  public String getName() { return this.name; }
  public int getHealth() {
    return this.health;
  }
  public int getMana() {
    return this.mana;
  }
  public Deck getDeck() { return this.deck; }
  public void setDeck(Deck<Card> deck) {
    this.deck = deck;
  }
  public List<Card> getHand() {
    return this.hand;
  }
  public Map getBoard() {
    return this.board;
  }
  public boolean getTurn() {
    return this.turn;
  }
  public Summon getSummon(int key) {
    return board.get(key);
  }
  public boolean isAllowToAttack(){
    return this.isAllowToAttack;
  }

  public void setAllowAttack(boolean isAllow){
    this.isAllowToAttack = isAllow;
  }

  public boolean isAllowPutCardCharacter(){
    return this.isAllowPutCardCharacter;
  }

  public void setAllowPutCardCharacter(boolean isAllow){
    this. isAllowPutCardCharacter = isAllow;
  }

  public boolean isAllowPutCardSpell(){
    return this.isAllowPutCardSpell;
  }

  public void setAllowPutCardSpell(boolean isAllow){
    this. isAllowPutCardSpell = isAllow;
  }

  public boolean isAllowRemoveCardCharacterInHand(){
    return this.isAllowRemoveCardCharacterInHand;
  }

  public void setAllowRemoveCardCharacterInHand(boolean isAllow){
    this.isAllowRemoveCardCharacterInHand = isAllow;
  }

  public boolean isAllowRemoveCardSpellInHand(){
    return this.isAllowRemoveCardSpellInHand;
  }

  public void setAllowRemoveCardSpellInHand(boolean isAllow){
    this.isAllowRemoveCardSpellInHand = isAllow;
  }

  public boolean isAllowRemoveCharacterInBoard(){
    return this.isAllowRemoveCharacterInBoard;
  }

  public void setAllowRemoveCharacterInBoard(boolean isAllow){
    this.isAllowRemoveCharacterInBoard = isAllow;
  }

  public boolean isAllowUseMana(){
    return this.isAllowUseMana;
  }

  public void setAllowUseMana(boolean isAllow){
    this.isAllowUseMana = isAllow;
  }

  public boolean isHoverableCard(){
    return this.isHoverableCard;
  }

  public void setHoverableCard(boolean isAllow){
    this.isHoverableCard = isAllow;
  }

  // Setters
  public void setHealth(int health) {
    this.health = health;
  }
  public void setMana(int mana) {
    this.mana = mana;
  }
  public void setTurn(boolean turn) {
    this.turn = turn;
  }

  // Methods
  public void displayCard(Card card) {
    // melihat deskripsi dan atribut kartu
    System.out.println(card.toString());
  }

  public void takeCard(int pickCard) {
    // mengambil kartu baru dari deck
    List<Card> drawCards = this.deck.draw();
    Card chosenCard = drawCards.get(pickCard);
    drawCards.remove(pickCard);
    this.hand.add(chosenCard);
    if (this.hand.size() == 5) {
      Random rand = new Random();
      int idx = rand.nextInt(5);
      this.hand.remove(idx);
    }
    this.deck.putBackCards(drawCards);
  }

  public void summonCharacter(int key, CharacterCard card) {
    // Mengeluarkan kartu ke board
    Summon summon = new Summon(card);
    board.put(key, summon);
  }

  public void setSummon(int key, Summon summon){
    board.put(key, summon);
  }

  public void attackThis(Summon summon){
    if (this.isAllowToAttack){
      boolean isExistsSummon = false;
      for(Integer key : this.board.keySet()){
        if(this.board.get(key) != null){
          isExistsSummon = true;
          break;
        }
      }
      if(isExistsSummon){
        return;
      }
      this.health -= summon.getAttackEff();
    }
  }

  public void attack(char key, Player opponent) {
    // menyerang musuh
    if (this.isAllowToAttack){
      Summon summon = board.get(key);
      opponent.setHealth(opponent.getHealth() - summon.getAttack());
    }
  }

  public void nextPhase(Game.Phase phase) {
    // lanjut ke fase selanjutnya
    // Belum tau next phase mending di player atau game
    this.game.setPhase(phase);
  }
}

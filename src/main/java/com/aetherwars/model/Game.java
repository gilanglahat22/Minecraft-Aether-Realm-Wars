package com.aetherwars.model;
import java.util.*;

public class Game {
    // Singleton
    private static Game game = new Game();
    public enum Phase{
        DRAW,
        PLAN,
        ATTACK,
        END
    };

    private int round;
    private Phase phase;
    private int playerIndex;
    private ArrayList<Player> listPlayer;

    private Game()
    {
        round = 1;
        playerIndex = 1;
        phase = Phase.DRAW;
        listPlayer = new ArrayList<>();
        for (int i=0; i < 2 ; i++)
        {
            String name = "Player " + String.valueOf(i+1);
            Player p = new Player(name, false);
            p.getDeck().putBackCards(Deck.generateRandomCards());
            p.getHand().addAll(p.getDeck().draw());

            listPlayer.add(p);
        }
        listPlayer.get(0).setTurn(true);
    }

    public static Game getInstance(){
        return Game.game;
    }

    public int getRound()
    {
        return round;
    }

    public int getPlayerIndex()
    {
        return playerIndex;
    }

    public ArrayList<Player> getListPlayer()
    {
        return listPlayer;
    }

    public Phase getPhase()
    {
        return this.phase;
    }

    public void setRound(int round)
    {
        this.round = round;
    }

    public void setPlayerIndex(int playerIndex)
    {
        this.playerIndex = playerIndex;
    }

    public void setPhase(Phase phase)
    {
        this.phase = phase;
        if(phase == Phase.DRAW){
            if(this.playerIndex == 2) this.round++;
            Player p;
            if(this.playerIndex == 1){
                this.playerIndex = 2;
                p = this.getListPlayer().get(1);

            } else{
                this.playerIndex = 1;
                p = this.getListPlayer().get(0);
            }
            p.setMana(Math.min(this.round, 10));
        }
        this.setConditon();
    }

    public Phase nextPhase()
    {
        if (this.phase == Phase.DRAW){
            return Phase.PLAN;
        }
        else if (this.phase == Phase.PLAN){
            return Phase.ATTACK;
        }
        else if(this.phase == Phase.ATTACK){
            return Phase.END;
        }
        else {
            return Phase.DRAW;
        }
    }

    public void setConditon(){
        if (this.phase == Phase.DRAW){
            this.listPlayer.get(0).setHoverableCard(true);
            this.listPlayer.get(1).setHoverableCard(true);
        }
        else if (this.phase == Phase.PLAN){
            this.listPlayer.get(0).setAllowPutCardCharacter(true);
            this.listPlayer.get(1).setAllowPutCardCharacter(true);
            this.listPlayer.get(0).setAllowPutCardSpell(true);
            this.listPlayer.get(1).setAllowPutCardSpell(true);
            this.listPlayer.get(0).setAllowRemoveCardCharacterInHand(true);
            this.listPlayer.get(1).setAllowRemoveCardCharacterInHand(true);
            this.listPlayer.get(0).setAllowRemoveCardSpellInHand(true);
            this.listPlayer.get(1).setAllowRemoveCardSpellInHand(true);
            this.listPlayer.get(0).setAllowRemoveCharacterInBoard(true);
            this.listPlayer.get(1).setAllowRemoveCharacterInBoard(true);
            this.listPlayer.get(0).setAllowUseMana(true);
            this.listPlayer.get(1).setAllowUseMana(true);
        }
        else if(this.phase == Phase.ATTACK){
            this.listPlayer.get(0).setAllowAttack(true);
            this.listPlayer.get(1).setAllowAttack(true);
        }
        else {
            // Reset Game
            this.listPlayer.get(0).setHoverableCard(false);
            this.listPlayer.get(1).setHoverableCard(false);
            this.listPlayer.get(0).setAllowPutCardCharacter(false);
            this.listPlayer.get(1).setAllowPutCardCharacter(false);
            this.listPlayer.get(0).setAllowPutCardSpell(false);
            this.listPlayer.get(1).setAllowPutCardSpell(false);
            this.listPlayer.get(0).setAllowRemoveCardCharacterInHand(false);
            this.listPlayer.get(1).setAllowRemoveCardCharacterInHand(false);
            this.listPlayer.get(0).setAllowRemoveCardSpellInHand(false);
            this.listPlayer.get(1).setAllowRemoveCardSpellInHand(false);
            this.listPlayer.get(0).setAllowRemoveCharacterInBoard(false);
            this.listPlayer.get(1).setAllowRemoveCharacterInBoard(false);
            this.listPlayer.get(0).setAllowUseMana(false);
            this.listPlayer.get(1).setAllowUseMana(false);
            this.listPlayer.get(0).setAllowAttack(false);
            this.listPlayer.get(1).setAllowAttack(false);
            //this.game = new Game();
        }
    }
}

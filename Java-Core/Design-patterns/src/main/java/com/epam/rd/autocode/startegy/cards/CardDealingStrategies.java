package com.epam.rd.autocode.startegy.cards;

import java.util.*;

public class CardDealingStrategies {
    public static CardDealingStrategy texasHoldemCardDealingStrategy() {
        return (deck, players) -> {
            Map<String, List<Card>> result = dealCardsAmongPlayers(players, 2, deck);
            result.put("Community", dealNCards(deck, 5));
            result.put("Remaining", deck.restCards());
            return result;
        };
    }

    public static CardDealingStrategy classicPokerCardDealingStrategy() {
        return (deck, players) -> {
            Map<String, List<Card>> result = dealCardsAmongPlayers(players, 5, deck);
            result.put("Remaining", deck.restCards());
            return result;
        };
    }

    public static CardDealingStrategy bridgeCardDealingStrategy(){
        return (deck, players) ->  dealCardsAmongPlayers(players, 13, deck);
    }

    public static CardDealingStrategy foolCardDealingStrategy(){
        return (deck, players) -> {
            Map<String, List<Card>> result = dealCardsAmongPlayers(players, 6, deck);
            result.put("Trump card", dealNCards(deck, 1));
            result.put("Remaining", deck.restCards());
            return result;
        };
    }

    private static Map<String, List<Card>> dealCardsAmongPlayers(int players, int cardsNum, Deck deck) {
        Map<String, List<Card>> result = new HashMap<>();
        for(int i = 0; i < cardsNum; i++) {
            for (int j = 1; j <= players; j++) {
                List<Card> cards = new ArrayList<>();
                String player = "Player " + j;
                if(result.get(player) != null) {
                    cards.addAll(result.get(player));
                }
                cards.add(deck.dealCard());
                result.put(player, cards);
            }
        }
        return result;
    }

    private static List<Card> dealNCards(Deck deck, int n) {
        List<Card> cards = new ArrayList<>(n);
        for(int i = 0; i < n; i++) {
            cards.add(deck.dealCard());
        }
        return cards;
    }
}

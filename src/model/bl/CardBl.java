package src.model.bl;

import lombok.Getter;
import src.controller.exceptions.NotFoundException;
import src.model.da.CardDa;
import src.model.entity.Card;
import src.model.tools.CRUD;

import java.util.List;

public class CardBl  implements CRUD<Card> {
    @Getter
    private static CardBl cardBl = new CardBl();

    private CardBl() {
    }

    @Override
    public Card save(Card card) throws Exception {
        try (CardDa cardDa = new CardDa()) {
            cardDa.save(card);
            return card;
        }
    }

    @Override
    public Card edit(Card card) throws Exception {
        try (CardDa cardDa = new CardDa()) {
            if (cardDa.findById(card.getId()) != null) {
                cardDa.edit(card);
                return card;
            } else {
                throw new NotFoundException();
            }
        }
    }

    @Override
    public Card remove(int id) throws Exception {
        try (CardDa cardDa = new CardDa()) {
            Card card = cardDa.findById(id);
            if (card != null) {
                cardDa.remove(id);
                return card;
            } else {
                throw new NotFoundException();
            }
        }
    }

    @Override
    public List<Card> findAll() throws Exception {
        try (CardDa cardDa = new CardDa()) {
            List<Card> cardList = cardDa.findAll();
            if (!cardList.isEmpty()) {
                for (Card card : cardList) {
                    card.setAccountNumber(AccountBl.getAccountBl().findByAccountNumber(card.getAccountNumber().getAccountNumber()));
                }
                return cardList;
            } else {
                throw new NotFoundException();
            }
        }
    }

    @Override
    public Card findById(int id) throws Exception {
        try (CardDa cardDa = new CardDa()) {
            Card card = cardDa.findById(id);
            if (card != null) {
                card.setAccountNumber(AccountBl.getAccountBl().findByAccountNumber(card.getAccountNumber().getAccountNumber()));
                return card;
            } else {
                throw new NotFoundException();
            }
        }
    }
}

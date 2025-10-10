package ar.edu.utn.isi.boardgames.repositories;

import ar.edu.utn.isi.boardgames.entities.BoardGame;

public class BoardGameRepository extends Repository<BoardGame, Integer> {

    @Override
    protected Class<BoardGame> getEntityClass() {
        return BoardGame.class;
    }
}

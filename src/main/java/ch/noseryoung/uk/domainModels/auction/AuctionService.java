package ch.noseryoung.uk.domainModels.auction;

import java.util.List;

public interface AuctionService {

    Auction create(Auction auction);

    List<Auction> findAll();

    Auction findById(int id);

    Auction updateById(int id, Auction auction);

    void deleteById(int id);

    List<Auction> range(int from, int to);
}

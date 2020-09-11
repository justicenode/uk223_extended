package ch.noseryoung.uk.domain_models.auction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuctionServiceImpl implements AuctionService {
    private final AuctionRepository auctionRepository;

    @Autowired
    AuctionServiceImpl(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
    }

    @Override
    public Auction create(Auction auction) {
        auctionRepository.save(auction);
        return auction;
    }

    @Override
    public List<Auction> findAll() {
        return auctionRepository.findAll();
    }

    @Override
    public Auction findById(int id) {
        Optional<Auction> a = auctionRepository.findById(id);
        return a.orElse(null);
    }

    @Override
    public Auction updateById(int id, Auction auction) {
        auctionRepository.save(auction);
        return auction;
    }

    @Override
    public void deleteById(int id) {
        auctionRepository.deleteById(id);
    }

    @Override
    public List<Auction> range(int lowerThreshold, int upperThreshold) {
//        return auctionRepository.findAll().stream().filter(auction -> auction.getPrice() >= lowerThreshold && auction.getPrice() <= upperThreshold).collect(Collectors.toList());
        return auctionRepository.findAll().stream().filter(auction -> auction.getPrice() >= lowerThreshold && auction.getPrice() <= upperThreshold).sorted(Comparator.comparingDouble(Auction::getPrice)).collect(Collectors.toList());
    }
}

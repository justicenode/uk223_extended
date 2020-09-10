package ch.noseryoung.uk.domain_models.auction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Integer> {
//    @Query(nativeQuery = true, value = "SELECT * FROM auction WHERE price >= ?1 AND price <= ?2")
//    List<Auction> range(int from, int to);
}

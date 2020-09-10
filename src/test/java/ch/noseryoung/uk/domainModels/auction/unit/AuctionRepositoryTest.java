package ch.noseryoung.uk.domainModels.auction.unit;

import ch.noseryoung.uk.domainModels.auction.Auction;
import ch.noseryoung.uk.domainModels.auction.AuctionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
class AuctionRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private AuctionRepository auctionRepository;

    private Auction auctionToBeTestedAgainst1;
    private Auction auctionToBeTestedAgainst2;
    private List<Auction> listOfAuctionsToBeTestedAgainst;
    private List<Integer> listOfIdsFromDB;
    private Integer auctionIdFromDB1;
    private Integer auctionIdFromDB2;
//    private Auction newAuctionToBeSaved;

    @BeforeEach
    void setUp() {
        auctionToBeTestedAgainst1 = new Auction().setId(1).setName("Doe").setPrice(25);
        auctionToBeTestedAgainst2 = new Auction().setId(2).setName("asd").setPrice(34);
//        newAuctionToBeSaved = new Auction().setFirstName("Jack").setLastName("Doe").setEmail("jack.doe@noseryoung.ch").setEnabled(true).setPassword(new BCryptPasswordEncoder().encode(UUID.randomUUID().toString())).setRoles(rolesToBeTestedAgainst);
        listOfAuctionsToBeTestedAgainst = Arrays.asList(auctionToBeTestedAgainst1, auctionToBeTestedAgainst2);
        auctionIdFromDB1 = testEntityManager.persistAndGetId(auctionToBeTestedAgainst1, Integer.class);
        auctionIdFromDB2 = testEntityManager.persistAndGetId(auctionToBeTestedAgainst2, Integer.class);
        listOfIdsFromDB = Arrays.asList(auctionIdFromDB1, auctionIdFromDB2);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void findAll() {
        // Act
        List<Auction> auctionsFromDB = auctionRepository.findAll();

        // Assert
        assertThat(auctionsFromDB.stream().map(Auction::getId).toArray()).containsExactlyInAnyOrder(listOfIdsFromDB.toArray());
        assertThat(auctionsFromDB.stream().map(Auction::getName).toArray()).isEqualTo(listOfAuctionsToBeTestedAgainst.stream().map(Auction::getName).toArray());
        assertThat(auctionsFromDB.stream().map(Auction::getPrice).toArray()).isEqualTo(listOfAuctionsToBeTestedAgainst.stream().map(Auction::getPrice).toArray());
        assertThat(auctionsFromDB.stream().map(Auction::getEndDate).toArray()).isEqualTo(listOfAuctionsToBeTestedAgainst.stream().map(Auction::getEndDate).toArray());
    }

    // https://github.com/xinnyi/xinnyi_api/blob/master/src/test/java/ch/course223/advanced/domainmodels/user/unit/UserRepositoryUnitTest.java
}
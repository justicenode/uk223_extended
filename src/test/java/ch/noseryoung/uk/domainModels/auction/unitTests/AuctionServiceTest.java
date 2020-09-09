package ch.noseryoung.uk.domainModels.auction.unitTests;

import ch.noseryoung.uk.domainModels.auction.Auction;
import ch.noseryoung.uk.domainModels.auction.AuctionRepository;
import ch.noseryoung.uk.domainModels.auction.AuctionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
class AuctionServiceTest {
    @Autowired
    private AuctionService auctionService;
    @MockBean
    private AuctionRepository auctionRepository;

    private Auction testAuction;
    private List<Auction> testAuctions;
    private int min;
    private int max;
    private Auction lowerEdgeValue;
    private Auction upperEdgeValue;
    private Auction centerValue;
    private Auction lowerOutOfBoundsValue;
    private Auction upperOutOfBoundsValue;


    @BeforeEach
    void setUp() {
        testAuction = new Auction().setId(1).setName("asdf").setPrice(15);

        min = 1;
        max = 50;

        lowerEdgeValue = new Auction().setId(2).setName("asdf2").setPrice(min);
        upperEdgeValue = new Auction().setId(3).setName("asdf2").setPrice(max);
        centerValue = new Auction().setId(4).setName("asdf2").setPrice(max - min);
        lowerOutOfBoundsValue = new Auction().setId(5).setName("asdf2").setPrice(min - 1);
        upperOutOfBoundsValue = new Auction().setId(6).setName("asdf2").setPrice(max + 1);

        testAuctions = Arrays.asList(
                upperEdgeValue,
                lowerEdgeValue,
                centerValue,
                lowerOutOfBoundsValue,
                upperOutOfBoundsValue
        );
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAll() {
        given(auctionRepository.findAll()).will(a -> testAuctions);

        List<Auction> findAll = auctionService.findAll();

        assertEquals(findAll, testAuctions);
    }

    @Test
    void range() throws JsonProcessingException {
        Mockito.when(auctionRepository.findAll()).thenReturn(testAuctions);

        List<Auction> range = auctionService.range(min, max);

        assertFalse(range.isEmpty(), "no data returned");

        assertTrue(range.contains(lowerEdgeValue), "min was not included");
        assertTrue(range.contains(upperEdgeValue), "max was not included");
        assertTrue(range.contains(centerValue));

        assertFalse(range.contains(upperOutOfBoundsValue), "value above max was included");
        assertFalse(range.contains(lowerOutOfBoundsValue), "value below min was included");

        assertEquals(range.size(), testAuctions.size() - 2);
    }
}
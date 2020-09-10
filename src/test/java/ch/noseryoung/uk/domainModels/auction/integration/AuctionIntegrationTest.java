package ch.noseryoung.uk.domainModels.auction.integration;

import ch.noseryoung.uk.domainModels.auction.Auction;
import ch.noseryoung.uk.domainModels.auction.AuctionRepository;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuctionIntegrationTest {
    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private MockMvc mvc;

    @Before
    public void setUp() {
    }

    @Test
    public void findById() throws Exception {
        Auction auctionToBeTestedAgainst = auctionRepository.save(new Auction().setName("asdf").setPrice(12.5));

        mvc.perform(
                MockMvcRequestBuilders.get("/auctions/{id}", auctionToBeTestedAgainst.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(auctionToBeTestedAgainst.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(auctionToBeTestedAgainst.getPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(auctionToBeTestedAgainst.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value(auctionToBeTestedAgainst.getEndDate()));
    }

    @Test
    public void range() throws Exception {
        int min = 0;
        int max = 50;

        Auction auction1 = auctionRepository.save(new Auction().setId(1).setName("asdf").setPrice(min - 1));
        Auction auction2 = auctionRepository.save(new Auction().setId(2).setName("asdf").setPrice(min));
        Auction auction3 = auctionRepository.save(new Auction().setId(3).setName("asdf").setPrice(max - min));
        Auction auction4 = auctionRepository.save(new Auction().setId(4).setName("asdf").setPrice(max));
        Auction auction5 = auctionRepository.save(new Auction().setId(5).setName("asdf").setPrice(max + 1));
        List<Auction> expectedAuctions = Arrays.asList(
                auction2, auction4, auction3
        );

        mvc.perform(
                MockMvcRequestBuilders.get("/auctions/range/{min}/{max}", min, max)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(expectedAuctions.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id", Matchers.containsInAnyOrder(map(expectedAuctions, Auction::getId))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].price", Matchers.containsInAnyOrder(map(expectedAuctions, Auction::getPrice))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].name", Matchers.containsInAnyOrder(map(expectedAuctions, Auction::getName))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].endDate", Matchers.containsInAnyOrder(map(expectedAuctions, Auction::getEndDate))));
    }

    @Test
    public void findAll() throws Exception {
        Auction auction1 = auctionRepository.save(new Auction().setId(1).setName("asdf").setPrice(123));
        Auction auction2 = auctionRepository.save(new Auction().setId(2).setName("j2").setPrice(5.3));
        Auction auction3 = auctionRepository.save(new Auction().setId(3).setName("asadf").setPrice(1.2));
        Auction auction4 = auctionRepository.save(new Auction().setId(4).setName("as").setPrice(6.7));
        Auction auction5 = auctionRepository.save(new Auction().setId(5).setName("asdff").setPrice(69));
        List<Auction> auctionToBeTestedAgainst = Arrays.asList(
                auction1, auction2, auction3, auction4, auction5
        );

        mvc.perform(
                MockMvcRequestBuilders.get("/auctions/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(auctionToBeTestedAgainst.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id", Matchers.containsInAnyOrder(map(auctionToBeTestedAgainst, Auction::getId))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].price", Matchers.containsInAnyOrder(map(auctionToBeTestedAgainst, Auction::getPrice))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].name", Matchers.containsInAnyOrder(map(auctionToBeTestedAgainst, Auction::getName))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].endDate", Matchers.containsInAnyOrder(map(auctionToBeTestedAgainst, Auction::getEndDate))));
    }

    private List<?> map(List<Auction> auctions, Function<? super Auction, ?> mapper) {
        return auctions.stream().map(mapper).collect(Collectors.toList());
    }
}

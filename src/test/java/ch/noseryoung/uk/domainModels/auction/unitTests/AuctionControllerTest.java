package ch.noseryoung.uk.domainModels.auction.unitTests;

import ch.noseryoung.uk.domainModels.auction.Auction;
import ch.noseryoung.uk.domainModels.auction.AuctionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@AutoConfigureMockMvc
@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application-test.properties")
class AuctionControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    AuctionService auctionService;
    Auction testAuction;
    List<Auction> testAuctions;

    @BeforeEach
    void setUp() {
        testAuction = new Auction();
        testAuction.setId(1);
        testAuction.setName("test");
        testAuction.setPrice(16.5);

        Auction a1 = new Auction();
        a1.setPrice(14.5);
        a1.setName("asdf");
        a1.setId(2);

        testAuctions = Arrays.asList(testAuction, a1, new Auction().setId(4).setName("asdf").setPrice(50));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindById() throws Exception {
        given(auctionService.findById(anyInt())).will(invocation -> {
            if ("non-existent".equals(invocation.getArgument(0)))
                throw new Exception("400 - bad request");
            else return testAuction;
        });

        mockMvc.perform(
                MockMvcRequestBuilders.get("/auctions/{id}", testAuction.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(testAuction.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(testAuction.getPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(testAuction.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value(testAuction.getEndDate()));

        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);

        verify(auctionService, times(1)).findById(argument.capture());
        assertEquals((int) argument.getValue(), testAuction.getId());
    }

    @Test
    void testFindAll() throws Exception {
        given(auctionService.findAll()).will(invocation -> testAuctions);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/auctions/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(testAuctions.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id").value(map(testAuctions, Auction::getId)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].price").value(map(testAuctions, Auction::getPrice)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].name").value(map(testAuctions, Auction::getName)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].endDate").value(map(testAuctions, Auction::getEndDate)));

        verify(auctionService, times(1)).findAll();
    }

    @Test
    void testRange() throws Exception {
        given(auctionService.range(anyInt(), anyInt())).will((invocation -> testAuctions));

        mockMvc.perform(MockMvcRequestBuilders.get("/auctions/range/1/100")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(testAuctions.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id").value(map(testAuctions, Auction::getId)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].price").value(map(testAuctions, Auction::getPrice)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].name").value(map(testAuctions, Auction::getName)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].endDate").value(map(testAuctions, Auction::getEndDate)));

        ArgumentCaptor<Integer> arg1 = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> arg2 = ArgumentCaptor.forClass(Integer.class);
        verify(auctionService, times(1)).range(arg1.capture(), arg2.capture());

        assertEquals((int) arg1.getValue(), 1);
        assertEquals((int) arg2.getValue(), 100);
    }

    @Test
    void testUpdate() throws Exception {
        given(auctionService.updateById(anyInt(), any(Auction.class))).will(invocation -> {
            if ("non-existent".equals(invocation.getArgument(0)) || "non-existent".equals(invocation.getArgument(1)))
                throw new Exception("400 - bad request");
            else
                return ((Auction) invocation.getArgument(1)).setId(invocation.getArgument(0));
        });

        mockMvc.perform(MockMvcRequestBuilders.put("/auctions/{id}", testAuction.getId())
                .content(new ObjectMapper().writeValueAsString(testAuction))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(testAuction.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(testAuction.getPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(testAuction.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value(testAuction.getEndDate()));

        ArgumentCaptor<Integer> intArgs = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Auction> aucArgs = ArgumentCaptor.forClass(Auction.class);
        verify(auctionService, times(1)).updateById(intArgs.capture(), aucArgs.capture());

        assertEquals((int) intArgs.getValue(), testAuction.getId());
//        assertEquals(aucArgs.getValue(), testAuction);
    }

    @Test
    void testCreate() throws Exception {
        given(auctionService.create(any(Auction.class))).will(invocation -> {
            if ("non-existent".equals(invocation.getArgument(0)))
                throw new Exception("400 - bad request");
            else
                return ((Auction) invocation.getArgument(0)).setId(testAuction.getId());
        });

        mockMvc.perform(MockMvcRequestBuilders.post("/auctions")
                .content(new ObjectMapper().writeValueAsString(testAuction))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(testAuction.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(testAuction.getPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(testAuction.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value(testAuction.getEndDate()));

        ArgumentCaptor<Auction> aucArgs = ArgumentCaptor.forClass(Auction.class);
        verify(auctionService, times(1)).create(aucArgs.capture());

//        assertEquals(aucArgs.getValue(), testAuction);
    }

    private List<?> map(List<Auction> auctions, Function<? super Auction, ?> mapper) {
        return auctions.stream().map(mapper).collect(Collectors.toList());
    }
}
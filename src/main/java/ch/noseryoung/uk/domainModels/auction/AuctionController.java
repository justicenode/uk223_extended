package ch.noseryoung.uk.domainModels.auction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auctions")
public class AuctionController {
    private final AuctionService auctionService;

    @Autowired
    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @GetMapping({"/", ""})
    public ResponseEntity<List<Auction>> get() {
        return new ResponseEntity<>(auctionService.findAll(), HttpStatus.OK);
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<Auction> getById(@PathVariable int id) {
        return new ResponseEntity<>(auctionService.findById(id), HttpStatus.OK);
    }

    @PostMapping({"/", ""})
    public ResponseEntity<Auction> create(@RequestBody Auction auction) {
        return new ResponseEntity<>(auctionService.create(auction), HttpStatus.OK);
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<Auction> update(@PathVariable int id, @RequestBody Auction auction) {
        return new ResponseEntity<>(auctionService.updateById(id, auction), HttpStatus.OK);
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<String> delete(@PathVariable int id) {
        auctionService.deleteById(id);
        return new ResponseEntity<>("Deleted auction with id: " + id, HttpStatus.OK);
    }

    @GetMapping({"/range/{lowerThreshold}/{upperThreshold}"})
    public ResponseEntity<List<Auction>> range(@PathVariable int lowerThreshold, @PathVariable int upperThreshold) {
        return new ResponseEntity<>(auctionService.range(lowerThreshold, upperThreshold), HttpStatus.OK);
    }
}

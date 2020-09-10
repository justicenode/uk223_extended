package ch.noseryoung.uk.domain_models.auction;

import ch.noseryoung.uk.domain_models.auction.dto.AuctionDTO;
import ch.noseryoung.uk.domain_models.auction.dto.AuctionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auctions")
public class AuctionController {
    private final AuctionService auctionService;
    private final AuctionMapper auctionMapper;

    @Autowired
    public AuctionController(AuctionService auctionService, AuctionMapper auctionMapper) {
        this.auctionService = auctionService;
        this.auctionMapper = auctionMapper;
    }

    @GetMapping({"/", ""})
    public ResponseEntity<List<AuctionDTO>> get() {
        return new ResponseEntity<>(auctionMapper.toDTOs(auctionService.findAll()), HttpStatus.OK);
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<AuctionDTO> getById(@PathVariable int id) {
        return new ResponseEntity<>(auctionMapper.toDTO(auctionService.findById(id)), HttpStatus.OK);
    }

    @PostMapping({"/", ""})
    public ResponseEntity<AuctionDTO> create(@RequestBody AuctionDTO auction) {
        return new ResponseEntity<>(auctionMapper.toDTO(auctionService.create(auctionMapper.fromDTO(auction))), HttpStatus.OK);
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<AuctionDTO> update(@PathVariable int id, @RequestBody AuctionDTO auction) {
        return new ResponseEntity<>(auctionMapper.toDTO(auctionService.updateById(id, auctionMapper.fromDTO(auction))), HttpStatus.OK);
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<String> delete(@PathVariable int id) {
        auctionService.deleteById(id);
        return new ResponseEntity<>("Deleted auction with id: " + id, HttpStatus.OK);
    }

    @GetMapping({"/range/{lowerThreshold}/{upperThreshold}"})
    public ResponseEntity<List<AuctionDTO>> range(@PathVariable int lowerThreshold, @PathVariable int upperThreshold) {
        return new ResponseEntity<>(auctionMapper.toDTOs(auctionService.range(lowerThreshold, upperThreshold)), HttpStatus.OK);
    }
}

package ch.noseryoung.uk.domain_models.auction.dto;

import ch.noseryoung.uk.domain_models.auction.Auction;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuctionMapper {
    Auction fromDTO(AuctionDTO dto);
    List<Auction> fromDTOs(List<AuctionDTO> dtos);
    Set<Auction> fromDTOs(Set<AuctionDTO> dtos);
    AuctionDTO toDTO(Auction dm);
    List<AuctionDTO> toDTOs(List<Auction> dms);
    Set<AuctionDTO> toDTOs(Set<Auction> dms);
}

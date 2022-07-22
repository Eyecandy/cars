package no.linska.webapp.service;

import no.linska.webapp.dto.BuyerUserDto;
import no.linska.webapp.entity.Buyer;
import no.linska.webapp.entity.User;
import no.linska.webapp.exception.reason.ProcessingException;
import no.linska.webapp.exception.reason.Reason;
import no.linska.webapp.repository.BuyerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class BuyerServiceImpl implements  BuyerService{

    @Autowired
    BuyerRepository buyerRepository;

    @Autowired
    UserService userService;

    @Override
    public void updateBuyerInfo(BuyerUserDto buyerUserDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        user.setFirstName(buyerUserDto.getFirstName());
        user.setLastName(buyerUserDto.getLastName());
        user.setPhoneNumber(buyerUserDto.getPhoneNumber());

        Buyer buyer = getBuyerByUserId(user.getId());
        buyer.setCity(buyerUserDto.getCity());
        buyer.setPostBox(buyerUserDto.getPostBox());
        buyer.setStreetName(buyerUserDto.getStreetName());
        buyer.setStreetNumber(buyerUserDto.getStreetNumber());

        buyerRepository.save(buyer);
    }

    public void create(Buyer buyer) {
        buyerRepository.save(buyer);
    }

   public Buyer getBuyerByUserId(Long userId) {
        var buyer = buyerRepository.findByUser(userId);
        if (buyer.isEmpty()) {
            throw new ProcessingException(Reason.NO_SELLER_WITH_THAT_USER_ID, userId.toString());
        }
        return buyer.get();

    }
}

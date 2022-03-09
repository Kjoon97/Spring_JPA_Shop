package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional   //readOnly false로 됨.
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    //변경감지를 통해서 준영속 엔티티를 수정하는 방법.
    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity){
        Item findItem = itemRepository.findOne(itemId);   //실제 디비에 있는 영속 엔티티를 가져옴.
        findItem.setPrice(price);
        findItem.setName(name);
        findItem.setStockQuantity(stockQuantity);
    }
    //스프링이 이 라인이 끝나면 @Transactional의해서 트랜잭션이 커밋을한다. 커밋이되면 JPA는 플러쉬를 날림(영속성 컨텍스트에서 변경된 애를 탐지 - 더티체킹)
    //변경된애들을 update쿼리를 디비에 날림.
    //merge기법보다 변경감지법을 이용하자 실무에서는 merge쓸 일이 거의 없음

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }
}
/**이 service는 단순히 repository에게 위임하는 정도의 역할만 한다. 이때는 그냥 서비스 안 만들고 컨트롤러에서 바로 레포지토리로 접근해도 문제 없다.*/
package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class itemRepository {
    private final EntityManager em;

    /**상품 등록하기*/
    public void save(Item item){
        if(item.getId()==null){   //새로 생성된 item은 id가 없다. (jpa에 저장되기 전까지는 id가 없으므로)
            em.persist(item);
        }else{
            em.merge(item);       // 기존에 있으면 업데이트
        }
    }

    /**상품 id로 하나 찾기*/
    public Item findOne(Long id){
        return em.find(Item.class,id);
    }

    /**다 찾기*/
    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }
}

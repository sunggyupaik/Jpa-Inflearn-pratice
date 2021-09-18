import domain.Address;
import domain.AddressEntity;
import domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Set;

public class CollectionMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Member member = new Member();
            member.setName("member1");
            member.setAge(20);

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

            member.setHomeAddress(new Address("city", "zip", "street"));
            member.getAddressHistory().add(new AddressEntity("old1", "zip", "street"));
            member.getAddressHistory().add(new AddressEntity("old2", "zip", "street"));

            em.persist(member);

            em.flush();
            em.clear();
//
//            Member findMember = em.find(Member.class, member.getId());
//
//            List<AddressEntity> addressHistory = findMember.getAddressHistory();
//            System.out.println(addressHistory);
//
//            Set<String> favoriteFoods = findMember.getFavoriteFoods();
//            System.out.println(favoriteFoods);
//
//            findMember.getFavoriteFoods().remove("치킨");
//            findMember.getFavoriteFoods().add("요거트");
//
//            findMember.getAddressHistory().remove(0);
//            findMember.getAddressHistory().add(new AddressEntity("newCity", "zip", "street"));

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }
}

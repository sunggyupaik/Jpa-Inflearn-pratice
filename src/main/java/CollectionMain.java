import domain.Address;
import domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

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

            member.setHomeAddress(new Address("city", "zip", "street"));
            member.getAddressHistory().add(new Address("old1", "zip", "street"));
            member.getAddressHistory().add(new Address("old1", "zip", "street"));

            em.persist(member);

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

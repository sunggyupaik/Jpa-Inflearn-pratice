import domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class QueryMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Member member1 = new Member();
            member1.setName("memberA");
            member1.setAge(15);
            em.persist(member1);

            em.flush();
            em.clear();

            List<Member> result = em.createQuery("select m from Member m", Member.class)
                    .getResultList();

            Member findMember = result.get(0);
            findMember.setAge(20);

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

import domain.Member;
import domain.MemberDto;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpqlTypeMain {
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

            List<Object[]> resultList = em.createQuery("select m.name, 'HELLO', TRUE from Member m")
                    .getResultList();

            Object[] result = resultList.get(0);
            System.out.println(result[0]);
            System.out.println(result[1]);
            System.out.println(result[2]);

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

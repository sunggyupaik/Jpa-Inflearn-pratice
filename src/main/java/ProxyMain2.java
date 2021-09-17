import domain.Member;
import domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class ProxyMain2 {
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

            Member member2 = new Member();
            member2.setName("memberB");
            member2.setAge(20);
            em.persist(member2);

            em.flush();
            em.clear();

            Member refMember1 = em.getReference(Member.class, member1.getId());
            Member refMember2 = em.getReference(Member.class, member2.getId());
            System.out.println("== 타입 검사 결과는? " + (refMember1 == refMember2));
            System.out.println("refMember1 >>> " + refMember1.getClass());
            System.out.println("refMember2 >>> " + refMember2.getClass());

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

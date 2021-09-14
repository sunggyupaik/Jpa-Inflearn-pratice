package domain;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member1 = new Member();
            member1.setName("paik");
            member1.setAge(28);
            member1.setTeam(team);
            em.persist(member1);

            em.flush();
            em.clear();

            List<Member> members = em.createQuery("select m FROM Member m JOIN FETCH m.team", Member.class)
                    .getResultList();
//            Member findMember = em.find(Member.class, member1.getId());
//            System.out.println("Team class 호출 : " + findMember.getTeam().getClass());

//            System.out.println("=======================");
//            System.out.println(findMember.getTeam());
//            System.out.println(findMember.getTeam().getName());
//            System.out.println("=======================");

//            System.out.println("Team class 호출 : " + findMember.getTeam().getClass());

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
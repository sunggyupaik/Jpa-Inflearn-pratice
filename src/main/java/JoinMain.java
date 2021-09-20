import domain.Member;
import domain.MemberDto;
import domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JoinMain {
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
            member1.setName("memberA");
            member1.setAge(15);
            member1.setTeam(team);
            em.persist(member1);

            em.flush();
            em.clear();

            //내부 조인
            List<Member> resultList = em.createQuery("select m from Member m INNER JOIN m.team")
                    .getResultList();

            Member result = resultList.get(0);
            System.out.println(result.getName());
            System.out.println(result.getAge());

            //외부조인
            List<Member> resultList2 = em.createQuery("select m from Member m LEFT JOIN m.team")
                    .getResultList();

            result = resultList2.get(0);
            System.out.println(result.getName());
            System.out.println(result.getAge());

            //세타조인
            List<Member> resultList3 = em.createQuery("select m from Member m, Team t where m.name = t.name")
                    .getResultList();

            result = resultList3.get(0);
            System.out.println(result.getName());
            System.out.println(result.getAge());


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

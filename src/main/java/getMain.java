import domain.Child;
import domain.Member;
import domain.Parent;
import domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Collection;
import java.util.List;

public class getMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Team team = new Team();
            team.setName("팀A");
            em.persist(team);

            Member memberA = new Member();
            memberA.setTeam(team);
            team.getMembers().add(memberA);
            em.persist(memberA);

            em.flush();
            em.clear();

            String query = "SELECT m.team.name FROM Member m";
            List<String> result = em.createQuery(query, String.class)
                    .getResultList();

            for(String s : result) {
                System.out.println(s);
            }

            String query2 = "SELECT m.team FROM Member m";
            List<Team> result2 = em.createQuery(query2, Team.class)
                    .getResultList();

            for(Team s : result2) {
                System.out.println(s);
            }

            String query3 = "SELECT t.members FROM Team t";
            List<Collection> result3 = em.createQuery(query3, Collection.class)
                    .getResultList();

            for(Object s : result3) {
                System.out.println(s);
            }

            String query4 = "SELECT m FROM Team t JOIN t.members m";
            List<Member> result4 = em.createQuery(query4, Member.class)
                    .getResultList();

            for(Member s : result4) {
                System.out.println(s);
            }

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

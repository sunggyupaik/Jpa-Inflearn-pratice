import domain.Member;
import domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class FetchJoin2 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Team teamA = new Team();
            teamA.setName("팀A");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("팀B");
            em.persist(teamB);

            Member memberA = new Member();
            memberA.setName("회원1");
            memberA.setTeam(teamA);
            em.persist(memberA);

            Member memberB = new Member();
            memberB.setName("회원2");
            memberB.setTeam(teamA);
            em.persist(memberB);

            Member memberC = new Member();
            memberC.setName("회원3");
            memberC.setTeam(teamB);
            em.persist(memberC);

            em.flush();
            em.clear();

            //단순하게 lazy 로딩으로 페이징 처리하기
           String query = "select t From Team t";

            List<Team> result = em.createQuery(query, Team.class)
                    .setFirstResult(0)
                    .setMaxResults(2)
                    .getResultList();

            for(Team team : result) {
                System.out.println("teamName=" + team.getName() + ","
                        + "members=" + team.getMembers().size());
                for(Member member : team.getMembers()) {
                    System.out.println("-> member = " + member);
                }
            }

            //연관 컬렉션 fetch join 페이징 처리하기(위험)
            String query2 = "select t From Team t join fetch t.members m";

            List<Team> result2 = em.createQuery(query2, Team.class)
                    .setFirstResult(0)
                    .setMaxResults(2)
                    .getResultList();

            for(Team team : result2) {
                System.out.println("teamName=" + team.getName() + ","
                        + "members=" + team.getMembers().size());
                for(Member member : team.getMembers()) {
                    System.out.println("-> member = " + member);
                }
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

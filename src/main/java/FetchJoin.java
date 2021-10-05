import domain.Member;
import domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Collection;
import java.util.List;

public class FetchJoin {
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

            //LAZY로 연관 엔티티 조회
            String query = "select m From Member m";
            List<Member> members = em.createQuery(query, Member.class)
                    .getResultList();

            for(Member member : members) {
                System.out.println("username=" + member.getName() + ","
                        + "teamName=" + member.getTeam().getName());
            }

            //fetch join으로 연관 엔티티 조회
            String query2 = "select m From Member m join fetch m.team";
            List<Member> members2 = em.createQuery(query2, Member.class)
                            .getResultList();

            for(Member member : members2) {
                System.out.println("username=" + member.getName() + ","
                        + "teamName=" + member.getTeam().getName());
            }

            //fetch join으로 연관 컬렉션 조회
            String query3 = "select t From Team t join fetch t.members";
            List<Team> teams = em.createQuery(query3, Team.class)
                    .getResultList();

            for(Team team : teams) {
                System.out.println("username=" + team.getName() + ","
                        + "members=" + team.getMembers().size());
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

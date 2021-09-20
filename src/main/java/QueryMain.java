import domain.Member;
import domain.MemberDto;
import domain.Team;

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

            List<MemberDto> resultList = em.createQuery("select new domain.MemberDto(m.name, m.age) from Member m")
                    .getResultList();

            MemberDto result = resultList.get(0);
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

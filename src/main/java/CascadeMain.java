import domain.Child;
import domain.Member;
import domain.Parent;
import domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class CascadeMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Parent parent = new Parent();
            parent.setName("parent");

            Child child1 = new Child();
            child1.setName("child1");
            child1.setParent(parent);

            Child child2 = new Child();
            child2.setName("child2");
            child2.setParent(parent);

            //em.persist(child1);
            //em.persist(child2);
            parent.getChildList().add(child1);
            parent.getChildList().add(child2);
            em.persist(parent);

            em.flush();
            em.clear();

            Parent savedParent = em.find(Parent.class, parent.getId());
            savedParent.getChildList().remove(0);

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

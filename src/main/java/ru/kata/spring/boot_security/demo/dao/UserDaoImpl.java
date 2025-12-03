//package ru.kata.spring.boot_security.demo.dao;
//
//import org.hibernate.QueryException;
//import org.springframework.stereotype.Repository;
//import ru.kata.spring.boot_security.demo.model.User;
//
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.Query;
//import java.util.List;
//
//@Repository
//public class UserDaoImpl implements UserDao{
//
//
//    @PersistenceContext
//    private EntityManager em;
//
//    @Override
//    public void add(User user) {
//        em.persist(user);
//    }
//
//
//    @Override
//    public void update(User user) {
//        User oldUser = findById(user.getId());
//        oldUser.setFirstName(user.getFirstName());
//        oldUser.setLastName(user.getLastName());
//    }
//
//
//    @Override
//    public void delete(User user) {
//        User managedUser = em.find(User.class, user.getId());
//        if (managedUser != null) {
//            em.remove(managedUser);
//        }
//        em.flush();
//    }
//
//    @Override
//    public List<User> findAll() {
//        try {
//            Query query = em.createQuery("from User");
//            return query.getResultList();
//        } catch (QueryException e) {
//            e.printStackTrace();
//            return List.of();
//        }
//    }
//
//    @Override
//    public User findById(Long id) {
//        return em.find(User.class, id) ;
//    }
//}
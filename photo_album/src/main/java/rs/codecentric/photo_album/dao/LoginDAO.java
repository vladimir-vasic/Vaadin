package rs.codecentric.photo_album.dao;

import javax.annotation.Resource;
import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import rs.codecentric.photo_album.entity.User;

@Repository("loginService")
@Transactional
public class LoginDAO implements ILoginDAO {

	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;

	public User getUserByUsername(String userName) {
		User user = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			String hql = "FROM User WHERE userName = :userName";
			user = (User) session.createQuery(hql)
					.setParameter("userName", userName).list().get(0);
			return user;
		} catch (NoResultException e) {
			return user;
		}
	}

}
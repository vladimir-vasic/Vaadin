package rs.codecentric.photo_album.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;

import javax.annotation.Resource;
import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import rs.codecentric.photo_album.entity.Authority;
import rs.codecentric.photo_album.entity.User;

@Repository("loginService")
@Transactional
public class LoginDAO implements ILoginDAO, Serializable {

	private static final long serialVersionUID = 8913297868875802060L;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

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

	public void insertInitialUser() {
		Session session = sessionFactory.getCurrentSession();
		User user = null;
		log.info("Trying to load initial user...");
		try {
			user = (User) session.load(User.class, 1L);
		} catch (Exception e) {
			log.error("NO INITIAL USER!!!");
		}
		if (user == null) {
			log.info("Inserting initial user on application startup...");
			try {
				Authority auth = new Authority();
				auth.setAuthority("ROLE_USER");
				user = new User();
				user.setUserName("abc");
				user.setUserPassword("123");
				user.setEnabled(Boolean.TRUE);
				user.setCreateDateTime(new Date());
				user.setUserEmail("abc@gmail.com");
				user.setUserLib("abc123");
				HashSet<Authority> authHashSet = new HashSet<Authority>();
				authHashSet.add(auth);
				user.setAuthoritySet(authHashSet);
				session.save(user);
				log.info("Initial user saved.");
			} catch (Exception exc) {
				log.error("COULD NOT INSERT INITIAL USER !!!", exc);
			}
		}
	}
}
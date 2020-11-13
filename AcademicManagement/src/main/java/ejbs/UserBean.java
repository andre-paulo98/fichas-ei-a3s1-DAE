package ejbs;

import entities.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UserBean {

	@PersistenceContext
	EntityManager em;

	public User authenticate(final String username, final String password) throws Exception {
		User user = em.find(User.class, username);
		String passwordC = user.getPassword();
		String hashed = User.hashPassword(password);
		if (user != null && passwordC.equals(hashed)) {
			return user;
		}
		throw new Exception("Failed logging in with username '" + username + "': unknown username or wrong password");
	}

}

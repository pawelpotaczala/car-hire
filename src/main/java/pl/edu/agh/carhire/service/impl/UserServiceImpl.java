package pl.edu.agh.carhire.service.impl;

import pl.edu.agh.carhire.model.User;
import pl.edu.agh.carhire.repository.UserRepository;
import pl.edu.agh.carhire.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public User findOne(Long id) {
		return userRepository.findById(id).get();
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User findByUserName(String userName) {
		return userRepository.findByUsername(userName);
	}

	@Override
	public User save(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public void remove(Long id) throws IllegalArgumentException {
		User user = userRepository.findById(id).get();
		if (user == null) {
			throw new IllegalArgumentException(String.format(
					"User with id=%d does not exist.", id));
		}
		userRepository.deleteById(id);
	}

	@Override
	public boolean isUsernameUnique(Long id, String username) {
		User user = findByUserName(username);

		return (user == null || ((id != null) && (user.getId() == id)));
	}
}

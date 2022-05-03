package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    public static final int USERS_PER_PAGE = 5;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public List<User> listAll() {
        return (List<User>) userRepository.findAll();
    }

    public Page<User> listByPage(int pageNumber, String sortField, String sortDir, String keyword) {

        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();


        Pageable pageable = PageRequest.of(pageNumber -1, USERS_PER_PAGE, sort);

        if(keyword != null) {
            return userRepository.findAll(keyword,pageable);
        }

        return userRepository.findAll(pageable);
    }

    public List<Role> getAllRoles() {
        return (List<Role>) roleRepository.findAll();
    }

    public User saveUser(User user) {

        boolean isUpdatingUser = (user.getId() != null);
        if(isUpdatingUser) {
            User existingUser = userRepository.findById(user.getId()).get();
                if(user.getPassword().isEmpty()) {
                    user.setPassword(existingUser.getPassword());
                } else {
                    encodePassword(user);
                }

        } else {
            encodePassword(user);
        }

        return userRepository.save(user);
    }

    public User updateAccount(User userInForm) {
      User userInDb = userRepository.findById(userInForm.getId()).get();

      if(!userInForm.getPassword().isEmpty()) {
          userInDb.setPassword(userInForm.getPassword());
          encodePassword(userInDb);
      }

      if(userInForm.getPhotos() != null) {
          userInDb.setPhotos(userInForm.getPhotos());
      }

      userInDb.setFirstName(userInForm.getFirstName());
      userInDb.setLastName(userInForm.getLastName());

      return userRepository.save(userInDb);
    }

    private void encodePassword(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    public boolean isEmailUnique(Integer id, String email) {
        User userByEmail = userRepository.getUserByEmail(email);

        if(userByEmail == null) return true;

        boolean isCreatingNew = (id == null);

        if(isCreatingNew) {
            if(userByEmail != null) return false;
        } else {
            if (userByEmail.getId() != id) {
                return false;
            }
        }

        return true;
    }

    public User userGetById(Integer id) throws UserNotFoundException {
        try {
            return userRepository.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new UserNotFoundException("Could not find any user with ID: " + id);
        }

    }

    public void deleteUserById(Integer id) throws UserNotFoundException {
         Long countById = userRepository.countById(id);

         if(countById ==null || countById == 0) {
             throw new UserNotFoundException("Could not find any user with ID " +id);
         }
         userRepository.deleteById(id);
    }

    public void updateUserEnabledStatus(Integer id, boolean enabled) {
        userRepository.updateEnabledStatus(id,enabled);
    }

}

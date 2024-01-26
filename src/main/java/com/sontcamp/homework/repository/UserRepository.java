package com.sontcamp.homework.repository;

import com.sontcamp.homework.domain.User;
import com.sontcamp.homework.dto.type.EProvider;
import com.sontcamp.homework.dto.type.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u.id AS id, u.role AS role, u.password AS password FROM User u " +
            "WHERE u.id = :id " +
            "AND u.isLogin = true")
    Optional<UserSecurityForm> findFormById(Long id);

    @Query("SELECT u.id AS id, u.role AS role, u.password AS password FROM User u " +
            "WHERE u.serialId = :serialId " +
            "AND u.provider = :provider")
    Optional<UserSecurityForm> findFormBySerialIdAndProvider(String serialId, EProvider provider);

    @Query("SELECT u.id AS id, u.role AS role, u.password AS password FROM User u " +
            "WHERE u.serialId = :serialId ")
    Optional<UserSecurityForm> findFormBySerialId(String serialId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE User u " +
            "SET u.refreshToken = :refreshToken, u.isLogin = :isLogin " +
            "WHERE u.id = :id")
    void updateRefreshTokenAndLoginStatus(Long id, String refreshToken, boolean isLogin);

    Optional<User> findBySerialId(String serialId);


    @Query("SELECT u FROM User u WHERE u.id = :userId AND u.isLogin = true AND u.refreshToken IS NOT NULL")
    Optional<User> findUserByUserIdAndIsLoginAndRefreshTokenIsNotNull(Long userId);

    /* Security */
    interface UserSecurityForm {
        Long getId();
        ERole getRole();
        String getPassword();

        static UserSecurityForm invoke(User user) {
            return new UserSecurityForm() {
                @Override
                public Long getId() {
                    return user.getId();
                }

                @Override
                public ERole getRole() {
                    return user.getRole();
                }

                @Override
                public String getPassword() {
                    return user.getPassword();
                }
            };
        }

        static UserSecurityForm createFromUser(User user) {
            return new UserSecurityForm() {
                @Override
                public Long getId() {
                    return user.getId();
                }

                @Override
                public ERole getRole() {
                    return user.getRole();
                }

                @Override
                public String getPassword() {
                    return user.getPassword();
                }
            };
        }
    }
}

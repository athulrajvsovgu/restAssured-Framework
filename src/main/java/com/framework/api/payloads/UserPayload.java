package com.framework.api.payloads;

import com.framework.api.pojos.User;
import com.github.javafaker.Faker;

/**
 * Factory class for creating User payloads using Java Faker.
 */
public class UserPayload {

    private static final Faker faker = new Faker();
    
    /**
     * Creates a new user with random values using Faker
     */
    public static User createRandomUser() {
        long id = faker.number().numberBetween(10000L, 100000L);
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String username = faker.name().username();
        String email = faker.internet().emailAddress(username);
        String password = faker.internet().password(8, 12);
        String phone = faker.phoneNumber().cellPhone();
        int userStatus = faker.number().numberBetween(0, 2);
        
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhone(phone);
        user.setUserStatus(userStatus);
        
        return user;
    }
    
    /**
     * Updates a user's email and password
     */
    public static User updateUserCredentials(User user, String newEmail, String newPassword) {
        user.setEmail(newEmail != null ? newEmail : faker.internet().emailAddress(user.getUsername()));
        user.setPassword(newPassword != null ? newPassword : faker.internet().password(8, 20));
        return user;
    }
}
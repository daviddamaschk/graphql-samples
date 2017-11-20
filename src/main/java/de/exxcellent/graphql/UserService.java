package de.exxcellent.graphql;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Service für User-Abfragen.
 */
public class UserService {

    @GraphQLQuery(name = "getUser")
    public User getById(@GraphQLArgument(name = "id") Integer id) {
        return new User(new Name("David", "D"), 123, new Date());
    }

    @GraphQLQuery(name = "userlist")
    public List<User> getUserlist() {
        User david = new User(new Name("David", "D"), 123, new Date());
        User alex = new User(new Name("Alex", "A"), 124, new Date());
        User andreas = new User(new Name("Andreas", "A"), 125, new Date());

        ArrayList<User> users = new ArrayList<>();
        users.add(david);
        users.add(alex);
        users.add(andreas);

        return users;
    }
}
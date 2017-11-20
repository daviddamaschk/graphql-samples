package de.exxcellent.graphql;

import io.leangen.graphql.annotations.GraphQLQuery;

import java.util.Date;

/**
 * User Data.
 */
public class User {

    private Name name;
    private Integer id;
    private Date registrationDate;

    public User() {
    }

    public User(Name name, Integer id, Date registrationDate) {
        this.name = name;
        this.id = id;
        this.registrationDate = registrationDate;
    }

    @GraphQLQuery(name = "name", description = "A person's name")
    public Name getName() {
        return name;
    }

    /**
     * Setter for property 'name'.
     *
     * @param name Value to set for property 'name'.
     */
    public void setName(Name name) {
        this.name = name;
    }

    @GraphQLQuery(name = "id", description = "A person's id")
    public Integer getId() {
        return id;
    }

    /**
     * Setter for property 'id'.
     *
     * @param id Value to set for property 'id'.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    @GraphQLQuery(name = "regDate", description = "Date of registration")
    public Date getRegistrationDate() {
        return registrationDate;
    }

    /**
     * Setter for property 'registrationDate'.
     *
     * @param registrationDate Value to set for property 'registrationDate'.
     */
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", registrationDate=" + registrationDate +
                '}';
    }
}

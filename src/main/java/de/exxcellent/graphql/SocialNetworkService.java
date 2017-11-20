package de.exxcellent.graphql;

import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for social accounts queries
 */
public class SocialNetworkService {

    @GraphQLQuery(name = "getSocialNetworkAccounts")
    public List<SocialNetworkAccount> getSocialNetworkAccounts(@GraphQLContext User user) {


        SocialNetworkAccount facebook = new SocialNetworkAccount(user.getName().getFirstName() + "  is on Facebook");
        SocialNetworkAccount twitter = new SocialNetworkAccount(user.getName().getFirstName() + " is on Twitter");
        SocialNetworkAccount google = new SocialNetworkAccount(user.getName().getFirstName() + " is a Googler");

        ArrayList<SocialNetworkAccount> socialAccounts = new ArrayList<>();
        socialAccounts.add(facebook);
        socialAccounts.add(twitter);
        socialAccounts.add(google);

        return socialAccounts;
    }
}
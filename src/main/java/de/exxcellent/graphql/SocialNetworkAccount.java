package de.exxcellent.graphql;

/**
 * Social account Data class.
 */
public class SocialNetworkAccount {

    private String socialnetworkAccount;

    /**
     * Constructor
     *
     * @param socialnetworkAccount name of social account.
     */
    public SocialNetworkAccount(String socialnetworkAccount) {
        this.socialnetworkAccount = socialnetworkAccount;
    }

    /**
     * Getter for property 'socialnetworkAccount'.
     *
     * @return Value for property 'socialnetworkAccount'.
     */
    public String getSocialnetworkAccount() {
        return socialnetworkAccount;
    }

    /**
     * Setter for property 'socialnetworkAccount'.
     *
     * @param socialnetworkAccount Value to set for property 'socialnetworkAccount'.
     */
    public void setSocialnetworkAccount(String socialnetworkAccount) {
        this.socialnetworkAccount = socialnetworkAccount;
    }
}

package de.exxcellent.graphql;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import io.leangen.graphql.GraphQLSchemaGenerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * Root-Class for running graphql examples with graphql-spqr annotations. https://github.com/leangen/graphql-spqr
 */
public class App {

    public static void main(String[] args) throws IOException {

        /**
         * Register services.
         */
        UserService userService = new UserService(); //instantiate the service (or inject by Spring or another framework)
        SocialNetworkService socialNetworkService = new SocialNetworkService(); //instantiate the service (or inject by Spring or another framework)
        GraphQLSchema schema = new GraphQLSchemaGenerator()
                .withOperationsFromSingleton(userService) //register the service
                .withOperationsFromSingleton(socialNetworkService) //register the service
                .generate();
        GraphQL graphQL = GraphQL.newGraphQL(schema).build();


        /**
         * simple graphql-query. Get user with id 123.
         */
        ExecutionResult result = graphQL.execute("{ getUser (id: 123) { name {firstName, lastName}, regDate}}");
        // Kommentare in Query möglich, Formattierung anpassbar (Kommas sind bspw. unnötig).

        toJson(result.getData());

        /**
         * simple graphql-query with lists.
         */
        ExecutionResult userlist = graphQL.execute(
                "{ userlist {name {firstName}, id}}");
        // todo uncomment to see result
        //toJson(userlist.getData());


        /**
         * more complex query. Get a user and his/her social accounts.
         */
        ExecutionResult userWithNetworkAccounts = graphQL.execute(
                "{ getUser (id: 123) {name {firstName}, getSocialNetworkAccounts{socialnetworkAccount} }}");
        // todo uncomment to see result
        //toJson(userWithNetworkAccounts.getData());

        /**
         * aliases for calling multiple times the same service.
         */
        ExecutionResult aliases = graphQL.execute("{ " +
                "userServiceAliasOne: getUser (id: 123) { name {firstName, lastName}, regDate} " +
                "userServiceAliasTwo: getUser (id: 122) { name {firstName, lastName}, regDate}}");
        // todo uncomment to see result
        //toJson(aliases.getData());

        /**
         *  fragments let you construct sets of fields, and then include them in queries where you need to.
         */
        ExecutionResult fragments = graphQL.execute("{ " +
                "userServiceAliasOne: getUser (id: 123) { ...fragmentUserName, regDate} " +
                "userServiceAliasTwo: getUser (id: 122) { ...fragmentUserName, regDate}}" +
                " fragment fragmentUserName on User { name {firstName, lastName} }");
        // todo uncomment to see result
        //toJson(fragments.getData());


        /**
         * introspection delivers information about services, fields etc.
         */
        String resultPath = "introspectionquery.txt";
        ClassLoader classLoader = new App().getClass().getClassLoader();
        InputStream resourceAsStream = classLoader.getResourceAsStream(resultPath);
        String res = read(resourceAsStream);
        ExecutionResult fullintrospection = graphQL.execute(res);
        // todo uncomment to see result
        //toJson(fullintrospection.getData());

        // schema-informations.
        //ExecutionResult introspection = graphQL.execute("{ __schema {types {name} } }");

        //toJson(introspection.getData());

    }

    public static void toJson(Object src) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String formattedString = gson.toJson(src);
        System.out.println(formattedString);
    }

    public static String read(InputStream input) throws IOException {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(input))) {
            return buffer.lines().collect(Collectors.joining("\n"));
        }
    }

}
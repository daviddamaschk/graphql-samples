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
 * Klasse zum Ausführen von GraphQL mit Annotationen. https://github.com/leangen/graphql-spqr
 */
public class App {

    public static void main(String[] args) throws IOException {

        /**
         * Servies an GraphQL registrieren.
         */
        UserService userService = new UserService(); //instantiate the service (or inject by Spring or another framework)
        SocialNetworkService socialNetworkService = new SocialNetworkService(); //instantiate the service (or inject by Spring or another framework)
        GraphQLSchema schema = new GraphQLSchemaGenerator()
                .withOperationsFromSingleton(userService) //register the service
                .withOperationsFromSingleton(socialNetworkService) //register the service
                .generate();
        GraphQL graphQL = GraphQL.newGraphQL(schema).build();


        /**
         * Einfache GraphQL-Abfrage:
         */
        ExecutionResult result = graphQL.execute("{ getUser (id: 123) { name {firstName, lastName}, regDate}}");
        // Kommentare in Query möglich, Formattierung anpassbar (Kommas sind bspw. unnötig).

        toJson(result.getData());

        /**
         * Einfache GraphQL-Abfrage mit Listen.
         */
        ExecutionResult userlist = graphQL.execute(
                "{ userlist {name {firstName}, id}}");
        // todo uncomment to see result
        //toJson(userlist.getData());


        /**
         * Ineinander verschachtelte Abfrage, um einen Nutzer mit seinen Sozialen Accounts zu erhalten.
         */
        ExecutionResult userWithNetworkAccounts = graphQL.execute(
                "{ getUser (id: 123) {name {firstName}, getSocialNetworkAccounts{socialnetworkAccount} }}");
        // todo uncomment to see result
        //toJson(userWithNetworkAccounts.getData());

        /**
         * Aliases, um identische Service-Methoden mehrfach aufzurufen.
         */
        ExecutionResult aliases = graphQL.execute("{ " +
                "userServiceAliasOne: getUser (id: 123) { name {firstName, lastName}, regDate} " +
                "userServiceAliasTwo: getUser (id: 122) { name {firstName, lastName}, regDate}}");
        // todo uncomment to see result
        //toJson(aliases.getData());

        /**
         * Mit Fragmenten können wierkehrende Querybestandteile mehrfach verwendet werden.
         */
        ExecutionResult fragments = graphQL.execute("{ " +
                "user1: getUser (id: 123) { ...fragmentName, regDate} " +
                "user2: getUser (id: 122) { ...fragmentName, regDate}}" +
                " fragment fragmentName on User { name {firstName, lastName} }");
        // todo uncomment to see result
        //toJson(fragments.getData());


        /**
         * Introspection liefert Informationen zu den verfügbaren Services, Rückgabewerten etc.
         */
        String resultPath = "introspectionquery.txt";
        ClassLoader classLoader = new App().getClass().getClassLoader();
        InputStream resourceAsStream = classLoader.getResourceAsStream(resultPath);
        String res = read(resourceAsStream);
        ExecutionResult fullintrospection = graphQL.execute(res);
        // todo uncomment to see result
        //toJson(fullintrospection.getData());

        // Schema-Informationen aus der Introspection
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
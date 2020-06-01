package Actors;

import Database.DataBase;
import Model.DatabaseMessage;
import Model.ServerRequest;
import akka.actor.AbstractActor;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(DatabaseMessage.class, database -> {
                    save(database);
                })
                .matchAny(unknown->{
                    System.err.println("Unknown message");
                })
                .build();
    }

    private void save(DatabaseMessage message) throws SQLException, ClassNotFoundException {
        Connection connection = DataBase.getDbConnection();
        Statement statement = connection.createStatement();

        String query = "SELECT * FROM history WHERE name = '" + message.getProductName() + "';";
        ResultSet result = statement.executeQuery(query);

        if(result.isBeforeFirst()){
            message.setCounter( result.getInt("count"));
            int counter = message.getCounter()+1;
            query = "UPDATE history SET count = " + counter + " WHERE name='" + message.getProductName() + "';";
            statement.executeUpdate(query);
        }
        else{
            query = "INSERT INTO history (name, count) VALUES ('" +  message.getProductName() + "', 1);";
            statement.executeUpdate(query);
            message.setCounter(0);
        }
        message.getServer().tell(message,getSelf());
        result.close();
        connection.close();
    }
}

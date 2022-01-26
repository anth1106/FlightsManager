package cat.proven.fligths.model.persist;

import cat.proven.fligths.model.Passenger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DAO for passenger entity
 * @author anth
 */
public class PassengerDao {
    
    //ATTRIBUTES
    private DbConnect dbConnect;
    private Map<String,String> queries;
    
    //CONSTRUCTORS
    public PassengerDao() {    
        //initializate dbConnect 
        dbConnect = new DbConnect();
        //initializate queries map
        queries = new HashMap<>();
        initQueries();
    }
    
    /**
     * gets all passengers from database
     * @return a list with all passengers or null in case of error.
     */
    public List<Passenger> selectAllPassengers(){
        List<Passenger> result = new ArrayList<>();
        try {
            Connection conn = dbConnect.getConnection();
            if(conn != null){
                String query = queries.get("sAll");
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query);
                while(rs.next()){
                    Passenger obj = fromResultSet(rs);
                    if(obj !=null){
                        result.add(obj);
                    }
                }
            }
        } catch (SQLException ex) {
            result = null;
        }
        return result;
    }
    
    /**
     * insert the new passenger given into the database
     * @param passenger new flight to insert
     * @return 1 if succesfully added, 0 otherwise
     */
    public int insert(Passenger passenger){
        int result = 0;
        try {
            Connection conn = dbConnect.getConnection();
            if (conn != null) {
                String query = queries.get("insert");
                PreparedStatement st = conn.prepareStatement(query);
                st.setInt(1, passenger.getPhone());
                st.setString(2, passenger.getName());
                st.setBoolean(3, passenger.isMinor());
                result = st.executeUpdate();
            }
        } catch (SQLException ex) {
            result = 0;
        }
        return result;
    }
    
    /**
     * searches the passenger with the given code and update it with the new passenger
     * data.
     * @param passenger passenger with the new data
     * @param oldPhone phone of the passenger to update
     * @return 1 if succesfully modified, 0 otherwise
     */
    public int update(Passenger passenger, String oldPhone){
        int result = 0;
        try {
            Connection conn = dbConnect.getConnection();
            if (conn != null) {
                String query = queries.get("update");
                PreparedStatement st = conn.prepareStatement(query);
                st.setInt(1, passenger.getPhone());
                st.setString(2, passenger.getName());
                st.setBoolean(3, passenger.isMinor());
                st.setString(4, oldPhone);
                result = st.executeUpdate();
            }
        } catch (SQLException ex) {
            result = 0;
        }
        return result;
    }
    
    /**
     * delete the passenger with te given code from the database.
     * @param phone phone of the passenger to delete
     * @return 1 if succesfully deleted, 0 otherwise
     */
    public int delete(int phone){
        int result = 0;
        try {
            Connection conn = dbConnect.getConnection();
            if (conn != null) {
                String query = queries.get("delete");
                PreparedStatement st = conn.prepareStatement(query);
                String sphone = String.valueOf(phone);
                st.setString(1, sphone);
                result = st.executeUpdate();
            }
        } catch (SQLException ex) {
            result = 0;
        }
        return result;
    }
    
    /**
     * initializate the map with the queries to communicate with database
     */
    private void initQueries(){
        queries.put("sAll",String.format("select * from %s", "passengers"));
        queries.put("insert",String.format("insert into %s values(null,?,?,?)", "passengers"));
        queries.put("update",String.format("update %s set phone = ?, name = ?, minor = ? where phone = ?", "passengers"));
        queries.put("delete",String.format("delete from %s where phone = ?", "passengers"));
        queries.put("sWherePhone",String.format("select * from %s where phone = ?", "passengers"));
    }

    private Passenger fromResultSet(ResultSet rs) throws SQLException {
        Passenger p = null;
        long id = rs.getLong("id");
        String sphone = rs.getString("phone");
        int phone = Integer.valueOf(sphone);
        String name = rs.getString("name");
        boolean minor = rs.getBoolean("minor");
        p = new Passenger(id, phone, name, minor);
        return p;
    }

}

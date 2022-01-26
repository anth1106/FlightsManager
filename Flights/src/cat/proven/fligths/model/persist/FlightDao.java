package cat.proven.fligths.model.persist;

import cat.proven.fligths.model.Flight;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.java2d.pipe.SpanShapeRenderer.Simple;

/**
 * DAO for flight entity
 *
 * @author anth
 */
public class FlightDao {

    //ATTRIBUTES
    private DbConnect dbConnect;
    private Map<String, String> queries;

    //CONSTRUCTORS
    public FlightDao() throws ClassNotFoundException {
        //initializate dbConnect 
        dbConnect = new DbConnect();
        dbConnect.loadDriver();
        //initializate queries map
        queries = new HashMap<>();
        initQueries();
    }

    /**
     * gets all flights grom database
     *
     * @return a list with all flights or null in case of error.
     */
    public List<Flight> selectAllFlights() {
        List<Flight> result = new ArrayList<>();
        try {
            Connection conn = dbConnect.getConnection();
            if (conn != null) {
                String query = queries.get("sAll");
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    Flight obj = fromResultSet(rs);
                    if (obj != null) {
                        result.add(obj);
                    }
                }
            }
        } catch (SQLException ex) {
            //Logger.getLogger(FlightDao.class.getName()).log(Level.SEVERE, null, ex);
            result = null;
        }
        return result;
    }

    /**
     * insert the new flight given into the database
     *
     * @param flight new flight to insert
     * @return 1 if succesfully added, 0 otherwise
     */
    public int insert(Flight flight) {
        int result = 0;
        try {
            Connection conn = dbConnect.getConnection();
            if (conn != null) {
                String query = queries.get("insert");
                PreparedStatement st = conn.prepareStatement(query);
                st.setString(1, flight.getCode());
                st.setInt(2, flight.getCapacity());
                st.setDate(3, java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(flight.getDate())));
                st.setTime(4, java.sql.Time.valueOf(new SimpleDateFormat("HH:mm:ss").format(flight.getTime())));
                result = st.executeUpdate();
            }
        } catch (SQLException ex) {
            result = 0;
        } catch (ClassCastException ex) {
            result = 0;
        }
        return result;
    }

    /**
     * searches the flight with the given code and update it with the new flight
     * data.
     *
     * @param flight flight with the new data
     * @param code code of the flight to update
     * @return 1 if succesfully modified, 0 otherwise
     */
    public int update(Flight flight, String code) {
        int result = 0;
        try {
            Connection conn = dbConnect.getConnection();
            if (conn != null) {
                String query = queries.get("update");
                PreparedStatement st = conn.prepareStatement(query);
                st.setString(1, flight.getCode());
                st.setInt(2, flight.getCapacity());
                st.setDate(3, java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(flight.getDate())));
                st.setTime(4, java.sql.Time.valueOf(new SimpleDateFormat("HH:mm:ss").format(flight.getTime())));
                st.setString(5, code);
                result = st.executeUpdate();
            }
        } catch (SQLException ex) {
            result = 0;
        } catch (ClassCastException ex) {
            result = 0;
        }
        return result;
    }

    /**
     * delete the flight with te given code from the database.
     *
     * @param code code of the flight to delete
     * @return 1 if succesfully deleted, 0 otherwise
     */
    public int delete(String code) {
        int result = 0;
        try {
            Connection conn = dbConnect.getConnection();
            if (conn != null) {
                String query = queries.get("delete");
                PreparedStatement st = conn.prepareStatement(query);
                st.setString(1, code);
                result = st.executeUpdate();
            }
        } catch (SQLException ex) {
            result = 0;
        }
        return result;
    }

    public Flight selectByCode(String code) {
        Flight f = null;
        try {
            Connection conn = dbConnect.getConnection();
            if (conn != null) {
                String query = queries.get("sWhereCode");
                PreparedStatement st = conn.prepareStatement(query);
                st.setString(1, code);
                ResultSet rs = st.executeQuery();
                boolean success = rs.next();
                if (!success) {
                    throw new RuntimeException("Row not found");
                }
                f = fromResultSet(rs);
            }
        } catch (SQLException ex) {
            //Logger.getLogger(FlightDao.class.getName()).log(Level.SEVERE, null, ex);
            f = null;
        }
        return f;
    }

    /**
     * initializate the map with the queries to communicate with database
     */
    private void initQueries() {
        queries.put("sAll", String.format("select * from %s", "flights"));
        queries.put("insert", String.format("insert into %s values(null,?,?,?,?)", "flights"));
        queries.put("update", String.format("update %s set code = ?, capacity = ?, date = ?, time = ? where code = ?", "flights"));
        queries.put("delete", String.format("delete from %s where code = ?", "flights"));
        queries.put("sWhereCode", String.format("select * from %s where code = ?", "flights"));
    }

    private Flight fromResultSet(ResultSet rs) throws SQLException {
        Flight f = null;
        long id = rs.getLong("id");
        String code = rs.getString("code");
        int capacity = rs.getInt("capacity");
        java.sql.Date sql_date = rs.getDate("date");
        java.sql.Time sql_time = rs.getTime("time");
        Date date = new Date(sql_date.getTime());
        Date time = new Date(sql_time.getTime());
        f = new Flight(id, code, capacity, date, time);
        return f;
    }
}

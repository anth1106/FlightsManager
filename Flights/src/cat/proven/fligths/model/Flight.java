package cat.proven.fligths.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * ADT for a fligth 
 * @author anth
 */
public class Flight {
    //ATTRIBUTES
    private long id;
    private String code;
    private int capacity;
    private Date date;
    private Date time;

    //CONSTRUCTORS
    public Flight(long id, String code, int capacity, Date date, Date time) {
        this.id = id;
        this.code = code;
        this.capacity = capacity;
        this.date = date;
        this.time = time;
    }

    public Flight(String code) {
        this.code = code;
    }
    
    //GETTERS AND SETTERS

    public long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public int getCapacity() {
        return capacity;
    }

    public Date getDate() {
        return date;
    }

    public Date getTime() {
        return time;
    }    

    //METHODS
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 17 * hash + Objects.hashCode(this.code);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Flight other = (Flight) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.code, other.code)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //StringBuilder evita el uso inncesario de memoria
        StringBuilder sb = new StringBuilder();
        sb.append("Flight {");
        sb.append("[id=");
        sb.append(id);
        sb.append("], ");
        sb.append("[code=");
        sb.append(code);
        sb.append("], ");
        sb.append("[capacity=");
        sb.append(capacity);
        sb.append("], ");
        sb.append("[date=");
        sb.append(new SimpleDateFormat("yyyy-MM-dd").format(date));
        sb.append("], ");
        sb.append("[time=");
        sb.append(new SimpleDateFormat("HH:mm:ss").format(time));
        sb.append("]");
        sb.append("}");
        return sb.toString();
    }
    
    
}

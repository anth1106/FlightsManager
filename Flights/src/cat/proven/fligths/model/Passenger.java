package cat.proven.fligths.model;

/**
 * ADT for a passenger
 *
 * @author anth
 */
public class Passenger {

    //ATTRIBUTES
    private long id;
    private int phone;
    private String name;
    private boolean minor;

    //CONSTRUCTORS
    public Passenger(long id, int phone, String name, boolean minor) {
        this.id = id;
        this.phone = phone;
        this.name = name;
        this.minor = minor;
    }

    public Passenger(int phone) {
        this.phone = phone;
    }
    
    //GETTERS

    public long getId() {
        return id;
    }

    public int getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public boolean isMinor() {
        return minor;
    }
    
    //METHODS

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 59 * hash + this.phone;
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
        final Passenger other = (Passenger) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.phone != other.phone) {
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
        sb.append("[phone=");
        sb.append(phone);
        sb.append("], ");
        sb.append("[name=");
        sb.append(name);
        sb.append("], ");
        sb.append("[minor=");
        sb.append(minor);
        sb.append("]");
        sb.append("}");
        return sb.toString();
    }
    
    
}

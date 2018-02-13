package home.nkavtur.hibernateexamples.config.type;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.StringType;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;

/**
 * NikolaiKavtur.
 */
public class BooleanBitStringType implements UserType {

    @Override
    public int[] sqlTypes() {
        return new int[]{StringType.INSTANCE.sqlType()};
    }

    @Override
    public Class returnedClass() {
        return Boolean.class;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return Objects.equals(x, y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return Objects.hashCode(x);
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws HibernateException, SQLException {
        String columnName = names[0];
        String columnValue = String.class.cast(rs.getObject(columnName));

        if (columnName == null) {
            return null;
        }

        return "1".equalsIgnoreCase(columnValue) ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, Types.VARCHAR);
        } else {
            String stringValue = Boolean.class.cast(value) == Boolean.TRUE ? "1" : "0";
            st.setString(index, stringValue);
        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Boolean) value;
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return (Boolean) cached;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return (Boolean) original;
    }
}

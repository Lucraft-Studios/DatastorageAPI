package lucraft.database.query;

public class QueryBuilder {

    private String query = "";

    public QueryBuilder where(String field) {
        query += "where " + field + " ";
        return this;
    }

    public QueryBuilder isEqualTo(Object value) {

        return this;
    }

    public QueryBuilder contains(Object value) {

        return this;
    }
    
    public QueryBuilder and() {
        return this;
    }

    public QueryBuilder or() {
        return this;
    }

    public Query build() {
        return new Query(new QueryParameter[]{});
    }

}

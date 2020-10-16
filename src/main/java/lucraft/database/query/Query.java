package lucraft.database.query;

public class Query {

    private final QueryParameter[] params;

    Query(QueryParameter[] params) {
        this.params = params;
    }

    @Override
    public String toString() {
        String s = "where name = 'Daytimer'";
        for (QueryParameter param : params) {

        }
        return s;
    }

}

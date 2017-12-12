package com.unico.json;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PairParameterMapper implements ResultSetMapper<PairParameters> {
    @Override
    public PairParameters map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new PairParameters(r.getInt("A"), r.getInt("B"));
    }
}